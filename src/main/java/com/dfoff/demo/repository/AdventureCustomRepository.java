package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Adventure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdventureCustomRepository {
    List<Adventure.AdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureFame();

    List<Adventure.AdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower();

    Boolean checkAdventureHasUserAccount(String adventureName);

    Page<Adventure.AdventureRankingResponse> getAdventureRankrderByAdventureFame(Pageable pageable, String adventureName);

    Page<Adventure.AdventureRankingResponse> getAdventureRankrderByAdventureDamageIncreaseBuffPower(Pageable pageable, String adventureName);

    Long getRankCountBAdventureNameOrderByAdventureFame(String adventureName);

    Long getRankCountBAdventureNameOrderByAdventureDamageIncreaseBuffPower(String adventureName);


}
