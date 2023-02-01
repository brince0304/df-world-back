package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    @Query("select b from BoardComment b where b.id=:id and b.deleted=false")
    Optional<BoardComment> findBoardCommentById(@Param("id") Long id);

    @Query("select b from BoardComment b where b.deleted=false and b.isParent = true  and b.board.id=:boardId order by b.createdAt asc")
    List<BoardComment> findBoardCommentByBoardId(@Param("boardId") Long id);
    @Query("select b from BoardComment b where b.deleted=false and b.userAccount.userId=:userAccountId")
    List<BoardComment> findBoardCommentByUserAccount_UserId(String userAccountId);

    @Query ("select b  from BoardComment b where b.parentComment.id =:parentCommentId and b.deleted=false and b.board.id=:boardId order by b.createdAt asc")
    List<BoardComment> findBoardCommentByParentCommentId(@Param("boardId") Long boardId,@Param("parentCommentId")  Long parentCommentId);

    @Query ("select b from BoardComment b where b.board.id=:boardId and b.deleted=false and b.commentLikeCount >= 10 order by b.commentLikeCount desc limit 3")
    List<BoardComment> findBoardCommentByLikeCount(@Param("boardId") Long boardId);








}

