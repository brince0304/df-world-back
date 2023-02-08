package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.QBoard;
import com.dfoff.demo.domain.QCharacterEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    @Override
    public Long getRankByCharacterId(String characterId) {
        return queryFactory.select(QCharacterEntity.characterEntity.count())
                .from(QCharacterEntity.characterEntity)
                .where(QCharacterEntity.characterEntity.adventureFame.gt(queryFactory.select(QCharacterEntity.characterEntity.adventureFame)
                        .from(QCharacterEntity.characterEntity)
                        .where(QCharacterEntity.characterEntity.characterId.eq(characterId))))
                .fetchFirst()+1;
    }

    @Override
    public Long getRankByCharacterId(String characterId, String jobName) {
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
}
