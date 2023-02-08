package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.id=:id and b.deleted=false")
    Optional<Board> findBoardById(@Param("id") Long id);
    @Query("select b from Board b where b.deleted=false ")
    Page<Board> findAll(Pageable pageable);
    @Query("select b from Board b where b.boardType=:boardType and b.deleted=false and b.boardContent like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByTypeAndBoardContentContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.boardTitle) like lower (concat('%',:keyword,'%')) and b.deleted=false and b.boardType=:boardType")
    Page<Board> findAllByTypeAndBoardTitleContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.deleted=false and lower(b.boardTitle) like lower (concat('%',:keyword,'%')) or b.boardContent like lower (concat('%',:keyword,'%')) and b.boardType=:boardType")
    Page<Board> findAllByTypeAndBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.userAccount.nickname) like lower (concat('%',:keyword,'%')) and b.deleted=false and b.boardType=:boardType")
    Page<Board> findAllByTypeAndUserAccountContainingIgnoreCase(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);

    @Query("select b from Board b where  b.deleted=false and lower(b.boardContent) like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByBoardContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.boardTitle) like lower (concat('%',:keyword,'%')) and b.deleted=false")
    Page<Board> findAllByBoardTitleContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where b.deleted=false and lower(b.boardTitle) like lower (concat('%',:keyword,'%')) or b.boardContent like lower (concat('%',:keyword,'%'))")
    Page<Board> findAllByBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Board b where lower(b.userAccount.nickname) like lower (concat('%',:keyword,'%')) and b.deleted=false")
    Page<Board> findAllByUserAccountContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query("select b from Board b where b.deleted=false and b.boardType=:boardType")
    Page<Board> findAllByBoardType(@Param("boardType") BoardType boardType, Pageable pageable);

    @Query("select b from Board b where b.deleted=false and lower(b.character.characterName) like (concat('%',:characterName,'%'))")
    Page<Board> findAllByCharacterName(@Param("characterName") String characterName, Pageable pageable);

    @Query("select b from Board b where b.deleted=false and b.boardType=:boardType and b.createdAt between  :startDate and :endDate and b.boardLikeCount >=10 order by b.boardLikeCount desc limit 5")
    List<Board> findBoardByLikeCountAndBoardType(@Param("boardType") BoardType boardType,@Param("startDate") LocalDateTime sDate ,@Param("endDate") LocalDateTime eDate);

    @Query("select b from Board b where b.deleted=false and  b.createdAt between :startDate and :endDate and b.boardLikeCount >=10 order by b.boardLikeCount desc limit 5")
    List<Board> findBoardByLikeCount(@Param("startDate") LocalDateTime sDate , @Param("endDate") LocalDateTime eDate);



    @Query("select b from Board b inner join b.boardComments c where b.deleted=false and c.userAccount.nickname=:userId or c.userAccount.userId=:userId")
    Page<Board> findBoardByCommentWriter(String userId, Pageable pageable);





}
