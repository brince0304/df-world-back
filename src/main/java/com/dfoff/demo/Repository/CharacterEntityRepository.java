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

    @Query("select count(b) from Board b  inner join b.character c on b.character.characterId=c.characterId where b.isDeleted='N' and c.characterId=:characterId")
    Long getBoardCountByCharacterId(String characterId);





}

