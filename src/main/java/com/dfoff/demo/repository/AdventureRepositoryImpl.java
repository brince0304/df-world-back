package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Adventure;
import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.QAdventure;
import com.dfoff.demo.domain.QCharacterEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AdventureRepositoryImpl implements AdventureCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AdventureRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QAdventure q = QAdventure.adventure;



    @Override
    public List<Adventure.AdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureFame() {
        return queryFactory.selectFrom(q)
                .orderBy(q.adventureFame.desc())
                .limit(5L)
                .fetch().stream().map(Adventure.AdventureMainPageResponse::from).toList();
    }

    @Override
    public List<Adventure.AdventureMainPageResponse> getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower() {
        return queryFactory.selectFrom(q)
                .orderBy(q.adventureDamageIncreaseAndBuffPower.desc())
                .limit(5L)
                .fetch().stream().map(Adventure.AdventureMainPageResponse::from).toList();
    }

    @Override
    public Boolean checkAdventureHasUserAccount(String adventureName) {
        return queryFactory.selectFrom(q)
                .where(q.adventureName.eq(adventureName),q.userAccount.isNotNull())
                .fetchOne() != null;
    }

    @Override
    public Page<Adventure.AdventureRankingResponse> getAdventureRankrderByAdventureFame(Pageable pageable, String adventureName) {
        Long count = queryFactory.select(q.count())
                .from(q)
                .where(q.adventureName.contains(adventureName),q.adventureFame.gt(0))
                .fetchFirst();
        List<Adventure.AdventureRankingResponse> list = queryFactory.selectFrom(q)
                .where(q.adventureName.contains(adventureName),q.adventureFame.gt(0))
                .orderBy(q.adventureFame.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(Adventure.AdventureRankingResponse::from).peek(o->o.setAdventureRank(getRankCountBAdventureNameOrderByAdventureFame(o.getAdventureName()))).toList();
        return new PageImpl<>(list, pageable, count);
    }



    @Override
    public Page<Adventure.AdventureRankingResponse> getAdventureRankrderByAdventureDamageIncreaseBuffPower(Pageable pageable, String adventureName) {
        Long count = queryFactory.select(q.count())
                .from(q)
                .where(q.adventureName.contains(adventureName),q.adventureDamageIncreaseAndBuffPower.gt(0))
                .fetchFirst();
        List<Adventure.AdventureRankingResponse> list = queryFactory.selectFrom(q)
                .where(q.adventureName.contains(adventureName),q.adventureDamageIncreaseAndBuffPower.gt(0))
                .orderBy(q.adventureDamageIncreaseAndBuffPower.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(Adventure.AdventureRankingResponse::from).peek(o->o.setAdventureRank(getRankCountBAdventureNameOrderByAdventureDamageIncreaseBuffPower(o.getAdventureName()))).toList();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Long getRankCountBAdventureNameOrderByAdventureFame(String adventureName) {
        return  queryFactory.select(q.count())
                .from(q)
                .where(q.adventureFame.gt(queryFactory.select(q.adventureFame)
                        .from(q)
                        .where(q.adventureName.eq(adventureName))
                        .fetchFirst()),q.adventureFame.gt(0))
                .fetchFirst()+1;
    }

    @Override
    public Long getRankCountBAdventureNameOrderByAdventureDamageIncreaseBuffPower(String adventureName) {
        return  queryFactory.select(q.count())
                .from(q)
                .where(q.adventureDamageIncreaseAndBuffPower.gt(queryFactory.select(q.adventureDamageIncreaseAndBuffPower)
                        .from(q)
                        .where(q.adventureName.eq(adventureName))
                        .fetchFirst()),q.adventureFame.gt(0))
                .fetchFirst()+1;
    }



}
