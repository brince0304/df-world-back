package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardHashtagMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

public interface BoardHashtagMapperRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.Domain.BoardHashtagMapper, Long> {
   @Query("select bhm from BoardHashtagMapper bhm where bhm.hashtag.name = :name and bhm.board.deleted=false")
    Page<BoardHashtagMapper> findAllByHashtagName(String name, org.springframework.data.domain.Pageable pageable);

    @Query("select bhm from BoardHashtagMapper bhm where bhm.hashtag.name=:name and bhm.board.deleted=false and bhm.board.boardType=:boardType")
    Page<BoardHashtagMapper> findAllByHashtagNameAndBoardType(String name, com.dfoff.demo.Domain.EnumType.BoardType boardType, org.springframework.data.domain.Pageable pageable);
}
