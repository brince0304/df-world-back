package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.UserAccount.LogType;
import com.dfoff.demo.Domain.Notification;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardCommentRepository;
import com.dfoff.demo.Repository.NotificationRepository;
import com.dfoff.demo.Util.UserLogUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardCommentService {
    private final BoardCommentRepository commentRepository;



    public BoardComment.BoardCommentResponse findBoardCommentById(Long id){
        if(!commentRepository.existsById(id)){
            throw new EntityNotFoundException("존재하지 않는 댓글입니다.");
        }
        return BoardComment.BoardCommentResponse.from(commentRepository.findBoardCommentById(id));
    }



    public List<BoardComment.BoardCommentResponse> findBoardCommentByBoardId(Long id){
        return commentRepository.findBoardCommentByBoardId(id).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public void createBoardComment(BoardComment.BoardCommentRequest request, UserAccount.UserAccountDto account, Board.BoardDto board){
        if(account ==null || board ==null){
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        if(request.getCommentContent() == null || request.getCommentContent().equals("")){
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }

        BoardComment comment_ = commentRepository.save(request.toEntity(account, board));
        if(!account.userId().equals(board.getUserAccount().userId())) {
            comment_.getBoard().getUserAccount().getNotifications().add(Notification.of(comment_.getBoard().getUserAccount(), comment_.getBoard(), LogType.COMMENT, UserLogUtil.getLogContent(LogType.COMMENT.name(), account.nickname())));
        }
    }

    public List<BoardComment.BoardCommentResponse> findBoardCommentsByParentId(Long boardId, Long parentId){
        return commentRepository.findBoardCommentByParentCommentId(boardId,parentId).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public void deleteBoardComment(Long id){
        if(!commentRepository.existsById(id)){
            throw new EntityNotFoundException("존재하지 않는 댓글입니다.");
        }
        commentRepository.findById(id).ifPresent(comment -> {
            comment.getChildrenComments().forEach(child -> {
                if(!Objects.equals(comment.getUserAccount().getUserId(), child.getUserAccount().getUserId())) {
                    comment.getBoard().getUserAccount().getNotifications().add(Notification.of(comment.getUserAccount(), child, LogType.DELETE_CHILDREN_COMMENT, UserLogUtil.getLogContent(LogType.DELETE_CHILDREN_COMMENT.name(), comment.getUserAccount().getNickname())));
                }
            });
        });
        commentRepository.deleteChildrenCommentById(id);
        commentRepository.deleteBoardCommentById(id);

    }

    public void updateBoardComment(Long id, BoardComment.BoardCommentRequest request,String username){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        if(boardComment_== null){
            throw new EntityNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        if(!boardComment_.getUserAccount().getUserId().equals(username)){
            throw new IllegalArgumentException("해당 댓글을 수정할 권한이 없습니다.");
        }
        boardComment_.setCommentContent(request.getCommentContent());
    }

    public Integer updateBoardCommentLike(Long id,String nickname){
        if(nickname.equals("")){nickname="비회원";}
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()+1);
        boardComment_.getBoard().getUserAccount().getNotifications().add(Notification.of(boardComment_.getUserAccount(),boardComment_, LogType.COMMENT_LIKE,UserLogUtil.getLogContent(LogType.COMMENT_LIKE.name(),nickname)));
        return boardComment_.getCommentLikeCount();
    }

    public Integer updateBoardCommentDisLike(Long id,String nickname){
        if(nickname.equals("")){nickname="비회원";}
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()-1);
        boardComment_.getBoard().getUserAccount().getNotifications().add(Notification.of(boardComment_.getUserAccount(),boardComment_, LogType.COMMENT_UNLIKE,UserLogUtil.getLogContent(LogType.COMMENT_UNLIKE.name(),nickname)));
        return boardComment_.getCommentLikeCount();
    }



    public void createChildrenComment(Long parentId, BoardComment.BoardCommentRequest request, UserAccount.UserAccountDto account, Board.BoardDto board) {
        BoardComment boardComment_ = commentRepository.findBoardCommentById(parentId);
        if(boardComment_== null){
            throw new EntityNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        BoardComment children = request.toEntity(account,board);
        children.setIsParent("N");
        children.setParentComment(boardComment_);
        commentRepository.save(children);
        if(!account.userId().equals(board.getUserAccount().userId())) {
            boardComment_.getUserAccount().getNotifications().add(Notification.of(boardComment_.getUserAccount(),children, LogType.CHILDREN_COMMENT,UserLogUtil.getLogContent(LogType.CHILDREN_COMMENT.name(),account.nickname())));
        }
    }

    public List<BoardComment.BoardCommentResponse> findBestBoardCommentByBoardId(Long boardId) {
        return commentRepository.findBoardCommentByLikeCount(boardId).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public String getCommentAuthor(Long id) {
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        if(boardComment_== null){
            throw new EntityNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        return boardComment_.getUserAccount().getUserId();
    }
}
