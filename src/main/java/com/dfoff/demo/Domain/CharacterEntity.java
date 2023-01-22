package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterDto;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import com.dfoff.demo.Util.SearchPageUtil;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dfoff.demo.Util.SearchPageUtil.timesAgo;


@Entity
@Getter
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
public class CharacterEntity extends AuditingFields {
    @Id
    private String characterId;
    @Column (nullable = false)
    @Setter
    private String serverId;
    @Setter
    private String characterName;
    @Column (nullable = false)
    @Setter
    private Integer level;
    @Column (nullable = false)
    @Setter
    private String jobId;
    @Column (nullable = false)
    @Setter
    private String jobGrowId;
    @Column (nullable = false)
    @Setter
    private String jobName;
    @Column (nullable = false)
    @Setter
    private String jobGrowName;
    @Setter
    private String adventureFame;

    @Setter
    private String adventureName;

    @Setter
    private String guildId;

    @Setter
    private String guildName;

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private final Set<UserAccountCharacterMapper> userAccount = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterEntity that)) return false;
        return characterId.equals(that.characterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId);
    }

    /**
     * A DTO for the {@link CharacterEntity} entity
     */
    @Data
    @Builder
    public static class CharacterEntityDto implements Serializable {
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

        private final String guildId;

        private final String guildName;





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
                    .guildId(dto.getGuildId())
                    .guildName(dto.getGuildName())
                    .build();
        }

        public static CharacterEntityDto from(CharacterDto dto) {
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

        public static CharacterEntity toEntity(CharacterDto dto) {
            return CharacterEntity.builder()
                    .characterId(dto.getCharacterId())
                    .characterName(dto.getCharacterName())
                    .level(dto.getLevel())
                    .jobId(dto.getJobId())
                    .jobGrowId(dto.getJobGrowId())
                    .jobName(dto.getJobName())
                    .jobGrowName(dto.getJobGrowName())
                    .serverId(dto.getServerId())
                    .build();
        }
        public static String getServerName(String serverId) {
            if (serverId.equals("bakal")) {
                return "바칼";
            } else if (serverId.equals("cain")) {
                return "카인";
            } else if (serverId.equals("diregie")) {
                return "디레지에";
            } else if (serverId.equals("hilder")) {
                return "힐더";
            } else if (serverId.equals("prey")) {
                return "프레이";
            } else if (serverId.equals("siroco")) {
                return "시로코";
            } else if (serverId.equals("casillas")) {
                return "카시야스";
            } else if (serverId.equals("anton")) {
                return "안톤";
            } else {
                return serverId;
            }
        }

        public static CharacterEntityDto from(CharacterEntity entity) {
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
                    .guildId(entity.getGuildId())
                    .guildName(entity.getGuildName())
                    .build();
        }


        @Getter
        @Data
        @Builder
        public static class CharacterEntityResponse implements Serializable {
            private final String createdAt;
            private final String modifiedAt;
            private final String characterId;
            private final String characterName;

            private final String serverId;

            private final String serverName;

            private final Integer level;

            private final String jobId;

            private final String jobGrowId;

            private final String jobName;

            private final String jobGrowName;

            private final String adventureFame;
            private final String adventureName;

            private final String guildId;

            private final String guildName;

            private final List<CharacterAbilityDto.Status__1> status;


            public static CharacterEntityResponse from(CharacterEntityDto dto) {
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
                        .serverName(getServerName(dto.getServerId()))
                        .guildId(dto.getGuildId())
                        .guildName(dto.getGuildName())
                        .modifiedAt(dto.getModifiedAt()!=null?timesAgo(dto.getModifiedAt()):"방금 전")
                        .build();
            }

            public static CharacterEntityResponse from(CharacterAbilityDto dto,String serverId) {
                return CharacterEntityResponse.builder()
                        .characterId(dto.getCharacterId())
                        .characterName(dto.getCharacterName())
                        .serverId(serverId)
                        .level(dto.getLevel())
                        .jobId(dto.getJobId())
                        .jobGrowId(dto.getJobGrowId())
                        .jobName(dto.getJobName())
                        .jobGrowName(dto.getJobGrowName())
                        .adventureFame(dto.getAdventureFame())
                        .adventureName(dto.getAdventureName())
                        .serverName(getServerName(serverId))
                        .guildId(dto.getGuildId())
                        .guildName(dto.getGuildName())
                        .status(dto.getStatus())
                        .build();
            }

            public static String getServerName(String serverId) {
                if (serverId.equals("bakal")) {
                    return "바칼";
                } else if (serverId.equals("cain")) {
                    return "카인";
                } else if (serverId.equals("diregie")) {
                    return "디레지에";
                } else if (serverId.equals("hilder")) {
                    return "힐더";
                } else if (serverId.equals("prey")) {
                    return "프레이";
                } else if (serverId.equals("siroco")) {
                    return "시로코";
                } else if (serverId.equals("casillas")) {
                    return "카시야스";
                } else if (serverId.equals("anton")) {
                    return "안톤";
                } else {
                    return serverId;
                }
            }

            public static Set<CharacterEntityResponse> from(Set<CharacterEntityDto> dtos) {
                return dtos.stream().map(CharacterEntityResponse::from).collect(Collectors.toSet());
            }

        }


    }
}
