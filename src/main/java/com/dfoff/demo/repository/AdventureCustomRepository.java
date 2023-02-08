package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Adventure;

import java.util.List;

public interface AdventureCustomRepository {
    List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureFame();

    List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower();

    Boolean checkAdventureHasUserAccount(String adventureName);

}
