package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Service.*;
import io.github.furstenheim.CopyDown;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
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
        if(redisService.checkLikeLog(req.getRemoteAddr(),boardId)) {
            redisService.deleteLikeLog(req.getRemoteAddr(),boardId);
            return new ResponseEntity<>(boardService.decreaseLikeCount(boardId),HttpStatus.OK);

        }else{
            redisService.saveLikeLog(req.getRemoteAddr(),boardId);
            return new ResponseEntity<>(boardService.increaseLikeCount(boardId),HttpStatus.OK);
        }
    }
    @GetMapping("/api/comment.df")
    public ResponseEntity<?> getBoardComments(@RequestParam (required = false) Long boardId,
                                              @RequestParam (required = false) Long commentId){
        if(commentId==null) {
            return new ResponseEntity<>(commentService.findBoardCommentByBoardId(boardId).stream().map(BoardComment.BoardCommentResponse::from),HttpStatus.OK);
        }
        else{
            List<BoardComment.BoardCommentDto> dtos =commentService.findBoardCommentsByParentId(boardId,commentId);
            log.info("dtos : {}",dtos);
            return new ResponseEntity<>(dtos.stream().map(BoardComment.BoardCommentResponse::from),HttpStatus.OK);
        }
    }

    @PostMapping("/api/comment.df")
    public ResponseEntity<?> createBoardComment(@RequestBody BoardComment.BoardCommentRequest request, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                                @RequestParam (required = false) String mode){
        try {
            Board.BoardDto boardDto = boardService.getBoardDetail(request.getBoardId());
            if(mode!=null&&mode.equals("children")) {
                BoardComment.BoardCommentDto parent = commentService.findBoardCommentById(request.getCommentId());
                commentService.createChildrenComment(request.getCommentId(),BoardComment.BoardCommentDto.from(request, UserAccount.UserAccountDto.from(principalDto),boardDto));
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                commentService.createBoardComment(BoardComment.BoardCommentDto.from(request, UserAccount.UserAccountDto.from(principalDto), boardDto));
                return new ResponseEntity<>("success", HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/comment.df")
    public ResponseEntity<?> deleteBoardComment(@RequestParam Long commentId){
        try {
            commentService.deleteBoardComment(commentId);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/comment.df")
    public ResponseEntity<?> updateBoardComment(@RequestBody BoardComment.BoardCommentRequest request, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto){
        try {
            if(principalDto==null){
                return new ResponseEntity<>("failed",HttpStatus.UNAUTHORIZED);
            }
            commentService.updateBoardComment(request.getCommentId(),request,principalDto.getUsername());
            return new ResponseEntity<>("success",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/board/{boardId}.df")
    public ModelAndView getBoardDetails(@PathVariable Long boardId,
                                        HttpServletRequest req) {
        try {
            ModelAndView mav = new ModelAndView("/board/boardDetails");
            if(!redisService.checkViewLog(req.getRemoteAddr(),boardId)){
                redisService.saveViewLog(req.getRemoteAddr(),boardId);
                boardService.increaseViewCount(boardId);
            }
            Board.BoardResponse boardResponse = Board.BoardResponse.from(boardService.getBoardDetail(boardId));
            mav.addObject("article", boardResponse);
            mav.addObject("boardType", boardResponse.getBoardType().toString());
            mav.addObject("bestArticles", boardService.getBestBoard(null).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
            mav.addObject("likeLog",redisService.checkLikeLog(req.getRemoteAddr(),boardId));
            return mav;
        }
        catch (Exception e){
            return new ModelAndView("redirect:/board.df");
        }
    }


    @GetMapping("/board/insert.df")
    public ModelAndView getBoardInsert(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                       @RequestParam (required = true) String request,
                                       @RequestParam (required = false) String id,
                                       Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ModelAndView("redirect:/board.df");
        }else if(request==null){
            return new ModelAndView("redirect:/board.df");
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
                sb.append("#");
                sb.append(hashtag.getName());
                sb.append(" ");
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
        try {
            Board.BoardDto boardDto = boardService.getBoardDetail(id);
            if (principalDto == null || !boardDto.getUserAccount().userId().equals(principalDto.getUsername())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            boardDto.getBoardFiles().forEach(saveFile -> {
                log.info("saveFile : {}", saveFile);
                saveFileService.deleteFile(saveFile.id());
            });
            boardService.deleteBoardById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/board.df")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(boardRequest);
        if(boardRequest.getServerId().equals("")){
            return new ResponseEntity<>(boardService.createBoard(Board.BoardDto.from(boardRequest, UserAccount.UserAccountDto.from(principalDto)),set,null).getId(),HttpStatus.OK);
        }
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(boardRequest.getServerId(),boardRequest.getCharacterId());
        return new ResponseEntity<>(boardService.createBoard(Board.BoardDto.from(boardRequest, UserAccount.UserAccountDto.from(principalDto)),set,character).getId(),HttpStatus.OK);
    }

    @PutMapping("/api/board.df")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody Board.BoardRequest updateRequest) {
        try{
        Board.BoardDto dto_ = boardService.getBoardDetail(updateRequest.getId());
        if(principalDto==null || !dto_.getUserAccount().userId().equals(principalDto.getUsername())){
            return new ResponseEntity<>("로그인이 필요하거나 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(updateRequest);
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(updateRequest.getServerId(),updateRequest.getCharacterId());
            return new ResponseEntity<>(boardService.updateBoard(updateRequest.getId(),Board.BoardDto.from(updateRequest, UserAccount.UserAccountDto.from(principalDto)),set,character).getId(),HttpStatus.OK);
    }catch (Exception e){
            log.error("error : {}",e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
}
