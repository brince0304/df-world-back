package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterEntityRepository  extends JpaRepository<CharacterEntity, String> {

    Page<CharacterEntity> findAllByCharacterNameContaining(String characterName, Pageable pageable);

    Page<CharacterEntity> findAllByAdventureNameContaining(String adventureName, Pageable pageable);

    Long countCharacterEntitiesByJobName(String jobName);





}

