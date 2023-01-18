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

import java.util.Collection;
import java.util.List;

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
        if(request.getCommentContent() == null || request.getCommentContent().equals("")){
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }
        commentRepository.save(request.toEntity(account, board));
    }

    public List<BoardComment.BoardCommentResponse> findBoardCommentsByParentId(Long boardId, Long parentId){
        return commentRepository.findBoardCommentByParentCommentId(boardId,parentId).stream().map(BoardComment.BoardCommentResponse::from).toList();
    }

    public void deleteBoardComment(Long id){
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

    public Integer updateBoardCommentLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()+1);
        return boardComment_.getCommentLikeCount();
    }

    public Integer updateBoardCommentDisLike(Long id){
        BoardComment boardComment_ = commentRepository.findBoardCommentById(id);
        boardComment_.setCommentLikeCount(boardComment_.getCommentLikeCount()-1);
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
