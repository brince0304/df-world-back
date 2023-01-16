package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardCommentService {
    private final BoardCommentRepository commentRepository;

    public BoardComment.BoardCommentDto findBoardCommentById(Long id){
        return BoardComment.BoardCommentDto.from(commentRepository.findBoardCommentById(id));
    }

    public List<BoardComment.BoardCommentDto> findBoardCommentByBoardId(Long id){
        return commentRepository.findBoardCommentByBoardId(id).stream().map(BoardComment.BoardCommentDto::from).toList();
    }

    public BoardComment.BoardCommentDto createBoardComment(BoardComment.BoardCommentDto boardComment){
        BoardComment boardComment_ = commentRepository.save(boardComment.toEntity());
        return BoardComment.BoardCommentDto.from(boardComment_);
    }

    public List<BoardComment.BoardCommentDto> findBoardCommentsByParentId(Long boardId,Long parentId){
        return commentRepository.findBoardCommentByParentCommentId(boardId,parentId).stream().map(BoardComment.BoardCommentDto::from).toList();
    }

    public void deleteBoardComment(Long id){
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

    public void updateBoardCommentLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()+1);
    }

    public void updateBoardCommentDisLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()-1);
    }


    public void createChildrenComment(Long parentId,BoardComment.BoardCommentDto dto) {
        BoardComment boardComment_ = commentRepository.findBoardCommentById(parentId);
        if(boardComment_== null){
            throw new EntityNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        BoardComment children = dto.toEntity();
        children.setIsParent("N");
        children.setParentComment(boardComment_);
        commentRepository.save(children);
    }
}
