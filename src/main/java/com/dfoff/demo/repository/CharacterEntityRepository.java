package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CharacterEntityRepository  extends JpaRepository<CharacterEntity, String>, CharacterEntityCustomRepository {







    @Query("select c from CharacterEntity c order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFame(Pageable pageable);

    @Query("select c from CharacterEntity c where c.jobName = :jobName order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFameAndJobName(String jobName, Pageable pageable);

    @Query ("select c from CharacterEntity c where c.jobGrowName = :jobGrowName order by c.adventureFame desc")
    Page<CharacterEntity> findAllByAdventureFameAndJobGrowName(String jobGrowName, Pageable pageable);


    @Query("select c from CharacterEntity  c order by c.adventureFame desc")
    Page<CharacterEntity> getCharacterEntitiesOrderByAdventureFame(Pageable pageable);

    @Query("select c from CharacterEntity  c where c.jobName = :jobName order by c.adventureFame desc")
    Page<CharacterEntity> getCharacterEntitiesOrderByAdventureFameAndJobName(String jobName, Pageable pageable);














}

