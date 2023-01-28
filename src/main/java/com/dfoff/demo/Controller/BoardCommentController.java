package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.BoardCommentService;
import com.dfoff.demo.Service.BoardService;
import com.dfoff.demo.Service.NotificationService;
import com.dfoff.demo.Service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService commentService;

    private final RedisService redisService;
    private final BoardService boardService;

    private final NotificationService notificationService;

    @GetMapping("/comments/")
    public ResponseEntity<?> getBoardComments(HttpServletRequest req,
                                              @RequestParam(required = false) Long boardId,
                                              @RequestParam(required = false) Long commentId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Boolean> likeMap = new HashMap<>();
        List<BoardComment.BoardCommentResponse> bestComments = commentService.findBestBoardCommentByBoardId(boardId);
        map.put("bestComments", bestComments);
        List<BoardComment.BoardCommentResponse> comments;
        if (commentId == null) {
            comments = commentService.findBoardCommentByBoardId(boardId);
        } else {
            comments = commentService.findBoardCommentsByParentId(boardId, commentId);
        }
        return getCommentResponse(req, boardId, map, likeMap, comments, bestComments);
    }

    private ResponseEntity<?> getCommentResponse(HttpServletRequest req, @RequestParam(required = false) Long boardId, Map<String, Object> map, Map<String, Boolean> likeMap, List<BoardComment.BoardCommentResponse> comments
            , List<BoardComment.BoardCommentResponse> bestComments) {
        map.put("comments", comments);
        comments.forEach(o -> {
            likeMap.put(String.valueOf(o.getId()), redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId, o.getId()));
        });
        bestComments.forEach(o -> {
            likeMap.put(String.valueOf(o.getId()), redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId, o.getId()));
        });
        map.put("likeMap", likeMap);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/comments/like-comment")
    public ResponseEntity<?> likeComment(@RequestParam Long commentId, @RequestParam Long boardId, HttpServletRequest req,@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
            if (redisService.checkBoardCommentLikeLog(req.getRemoteAddr(), boardId, commentId)) {
                redisService.deleteBoardCommentLikeLog(req.getRemoteAddr(), boardId, commentId);
                return new ResponseEntity<>(commentService.updateBoardCommentDisLike(commentId).getCommentLikeCount(), HttpStatus.OK);

            } else {
                redisService.saveBoardCommentLikeLog(req.getRemoteAddr(), boardId, commentId);
                BoardComment.BoardCommentDto dto = commentService.updateBoardCommentLike(commentId);
                if(principal!=null && !principal.getUsername().equals(dto.getUserId())){
                notificationService.saveBoardCommentNotification(dto.getUserAccountDto(), dto, "",NotificationType.COMMENT_LIKE);}
                return new ResponseEntity<>(dto.getCommentLikeCount(), HttpStatus.OK);
            }
    }

    @PostMapping("/comments")
    public ResponseEntity<?> createBoardComment(@RequestBody @Valid BoardComment.BoardCommentRequest request, BindingResult bindingResult, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                                @RequestParam(required = false) String mode) {
        if (principalDto == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Board.BoardDto boardDto = boardService.getBoardDto(request.getBoardId());
        if (mode != null && mode.equals("children")) {
            BoardComment.BoardCommentDto commentDto =commentService.createChildrenComment(request.getCommentId(), request, UserAccount.UserAccountDto.from(principalDto), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principalDto.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principalDto.getNickname(),NotificationType.CHILDREN_COMMENT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            BoardComment.BoardCommentDto commentDto = commentService.createBoardComment(request, UserAccount.UserAccountDto.from(principalDto), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principalDto.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principalDto.getNickname(),NotificationType.COMMENT);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }


    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto dto, @RequestParam Long commentId) {
        String id = commentService.getCommentAuthor(commentId);
        if (dto == null || !id.equals(dto.getUsername())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }
        List<BoardComment.BoardCommentDto> children = commentService.getChildrenComments(commentId);
        children.stream().filter(o-> !Objects.equals(o.getUserId(), dto.getUsername())).forEach(o-> notificationService.saveBoardCommentNotification(o.getUserAccountDto(), o, dto.getNickname(),NotificationType.DELETE_CHILDREN_COMMENT));
        commentService.deleteBoardComment(commentId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @PutMapping("/comments")
    public ResponseEntity<?> updateBoardComment(@RequestBody @Valid BoardComment.BoardCommentRequest request, BindingResult bindingResult, @AuthenticationPrincipal UserAccount.PrincipalDto principalDto) {

        if (principalDto == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        commentService.updateBoardComment(request.getCommentId(), request, principalDto.getUsername());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
