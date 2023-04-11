package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.QBoard;
import com.dfoff.demo.domain.QCharacterEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterEntityRepositoryImpl implements CharacterEntityCustomRepository {
    private final JPAQueryFactory queryFactory;

    public CharacterEntityRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QCharacterEntity q = QCharacterEntity.characterEntity;

    QBoard b = QBoard.board;



    @Override
    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByAdventureFame() {
        return queryFactory.selectFrom(q)
                .orderBy(QCharacterEntity.characterEntity.adventureFame.desc())
                .where(q.adventureFame.gt(0))
                .limit(5L)
                .fetch().stream().map(CharacterEntity.CharacterEntityMainPageResponse::from).toList();
    }

    @Override
    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByDamageIncrease() {
        return queryFactory.selectFrom(q)
                .orderBy(QCharacterEntity.characterEntity.damageIncrease.desc())
                .where(q.damageIncrease.gt(0))
                .limit(5L)
                .fetch().stream().map(CharacterEntity.CharacterEntityMainPageResponse::from).toList();
    }

    @Override
    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByBuffPower() {
        return queryFactory.selectFrom(q)
                .orderBy(QCharacterEntity.characterEntity.buffPower.desc())
                .where(q.buffPower.gt(0))
                .limit(5L)
                .fetch().stream().map(CharacterEntity.CharacterEntityMainPageResponse::from).toList();
    }

    @Override
    public Page<CharacterEntity.CharacterEntityResponse> findAllByAdventureNameContaining(String adventureName, Pageable pageable) {
        List<CharacterEntity.CharacterEntityResponse> list  =queryFactory.selectFrom(QCharacterEntity.characterEntity)
                .where(q.adventureName.contains(adventureName))
                .orderBy(QCharacterEntity.characterEntity.adventureFame.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(CharacterEntity.CharacterEntityResponse::from).toList();

        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public Long getBoardCountByCharacterId(String characterId) {
        return queryFactory.select(b.count())
                .from(b)
                .join(b.character,q)
                .where(b.character.characterId.eq(characterId))
                .fetchFirst();
    }

    public Long getRankCountByCharacterIdOrderByAdventureFame(String characterId) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.adventureFame.gt(queryFactory.select(QCharacterEntity.characterEntity.adventureFame)
                        .from(QCharacterEntity.characterEntity)
                        .where(QCharacterEntity.characterEntity.characterId.eq(characterId))))
                .fetchFirst()+1;
    }

    public Long getRankCountByCharacterIdOrderByDamageIncrease(String characterId) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.damageIncrease.gt(queryFactory.select(QCharacterEntity.characterEntity.damageIncrease)
                        .from(QCharacterEntity.characterEntity)
                        .where(QCharacterEntity.characterEntity.characterId.eq(characterId))))
                .fetchFirst()+1;
    }

    public Long getRankCountByCharacterIdOrderByBuffpower(String characterId) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.buffPower.gt(queryFactory.select(QCharacterEntity.characterEntity.buffPower)
                        .from(QCharacterEntity.characterEntity)
                        .where(QCharacterEntity.characterEntity.characterId.eq(characterId))))
                .fetchFirst()+1;
    }

    public Long getRankCountByCharacterIdAndJobNameOrderByAdventureFame(String characterId, String jobName) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.adventureFame.gt(queryFactory.select(QCharacterEntity.characterEntity.adventureFame)
                        .from(QCharacterEntity.characterEntity)
                        .where(QCharacterEntity.characterEntity.characterId.eq(characterId))))
                .where(QCharacterEntity.characterEntity.jobName.eq(jobName))
                .fetchFirst()+1;
    }

    @Override
    public Long getCharacterCountByJobName(String jobName) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.jobName.eq(jobName))
                .fetchFirst();
    }

    @Override
    public List<CharacterEntity> findAllByAdventureName(String adventureName) {
        return queryFactory.selectFrom(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.adventureName.eq(adventureName))
                .fetch();
    }

    @Override
    public Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByAdventureFame(String characterName, Pageable pageable) {
        Long count = queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.characterName.contains(characterName),QCharacterEntity.characterEntity.adventureFame.gt(0))
                .fetchFirst();
        List<CharacterEntity.CharacterEntityRankingResponse> list = queryFactory.selectFrom(q)
                .where(q.characterName.contains(characterName),q.adventureFame.gt(0))
                .orderBy(q.adventureFame.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(CharacterEntity.CharacterEntityRankingResponse::from).peek(o->o.setRank(getRankCountByCharacterIdOrderByAdventureFame(o.getCharacterId()))).toList();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByDamageIncrease(String characterName, Pageable pageable) {
        Long count = queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.characterName.contains(characterName),QCharacterEntity.characterEntity.damageIncrease.gt(0))
                .fetchFirst();
        List<CharacterEntity.CharacterEntityRankingResponse> list = queryFactory.selectFrom(q)
                .where(q.characterName.contains(characterName),q.damageIncrease.gt(0))
                .orderBy(q.damageIncrease.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(CharacterEntity.CharacterEntityRankingResponse::from).peek(o->o.setRank(getRankCountByCharacterIdOrderByDamageIncrease(o.getCharacterId()))).toList();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRankingOrderByBuffPower(String characterName, Pageable pageable) {
        Long count = queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.characterName.contains(characterName),QCharacterEntity.characterEntity.buffPower.gt(0))
                .fetchFirst();
        List<CharacterEntity.CharacterEntityRankingResponse> list = queryFactory.selectFrom(q)
                .where(q.characterName.contains(characterName),q.buffPower.gt(0))
                .orderBy(q.buffPower.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(CharacterEntity.CharacterEntityRankingResponse::from).peek(o->o.setRank(getRankCountByCharacterIdOrderByBuffpower(o.getCharacterId()))).toList();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoCompleteServerAll(String characterName) {
        return queryFactory.selectFrom(q)
                .where(q.characterName.contains(characterName))
                .orderBy(q.characterName.asc())
                .limit(5)
                .fetch().stream().map(CharacterEntity.AutoCompleteResponse::from).toList();
    }

    @Override
    public List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoComplete(String characterName, String serverId) {
        return queryFactory.selectFrom(q)
                .where(q.characterName.contains(characterName),q.serverId.eq(serverId))
                .orderBy(q.characterName.asc())
                .limit(5)
                .fetch().stream().map(CharacterEntity.AutoCompleteResponse::from).toList();
    }

    @Override
    public List<CharacterEntity.AutoCompleteResponse> getCharacterNameAutoCompleteServerAdventure(String name) {
        return queryFactory.selectFrom(q)
                .where(q.adventureName.contains(name))
                .orderBy(q.adventureName.asc())
                .limit(5)
                .fetch().stream().map(CharacterEntity.AutoCompleteResponse::from).toList();
    }
}
