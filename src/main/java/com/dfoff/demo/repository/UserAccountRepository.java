package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.BoardComment;
import com.dfoff.demo.domain.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserAccountRepository  extends JpaRepository<UserAccount, String> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Modifying
    @Query("update Board b set b.deleted=true  where b.userAccount.userId=:id ")
    void deleteBoardByUserAccountId(@Param("id") String id);

    @Modifying
    @Query("update BoardComment b set b.deleted=true where b.userAccount.userId=:id")
    void deleteBoardCommentByUserAccountId(@Param("id") String id);


    @Query("select b from UserAccount c join Board b on b.userAccount.userId = c.userId where c.userId=:id order by b.createdAt desc")
    Page<Board> findBoardsByUserId(@Param("id") String id, Pageable pageable);

    @Query("select b from UserAccount c join Board b on b.userAccount.userId = c.userId where c.userId=:id order by b.boardLikeCount desc")
    Page<Board> findBoardsByUserIdOrderByLikeCount(@Param("id") String id, Pageable pageable);

    @Query("select b from UserAccount c join Board b on b.userAccount.userId = c.userId where c.userId=:id order by b.boardViewCount desc")
    Page<Board> findBoardsByUserIdOrderByViewCount(@Param("id") String id, Pageable pageable);

    @Query("select  b from UserAccount c join Board b on b.userAccount.userId = c.userId  join  BoardComment bc on bc.board.id= b.id where c.userId=:id group by bc.id  order by count(bc.id) desc")
    Page<Board> findBoardsByUserIdOrderByCommentCount(@Param("id") String id, Pageable pageable);
    @Query("select b from UserAccount c join BoardComment b on b.userAccount.userId = c.userId  where c.userId=:id  order by b.createdAt desc")
    Page<BoardComment> findBoardCommentsByUserId(@Param("id") String id, Pageable pageable);
    @Query("select b from UserAccount c join BoardComment b on b.userAccount.userId = c.userId where c.userId=:id order by b.commentLikeCount desc")
    Page<BoardComment> findBoardCommentsByUserIdOrderByLikeCount(@Param("id") String id, Pageable pageable);

}
