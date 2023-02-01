package com.dfoff.demo.Controller;

import com.dfoff.demo.Annotation.Auth;
import com.dfoff.demo.Annotation.BindingErrorCheck;
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
    public ResponseEntity<?> likeComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal,@RequestParam Long commentId, @RequestParam Long boardId, HttpServletRequest req) {
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

    @Auth
    @BindingErrorCheck
    @PostMapping("/comments")
    public ResponseEntity<?> createBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal,@RequestBody @Valid BoardComment.BoardCommentRequest request, BindingResult bindingResult,
                                                @RequestParam(required = false) String mode) {
        Board.BoardDto boardDto = boardService.getBoardDto(request.boardId());
        if (mode != null && mode.equals("children")) {
            BoardComment.BoardCommentDto commentDto =commentService.createChildrenComment(request.commentId(), request, UserAccount.UserAccountDto.from(principal), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principal.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principal.getNickname(),NotificationType.CHILDREN_COMMENT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            BoardComment.BoardCommentDto commentDto = commentService.createBoardComment(request, UserAccount.UserAccountDto.from(principal), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principal.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principal.getNickname(),NotificationType.COMMENT);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }


    @Auth
    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestParam Long commentId) {
        String id = commentService.getCommentAuthor(commentId);
        if (!id.equals(principal.getUsername())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }
        List<BoardComment.BoardCommentDto> children = commentService.getChildrenComments(commentId);
        children.stream().filter(o-> !Objects.equals(o.getUserId(), principal.getUsername())).forEach(o-> notificationService.saveBoardCommentNotification(o.getUserAccountDto(), o, principal.getNickname(),NotificationType.DELETE_CHILDREN_COMMENT));
        commentService.deleteBoardComment(commentId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @Auth
    @BindingErrorCheck
    @PutMapping("/comments")
    public ResponseEntity<?> updateBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal,@RequestBody @Valid BoardComment.BoardCommentRequest request, BindingResult bindingResult) {
        commentService.updateBoardComment(request.commentId(), request, principal.getUsername());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
