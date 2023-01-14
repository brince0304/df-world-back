package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.id=:id and b.isDeleted='N'")
    Board findBoardById(@Param("id") Long id);
    @Query("select b from Board b where b.isDeleted='N'")
    Page<Board> findAll(Pageable pageable);
    @Query("select b from Board b where b.boardType=:boardType and b.isDeleted='N' and b.boardContent like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByTypeAndBoardContentContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.boardTitle) like lower (concat('%',:keyword,'%')) and b.isDeleted='N' and b.boardType=:boardType")
    Page<Board> findAllByTypeAndBoardTitleContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.isDeleted='N' and lower(b.boardTitle) like lower (concat('%',:keyword,'%')) or b.boardContent like lower (concat('%',:keyword,'%')) and b.boardType=:boardType")
    Page<Board> findAllByTypeAndBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.userAccount.nickname) like lower (concat('%',:keyword,'%')) and b.isDeleted='N' and b.boardType=:boardType")
    Page<Board> findAllByTypeAndUserAccountContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);

    @Query("select b from Board b where  b.isDeleted='N' and lower(b.boardContent) like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByBoardContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.boardTitle) like lower (concat('%',:keyword,'%')) and b.isDeleted='N'")
    Page<Board> findAllByBoardTitleContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.isDeleted='N' and lower(b.boardTitle) like lower (concat('%',:keyword,'%')) or b.boardContent like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.userAccount.nickname) like lower (concat('%',:keyword,'%')) and b.isDeleted='N'")
    Page<Board> findAllByUserAccountContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query("select b from Board b where b.isDeleted='N' and b.boardType=:boardType")
    Page<Board> findAllByBoardType(@Param("boardType") BoardType boardType, Pageable pageable);

    @Query("select b from Board b where b.isDeleted='N' and b.boardType=:boardType and b.createdAt between  :startDate and :endDate and b.boardLikeCount >=10 order by b.boardLikeCount desc")
    List<Board> findBoardByLikeCountAndBoardType(@Param("boardType") BoardType boardType,@Param("startDate") LocalDateTime sDate ,@Param("endDate") LocalDateTime eDate);

    @Query("select b from Board b where b.isDeleted='N' and  b.createdAt between :startDate and :endDate and b.boardLikeCount >=10 order by b.boardLikeCount desc")
    List<Board> findBoardByLikeCount(@Param("startDate") LocalDateTime sDate , @Param("endDate") LocalDateTime eDate);

    @Modifying
    @Query("update Board b set b.isDeleted='Y' where b.id=:id")
    void deleteBoardById(@Param("id") Long id);





}
