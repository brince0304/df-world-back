package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CharacterEntityCustomRepository {
    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByAdventureFame();

    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByDamageIncrease();

    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByBuffPower();

    Page<CharacterEntity.CharacterEntityResponse> findAllByAdventureNameContaining(String adventureName, Pageable pageable);

    Long getBoardCountByCharacterId(String characterId);

    Long getRankByCharacterId(String characterId);

    Long getRankByCharacterId(String characterId,String jobName);
    Long getCharacterCountByJobName(String jobName);

    List<CharacterEntity> findAllByAdventureName(String adventureName);
}
