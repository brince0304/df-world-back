package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Adventure;
import com.dfoff.demo.Domain.QAdventure;
import com.dfoff.demo.Domain.QCharacterEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class AdventureRepositoryImpl implements AdventureCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AdventureRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QAdventure q = QAdventure.adventure;



    @Override
    public List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureFame() {
        return queryFactory.selectFrom(q)
                .orderBy(q.adventureFame.desc())
                .limit(5L)
                .fetch().stream().map(Adventure.UserAdventureMainPageResponse::from).toList();
    }

    @Override
    public List<Adventure.UserAdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower() {
        return queryFactory.selectFrom(q)
                .orderBy(q.adventureDamageIncreaseAndBuffPower.desc())
                .limit(5L)
                .fetch().stream().map(Adventure.UserAdventureMainPageResponse::from).toList();
    }

    @Override
    public Boolean checkAdventureHasUserAccount(String adventureName) {
        return queryFactory.selectFrom(q)
                .where(q.adventureName.eq(adventureName),q.userAccount.isNotNull())
                .fetchOne() != null;
    }


}
