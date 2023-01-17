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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final BoardCommentService commentService;
    private final SaveFileService saveFileService;

    private final CharacterService characterService;

    private final RedisService redisService;




    @GetMapping("/board.df")
    public ModelAndView getBoardList(@RequestParam (required = false) BoardType boardType,
                                     @RequestParam (required = false) String keyword,
                                     @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam (required = false) String searchType) {
        ModelAndView mav = new ModelAndView("/board/boardList");
        mav.addObject("articles",boardService.getBoardsByKeyword(boardType,keyword,searchType,pageable).map(Board.BoardResponse::from));
        log.info("articles : {}",mav.getModel().get("articles"));
        mav.addObject("bestArticles",boardService.getBestBoard(boardType).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
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
    @GetMapping("/api/comment.df")
    public ResponseEntity<?> getBoardComments(HttpServletRequest req,
            @RequestParam (required = false) Long boardId,
                                              @RequestParam (required = false) Long commentId){
        Map<String,Object> map = new HashMap<>();
        Map<String,Boolean> likeMap = new HashMap<>();
        map.put("bestComments",commentService.findBestBoardCommentByBoardId(boardId).stream().map(BoardComment.BoardCommentResponse::from).collect(Collectors.toList()));
        if(commentId==null) {
            map.put("comments",commentService.findBoardCommentByBoardId(boardId).stream().map(BoardComment.BoardCommentResponse::from).collect(Collectors.toList()));
            boardService.getBoardDetail(boardId).getBoardComments().forEach(o->{
                likeMap.put(String.valueOf(o.getId()), redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId, o.getId()));
            });
            map.put("likeMap",likeMap);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        else{
            map.put("comments",commentService.findBoardCommentsByParentId(boardId,commentId).stream().map(BoardComment.BoardCommentResponse::from).collect(Collectors.toList()));
            boardService.getBoardDetail(boardId).getBoardComments().forEach(o->{
                likeMap.put(String.valueOf(o.getId()), redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId, o.getId()));
            });
            map.put("likeMap",likeMap);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
    }

    @PostMapping("/api/likeComment.df")
    public ResponseEntity<?> likeComment(@RequestParam Long commentId,@RequestParam Long boardId, HttpServletRequest req) {
        if(redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId,commentId)) {
            redisService.deleteBoardCommentLikeLog(req.getRemoteAddr(),boardId,commentId);
            return new ResponseEntity<>(commentService.updateBoardCommentDisLike(commentId),HttpStatus.OK);

        }else{
            redisService.saveBoardCommentLikeLog(req.getRemoteAddr(),boardId,commentId);
            return new ResponseEntity<>(commentService.updateBoardCommentLike(commentId),HttpStatus.OK);
        }
    }

    @PostMapping("/api/comment.df")
    public ResponseEntity<?> createBoardComment(@RequestBody @Valid BoardComment.BoardCommentRequest request,BindingResult bindingResult, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                                @RequestParam (required = false) String mode){
            if(principalDto==null){throw new SecurityException("로그인이 필요합니다.");}
            if(bindingResult.hasErrors()){
                return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
            }
            Board.BoardDto boardDto = boardService.getBoardDetail(request.getBoardId());
            if(mode!=null&&mode.equals("children")) {
                commentService.createChildrenComment(request.getCommentId(),BoardComment.BoardCommentDto.from(request, UserAccount.UserAccountDto.from(principalDto),boardDto));
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                commentService.createBoardComment(BoardComment.BoardCommentDto.from(request, UserAccount.UserAccountDto.from(principalDto), boardDto));
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
        }


    @DeleteMapping("/api/comment.df")
    public ResponseEntity<?> deleteBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto dto , @RequestParam Long commentId){

            if(dto==null || !Objects.equals(commentService.findBoardCommentById(commentId).getUserAccount().userId(), dto.getUsername())){
                return new ResponseEntity<>("권한이 없습니다.",HttpStatus.UNAUTHORIZED);
            }
            commentService.deleteBoardComment(commentId);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }


    @PutMapping("/api/comment.df")
    public ResponseEntity<?> updateBoardComment(@RequestBody @Valid BoardComment.BoardCommentRequest request,BindingResult bindingResult, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto){

            if(principalDto==null){
                throw new SecurityException("로그인이 필요합니다.");
            }if(bindingResult.hasErrors()){
                return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
            }
            commentService.updateBoardComment(request.getCommentId(),request,principalDto.getUsername());
            return new ResponseEntity<>("success",HttpStatus.OK);
        }



    @GetMapping("/board/{boardId}.df")
    public ModelAndView getBoardDetails(@PathVariable Long boardId,
                                        HttpServletRequest req) {
            ModelAndView mav = new ModelAndView("/board/boardDetails");
            if(!redisService.checkBoardViewLog(req.getRemoteAddr(),boardId)){
                redisService.saveBoardViewLog(req.getRemoteAddr(),boardId);
                boardService.increaseViewCount(boardId);
            }
            Board.BoardResponse boardResponse = Board.BoardResponse.from(boardService.getBoardDetail(boardId));
            mav.addObject("article", boardResponse);
            mav.addObject("boardType", boardResponse.getBoardType().toString());
            mav.addObject("bestArticles", boardService.getBestBoard(null).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
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
            Board.BoardResponse boardResponse = Board.BoardResponse.from(boardService.getBoardDetail(Long.parseLong(id)));
            mav.addObject("boardResponse", boardResponse);
            mav.addObject("requestType",request);
            StringBuilder sb = new StringBuilder();
            for(Hashtag.HashtagResponse hashtag : boardResponse.getHashtags()){
                sb.append("#").append(hashtag.getName());
            }
            mav.addObject("hashtag",sb);
            mav.addObject("characterExist",boardResponse.getCharacter()!=null);
            if(boardResponse.getCharacter()!=null){
                mav.addObject("characterName",boardResponse.getCharacter().getCharacterName());
                mav.addObject("characterId",boardResponse.getCharacter().getCharacterId());
                mav.addObject("serverId",boardResponse.getCharacter().getServerId());
            }
        }
        return mav;
    }

    @DeleteMapping("/api/board.df")
public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                      @RequestParam (required = true) Long id) {

            Board.BoardDto boardDto = boardService.getBoardDetail(id);
            if (principalDto == null || !boardDto.getUserAccount().userId().equals(principalDto.getUsername())) {
                throw new SecurityException("권한이 없습니다.");
            }
            boardDto.getBoardFiles().forEach(saveFile -> {
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
            return new ResponseEntity<>(boardService.createBoard(Board.BoardDto.from(boardRequest, UserAccount.UserAccountDto.from(principalDto)),set,null).getId(),HttpStatus.OK);
        }
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(boardRequest.getServerId(),boardRequest.getCharacterId());
        return new ResponseEntity<>(boardService.createBoard(Board.BoardDto.from(boardRequest, UserAccount.UserAccountDto.from(principalDto)),set,character).getId(),HttpStatus.OK);}


    @PutMapping("/api/board.df")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody @Valid Board.BoardRequest updateRequest,BindingResult bindingResult) {

        Board.BoardDto dto_ = boardService.getBoardDetail(updateRequest.getId());
        if(principalDto==null || !dto_.getUserAccount().userId().equals(principalDto.getUsername())){
           throw new SecurityException("권한이 없습니다.");
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(updateRequest);
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(updateRequest.getServerId(),updateRequest.getCharacterId());
            return new ResponseEntity<>(boardService.updateBoard(updateRequest.getId(),Board.BoardDto.from(updateRequest, UserAccount.UserAccountDto.from(principalDto)),set,character).getId(),HttpStatus.OK);
    }
}
