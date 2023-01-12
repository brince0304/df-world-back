package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.id=:id and b.isDeleted='N'")
    Board findBoardById(@Param("id") Long id);
    @Query("select b from Board b where b.isDeleted='N'")
    Page<Board> findAll(Pageable pageable);
    @Query("select b from Board b where b.isDeleted='N' and b.boardTitle like %:title%")
    Page<Board> findAllByBoardContentContainingIgnoreCase(@Param("title") String keyword, Pageable pageable);
    @Query("select b from Board b where b.boardContent like %:keyword% and b.isDeleted='N'")
    Page<Board> findAllByBoardTitleContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.isDeleted='N' and b.boardTitle like %:keyword% or b.boardContent like %:keyword%")
    Page<Board> findAllByBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.userAccount.userId like %:userId% and b.isDeleted='N'")
    Page<Board> findAllByUserAccountContainingIgnoreCase (@Param("userId") String keyword, Pageable pageable);

    @Query("select b from Board b where b.isDeleted='N' and b.boardType=:boardType")
    Page<Board> findAllByBoardType(@Param("boardType") BoardType boardType, Pageable pageable);

    @Query("select '*' from Board b where b.isDeleted='N' and b.boardType=:boardType and b.createdAt between :startDate and :endDate and b.boardLikeCount >=10 order by b.boardLikeCount desc")
    List<Board> findBoardByLikeCount(@Param("boardType") BoardType boardType,@Param("startDate") Date sDate ,@Param("endDate") Date eDate);

}
