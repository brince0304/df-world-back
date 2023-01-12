package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    @Query("select b from BoardComment b where b.id=:id and b.isDeleted='N'")
    BoardComment findBoardCommentById(Long id);

    @Query("select b from BoardComment b where b.isDeleted='N' and b.board.id=:boardId")
    List<BoardComment> findBoardCommentByBoardId(@Param("boardId") Long id);
    @Query("select b from BoardComment b where b.isDeleted='N' and b.userAccount.userId=:userAccountId")
    List<BoardComment> findBoardCommentByUserAccount_UserId(String userAccountId);

    @Query ("select b from BoardComment b where b.isDeleted='N' and b.board.id=:boardId and b.id=:parentCommentId")
    List<BoardComment> findBoardCommentByParentCommentId(Long boardId, Long parentCommentId);

    @Query ("select '*' from BoardComment b where b.board.id=:boardId and b.commentLikeCount >= 10 order by b.commentLikeCount desc")
    List<BoardComment> findBoardCommentByLikeCount(@Param("boardId") Long boardId);
}

