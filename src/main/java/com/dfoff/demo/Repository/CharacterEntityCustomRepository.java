package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CharacterEntityCustomRepository {
    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByAdventureFame();

    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByDamageIncrease();

    List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByBuffPower();

    Page<CharacterEntity.CharacterEntityDto.CharacterEntityResponse> findAllByAdventureNameContaining(String adventureName, Pageable pageable);

    Long getBoardCountByCharacterId(String characterId);

    Long getRankByCharacterId(String characterId);

    Long getRankByCharacterId(String characterId,String jobName);
    Long getCharacterCountByJobName(String jobName);

    List<CharacterEntity> findAllByAdventureName(String adventureName);
}
