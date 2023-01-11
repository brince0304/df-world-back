package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link CharacterEntity} entity
 */
@Data
@Builder
public class CharacterEntityDto implements Serializable {
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;
    private final String characterId;
    private final String characterName;

    private final String serverId;

    private final Integer level;

    private final String jobId;

    private final String jobGrowId;

    private final String jobName;

    private final String jobGrowName;

    private final String adventureFame;
    private final String adventureName;

    private final CharacterAbilityDTO.CharacterAbilityJSONDTO characterAbility;
    private final UserAccount.UserAccountDTO userAccount;

    public static CharacterEntity toEntity(CharacterEntityDto dto) {
        return CharacterEntity.builder()
                .characterId(dto.getCharacterId())
                .characterName(dto.getCharacterName())
                .serverId(dto.getServerId())
                .level(dto.getLevel())
                .jobId(dto.getJobId())
                .jobGrowId(dto.getJobGrowId())
                .jobName(dto.getJobName())
                .jobGrowName(dto.getJobGrowName())
                .adventureFame(dto.getAdventureFame())
                .adventureName(dto.getAdventureName())
                .build();
    }

    public static CharacterEntityDto from(CharacterDTO dto){
        return CharacterEntityDto.builder()
                .characterId(dto.getCharacterId())
                .characterName(dto.getCharacterName())
                .serverId(dto.getServerId())
                .level(dto.getLevel())
                .jobId(dto.getJobId())
                .jobGrowId(dto.getJobGrowId())
                .jobName(dto.getJobName())
                .jobGrowName(dto.getJobGrowName())
                .build();
    }

    public static CharacterEntity toEntity(CharacterDTO dto) {
        return CharacterEntity.builder()
                .characterId(dto.getCharacterId())
                .characterName(dto.getCharacterAbilityDTO().getCharacterName())
                .level(dto.getCharacterAbilityDTO().getLevel())
                .jobId(dto.getCharacterAbilityDTO().getJobId())
                .jobGrowId(dto.getCharacterAbilityDTO().getJobGrowId())
                .jobName(dto.getCharacterAbilityDTO().getJobName())
                .jobGrowName(dto.getCharacterAbilityDTO().getJobGrowName())
                .adventureFame(dto.getCharacterAbilityDTO().getAdventureFame())
                .adventureName(dto.getCharacterAbilityDTO().getAdventureName())
                .build();
    }

    public static CharacterEntityDto toDto(CharacterEntity entity) {
        return CharacterEntityDto.builder()
                .characterId(entity.getCharacterId())
                .characterName(entity.getCharacterName())
                .serverId(entity.getServerId())
                .level(entity.getLevel())
                .jobId(entity.getJobId())
                .jobGrowId(entity.getJobGrowId())
                .jobName(entity.getJobName())
                .jobGrowName(entity.getJobGrowName())
                .adventureFame(entity.getAdventureFame())
                .adventureName(entity.getAdventureName())
                .build();
    }


}