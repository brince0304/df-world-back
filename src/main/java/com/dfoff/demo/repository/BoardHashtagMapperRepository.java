package com.dfoff.demo.repository;

import com.dfoff.demo.domain.BoardHashtagMapper;
import com.dfoff.demo.domain.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardHashtagMapperRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.domain.BoardHashtagMapper, Long> {
   @Query("select bhm from BoardHashtagMapper bhm where bhm.hashtag.name = :name and bhm.board.deleted=false")
    Page<BoardHashtagMapper> findAllByHashtagName(@Param("name") String name, Pageable pageable);

    @Query("select bhm from BoardHashtagMapper bhm where bhm.hashtag.name=:name and bhm.board.deleted=false and bhm.board.boardType=:boardType")
    Page<BoardHashtagMapper> findAllByHashtagNameAndBoardType(@Param("name") String name,@Param("boardType") BoardType boardType, Pageable pageable);


    Long countAllByHashtagName(String name);
}
