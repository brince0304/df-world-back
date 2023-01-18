package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    private final SaveFileService saveFileService;

    private final CharacterService characterService;

    private final RedisService redisService;




    @GetMapping("/board.df")
    public ModelAndView getBoardList(@RequestParam (required = false) BoardType boardType,
                                     @RequestParam (required = false) String keyword,
                                     @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam (required = false) String searchType) {
        ModelAndView mav = new ModelAndView("/board/boardList");
        mav.addObject("articles",boardService.getBoardsByKeyword(boardType,keyword,searchType,pageable));
        log.info("articles : {}",mav.getModel().get("articles"));
        mav.addObject("bestArticles",boardService.getBestBoard(boardType));
        if(boardType!=null) {
            mav.addObject("boardType", boardType.toString());
        }
        mav.addObject("keyword",keyword);
        mav.addObject("searchType",searchType);
        return mav;
    }
    @PostMapping ("/api/like.df")
    public ResponseEntity<?> likeBoard(@RequestParam Long boardId, HttpServletRequest req) {
        if(redisService.checkBoardLikeLog(req.getRemoteAddr(),boardId)) {
            redisService.deleteBoardLikeLog(req.getRemoteAddr(),boardId);
            return new ResponseEntity<>(boardService.decreaseLikeCount(boardId),HttpStatus.OK);

        }else{
            redisService.saveBoardLikeLog(req.getRemoteAddr(),boardId);
            return new ResponseEntity<>(boardService.increaseLikeCount(boardId),HttpStatus.OK);
        }
    }



    @GetMapping("/board/{boardId}.df")
    public ModelAndView getBoardDetails(@PathVariable Long boardId,
                                        HttpServletRequest req) {
            ModelAndView mav = new ModelAndView("/board/boardDetails");
            if(!redisService.checkBoardViewLog(req.getRemoteAddr(),boardId)){
                redisService.saveBoardViewLog(req.getRemoteAddr(),boardId);
                boardService.increaseViewCount(boardId);
            }
            Board.BoardDetailResponse boardListResponse = boardService.getBoardDetail(boardId);
            mav.addObject("article", boardListResponse);
            mav.addObject("boardType", boardListResponse.getBoardType().toString());
            mav.addObject("bestArticles", boardService.getBestBoard(null));
            mav.addObject("likeLog",redisService.checkBoardLikeLog(req.getRemoteAddr(),boardId));
            return mav;
        }



    @GetMapping("/board/insert.df")
    public ModelAndView getBoardInsert(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                       @RequestParam (required = true) String request,
                                       @RequestParam (required = false) String id,
                                       Board.BoardRequest boardRequest) {
        if(principalDto==null){
            throw new EntityNotFoundException("로그인이 필요합니다.");
        }else if(request==null){
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        ModelAndView mav = new ModelAndView("/board/boardInsert");
        if(request.equals("add")){
            mav.addObject("boardRequest", boardRequest);
            mav.addObject("requestType",request);
        }else if(request.equals("update")){
            Board.BoardDetailResponse boardResponse = boardService.getBoardDetail(Long.parseLong(id));
            mav.addObject("boardResponse", boardResponse);
            mav.addObject("requestType",request);
            StringBuilder sb = new StringBuilder();
            for(String hashtag : boardResponse.getHashtags()){
                sb.append("#").append(hashtag);
            }
            mav.addObject("hashtag",sb);
            mav.addObject("characterExist", boardResponse.getCharacters().size()>0);
            if(boardResponse.getCharacters().size()>0){
                mav.addObject("characterName", boardResponse.getCharacters().stream().findFirst().get().getCharacterName());
                mav.addObject("characterId", boardResponse.getCharacters().stream().findFirst().get().getCharacterId());
                mav.addObject("serverId", boardResponse.getCharacters().stream().findFirst().get().getServerId());
            }
        }
        return mav;
    }

    @DeleteMapping("/api/board.df")
public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                      @RequestParam (required = true) Long id) {

            String writer = boardService.getBoardAuthor(id);
            if (principalDto == null || !writer.equals(principalDto.getUsername())) {
                throw new SecurityException("권한이 없습니다.");
            }
            boardService.getBoardSaveFile(id).forEach(saveFile -> {
                log.info("saveFile : {}", saveFile);
                saveFileService.deleteFile(saveFile.id());
            });
            boardService.deleteBoardById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }


    @PostMapping("/api/board.df")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody @Valid Board.BoardRequest boardRequest, BindingResult bindingResult) {

        if(principalDto==null){
           throw new SecurityException("권한이 없습니다.");
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(boardRequest);
        if(boardRequest.getServerId().equals("")){
            return new ResponseEntity<>(boardService.createBoard(boardRequest,set,UserAccount.UserAccountDto.from(principalDto),null),HttpStatus.OK);
        }
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(boardRequest.getServerId(),boardRequest.getCharacterId());
        return new ResponseEntity<>(boardService.createBoard(boardRequest,set ,UserAccount.UserAccountDto.from(principalDto),character),HttpStatus.OK);}


    @PutMapping("/api/board.df")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody @Valid Board.BoardRequest updateRequest,BindingResult bindingResult) {

        String writer = boardService.getBoardAuthor(updateRequest.getId());
        if(principalDto==null || !writer.equals(principalDto.getUsername())){
           throw new SecurityException("권한이 없습니다.");
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(updateRequest);
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(updateRequest.getServerId(),updateRequest.getCharacterId());
            return new ResponseEntity<>(boardService.updateBoard(updateRequest.getId(),updateRequest,set,character),HttpStatus.OK);
    }
}
