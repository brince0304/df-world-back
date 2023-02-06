package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Adventure;

import java.util.List;

public interface AdventureCustomRepository {
    List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureFame();

    List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower();

    Boolean checkAdventureHasUserAccount(String adventureName);

}
