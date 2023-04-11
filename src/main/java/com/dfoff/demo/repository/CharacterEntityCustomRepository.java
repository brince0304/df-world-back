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

    Long getRankCountByCharacterIdOrderByAdventureFame(String characterId);

    Long getRankCountByCharacterIdOrderByDamageIncrease(String characterId);

    Long getRankCountByCharacterIdOrderByBuffpower(String characterId);

        Long getRankCountByCharacterIdAndJobNameOrderByAdventureFame(String characterId,String jobName);
    Long getCharacterCountByJobName(String jobName);

    List<CharacterEntity> findAllByAdventureName(String adventureName);

    Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByAdventureFame(String characterName, Pageable pageable);

    Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByDamageIncrease(String characterName, Pageable pageable);

    Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByBuffPower(String characterName, Pageable pageable);

    List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoCompleteServerAll(String characterName);

    List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoComplete(String characterName, String serverId);

    List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoCompleteServerAdventure(String name);
}
