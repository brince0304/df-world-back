package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Domain.Notification;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardCommentRepository;
import com.dfoff.demo.Repository.NotificationRepository;
import com.dfoff.demo.Util.NotificationUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardCommentService {
    private final BoardCommentRepository commentRepository;



    public BoardComment.BoardCommentResponse findBoardCommentById(Long id){
        return BoardComment.BoardCommentResponse.from(commentRepository.findBoardCommentById(id).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다.")));
    }



    public List<BoardComment.BoardCommentResponse> findBoardCommentByBoardId(Long id){
        return commentRepository.findBoardCommentByBoardId(id).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public BoardComment.BoardCommentDto createBoardComment(BoardComment.BoardCommentRequest request, UserAccount.UserAccountDto account, Board.BoardDto board){
        if(request.commentContent() == null || request.commentContent().equals("")){
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }
        BoardComment comment_ = commentRepository.save(request.toEntity(account, board));
        return BoardComment.BoardCommentDto.from(comment_);
    }

    public List<BoardComment.BoardCommentResponse> findBoardCommentsByParentId(Long boardId, Long parentId){
        return commentRepository.findBoardCommentByParentCommentId(boardId,parentId).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public void deleteBoardComment(Long id){
        commentRepository.deleteById(id);
    }

    public List<BoardComment.BoardCommentDto> getChildrenComments(Long parentId){
        BoardComment comment_ = commentRepository.findBoardCommentById(parentId).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        return comment_.getChildrenComments().stream().map(BoardComment.BoardCommentDto::from).toList();
    }

    public void updateBoardComment(Long id, BoardComment.BoardCommentRequest request){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        boardComment_.setCommentContent(request.commentContent());
    }

    public BoardComment.BoardCommentDto updateBoardCommentLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()+1);
        return BoardComment.BoardCommentDto.from(boardComment_);
    }

    public BoardComment.BoardCommentDto updateBoardCommentDisLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()-1);
        return BoardComment.BoardCommentDto.from(boardComment_);
    }



    public BoardComment.BoardCommentDto createChildrenComment(Long parentId, BoardComment.BoardCommentRequest request, UserAccount.UserAccountDto account, Board.BoardDto board) {
        BoardComment boardComment_ = commentRepository.findBoardCommentById(parentId).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        BoardComment children = request.toEntity(account,board);
        children.setIsParent(Boolean.FALSE);
        children.setParentComment(boardComment_);
        commentRepository.save(children);
        return BoardComment.BoardCommentDto.from(children);
    }

    public List<BoardComment.BoardCommentResponse> findBestBoardCommentByBoardId(Long boardId) {
        return commentRepository.findBoardCommentByLikeCount(boardId).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public String getCommentAuthor(Long id) {
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        return boardComment_.getUserAccount().getUserId();
    }
}
