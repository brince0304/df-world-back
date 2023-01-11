package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterDTO;
import com.dfoff.demo.UserAccountCharacterMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final Set<UserAccount.UserAccountDTO> userAccounts;

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
                .userAccount(dto.getUserAccounts()==null? new LinkedHashSet<>() : dto.getUserAccounts().stream().map(UserAccount.UserAccountDTO::toEntity).findFirst().get().getCharacterEntities())
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
                .userAccounts(entity.getUserAccount()==null? new HashSet<>() : entity.getUserAccount().stream().map(UserAccountCharacterMapper::getUserAccount).map(UserAccount.UserAccountDTO::from).collect(Collectors.toSet()))
                .build();
    }
    @Getter
    @Data
    @Builder
    public static class CharacterEntityResponse implements Serializable {
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


        public static CharacterEntityResponse from(CharacterEntityDto dto){
            return CharacterEntityResponse.builder()
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

        public static Set<CharacterEntityResponse> from(Set<CharacterEntityDto> dtos){
            return dtos.stream().map(CharacterEntityResponse::from).collect(Collectors.toSet());
        }

    }


}