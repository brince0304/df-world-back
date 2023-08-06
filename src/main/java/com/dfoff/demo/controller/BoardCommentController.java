package com.dfoff.demo.controller;

import com.dfoff.demo.annotation.Auth;
import com.dfoff.demo.annotation.BindingErrorCheck;
import com.dfoff.demo.annotation.CommentCheck;
import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.BoardComment;
import com.dfoff.demo.domain.enums.useraccount.NotificationType;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.service.BoardCommentService;
import com.dfoff.demo.service.BoardService;
import com.dfoff.demo.service.NotificationService;
import com.dfoff.demo.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                                              @RequestParam(required = true) Long boardId) {
        Map<String, Object> map = new HashMap<>();
        List<BoardComment.BoardCommentLikeResponse> likeResponses = new ArrayList<>();
        List<BoardComment.BoardCommentResponse> bestComments = commentService.findBestBoardCommentByBoardId(boardId);
        List<BoardComment.BoardCommentResponse> comments;
            comments = commentService.findBoardCommentByBoardId(boardId);
        return getCommentResponse(req, boardId, map, likeResponses, comments, bestComments);
    }

    @GetMapping("/comments/{boardId}/{id}")
    public ResponseEntity<?> getChildrenComments(HttpServletRequest req,
                                             @PathVariable Long id,
                                                @PathVariable Long boardId) {
        return new ResponseEntity<>(commentService.findBoardCommentsByParentId(boardId,id), HttpStatus.OK);
    }

    private ResponseEntity<?> getCommentResponse(HttpServletRequest req, @RequestParam(required = false) Long boardId, Map<String, Object> map, List<BoardComment.BoardCommentLikeResponse> likeResponses, List<BoardComment.BoardCommentResponse> comments
            , List<BoardComment.BoardCommentResponse> bestComments) {
        map.put("comments", comments);
        comments.forEach(comment -> {
            likeResponses.add(BoardComment.BoardCommentLikeResponse.from(comment.getId(),redisService.checkBoardCommentLikeLog(req.getRemoteAddr(),boardId,comment.getId())));
        });
        comments.forEach(comment ->{
            commentService.getChildrenComments(comment.getId()).forEach(children->{
                likeResponses.add(BoardComment.BoardCommentLikeResponse.from(children.getId(),redisService.checkBoardCommentLikeLog(req.getRemoteAddr(),boardId,children.getId())));
            });
            }
        );

        map.put("bestComments", bestComments);


        map.put("likeResponses", likeResponses);
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
                                                @RequestParam(required = false) String parentId) {
        Board.BoardDto boardDto = boardService.getBoardDto(request.boardId());
        BoardComment.BoardCommentDto commentDto;
        if (parentId != null) {
            commentDto = commentService.createChildrenComment(request.commentId(), request, UserAccount.UserAccountDto.from(principal), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principal.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principal.getNickname(),NotificationType.CHILDREN_COMMENT);
            }
        } else {
            commentDto = commentService.createBoardComment(request, UserAccount.UserAccountDto.from(principal), boardDto);
            if(!commentDto.getBoardDto().getUserAccount().userId().equals(principal.getUsername())){
                notificationService.saveBoardCommentNotification(commentDto.getBoardDto().getUserAccount(), commentDto, principal.getNickname(),NotificationType.COMMENT);
            }
        }
        return new ResponseEntity<>(commentDto.getId(),HttpStatus.OK);
    }


    @Auth
    @CommentCheck
    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestParam Long commentId) {
        List<BoardComment.BoardCommentDto> children = commentService.getChildrenComments(commentId);
        children.stream().filter(o-> !Objects.equals(o.getUserId(), principal.getUsername())).forEach(o-> notificationService.saveBoardCommentNotification(o.getUserAccountDto(), o, principal.getNickname(),NotificationType.DELETE_CHILDREN_COMMENT));
        commentService.deleteBoardComment(commentId);
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @Auth
    @BindingErrorCheck
    @CommentCheck
    @PutMapping("/comments")
    public ResponseEntity<?> updateBoardComment(@AuthenticationPrincipal UserAccount.PrincipalDto principal,@RequestBody @Valid BoardComment.BoardCommentRequest request, BindingResult bindingResult) {
        commentService.updateBoardComment(request.commentId(), request);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
