package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CharacterEntityRepository  extends JpaRepository<CharacterEntity, String> {

    Page<CharacterEntity> findAllByCharacterNameContaining(String characterName, Pageable pageable);

    Page<CharacterEntity> findAllByAdventureNameContaining(String adventureName, Pageable pageable);

    Long countCharacterEntitiesByJobName(String jobName);

    @Query("select count(b) from Board b  inner join b.character c on b.character.characterId=c.characterId where b.deleted=false and c.characterId=:characterId")
    Long getBoardCountByCharacterId(String characterId);


    @Query("select c from CharacterEntity c order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFame(Pageable pageable);

    @Query("select c from CharacterEntity c where c.jobName = :jobName order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFameAndJobName(String jobName, Pageable pageable);

    @Query ("select c from CharacterEntity c where c.jobGrowName = :jobGrowName order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFameAndJobGrowName(String jobGrowName, Pageable pageable);
    @Query("select count(c) from CharacterEntity c where c.adventureFame > (select c2.adventureFame from CharacterEntity c2 where c2.characterId = :characterId)")
    Long getRankByCharacterId(String characterId);
    @Query("select count(c) from CharacterEntity c where c.jobName = :jobName and c.adventureFame > (select c2.adventureFame from CharacterEntity c2 where c2.characterId = :characterId)")
    Long getRankByCharacterId(String characterId,String jobName);

    //해당 캐릭터가 전체 캐릭터중에 명성이 몇번째인지 퍼센트로 알아내는 쿼리

    @Query("select c from CharacterEntity  c order by c.adventureFame desc")
    Page<CharacterEntity> getCharacterEntitiesOrderByAdventureFame(Pageable pageable);

    @Query("select c from CharacterEntity  c where c.jobName = :jobName order by c.adventureFame desc")
    Page<CharacterEntity> getCharacterEntitiesOrderByAdventureFameAndJobName(String jobName, Pageable pageable);

    @Query("select count(c) from CharacterEntity c where c.jobGrowName = :jobGrowName")
    Long getCharacterCountByJobGrowName(String jobGrowName);

    @Query("select count(c) from CharacterEntity c where c.jobName = :jobName")
    Long getCharacterCountByJobName(String jobName);
    @Query("select count(c) from CharacterEntity c")
    Long getCharacterCount();








}

