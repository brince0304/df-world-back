package com.dfoff.demo.domain;

import com.dfoff.demo.utils.CharactersUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@SQLDelete(sql = "UPDATE adventure SET deleted = true, deleted_at = now() WHERE id = ?")
public class Adventure extends AuditingFields {
    @Id
    @Column(name = "adv_name",unique = true)
    private String adventureName;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,foreignKey = @ForeignKey(name = "fk_user_adventure_user_id"))
    @Setter
    @Nullable
    private UserAccount userAccount;

    @OneToMany
    @JoinColumn(name = "adv_adventure_name", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @Builder.Default
    @OrderBy("adventureFame DESC")
    private Set<CharacterEntity> characters = new LinkedHashSet<>();

    @Builder.Default
    private boolean deleted = Boolean.FALSE;

    @Setter
    @Builder.Default
    private Integer adventureFame = 0;
    @Setter
    @Builder.Default
    private Integer adventureDamageIncreaseAndBuffPower = 0;


    private LocalDateTime deletedAt;

    private String serverId;

    @OneToOne
    @JoinColumn(name = "represent_character_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @Setter
    private CharacterEntity representCharacter;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAdventureRequest{
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private String randomJobName;

        private String randomString;







        public Adventure toEntity(UserAccount.UserAccountDto userAccountDto, CharacterEntity.CharacterEntityDto characterEntityDto){
            return Adventure.builder()
                    .adventureName(adventureName)
                    .userAccount(userAccountDto.toEntity())
                    .representCharacter(CharacterEntity.CharacterEntityDto.toEntity(characterEntityDto))
                    .serverId(serverId)
                    .build();
        }

    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdventureResponse {
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;

        private String serverName;



        private Set<Board.CharacterBoardResponse> characters;

        public static AdventureResponse from(Adventure adventure){
            return AdventureResponse.builder()
                    .adventureName(adventure.getAdventureName())
                    .representCharacterId(adventure.getRepresentCharacter().getCharacterId())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toSet()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .serverName(CharactersUtil.getServerName(adventure.getServerId()))
                    .build();
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdventureMainPageResponse {
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;

        private String serverName;

        private List<Board.CharacterBoardResponse> characters;

        private String representCharacterName;

        private Integer characterCount;


        public static AdventureMainPageResponse from(Adventure adventure){
            return AdventureMainPageResponse.builder()
                    .adventureName(adventure.getAdventureName())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().size()>4? adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toList()).subList(0,4)
                            : adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toList()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .serverName(CharactersUtil.getServerName(adventure.getServerId()))
                    .characterCount(adventure.getCharacters().size())
                    .representCharacterName(adventure.getRepresentCharacter() != null? adventure.getRepresentCharacter().getCharacterName() :
                            adventure.getCharacters().stream().findFirst().get().getCharacterName())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdventureRankingResponse {
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;

        private String serverName;

        private List<CharacterEntity.CharacterEntityRankingResponse> characters;

        private Long characterCount;

        private Long adventureRank;

        public static AdventureRankingResponse from(Adventure adventure){
            return AdventureRankingResponse.builder()
                    .adventureName(adventure.getAdventureName())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().size()>4? adventure.getCharacters().stream().map(CharacterEntity.CharacterEntityRankingResponse::from).collect(Collectors.toList()).subList(0,4)
                            : adventure.getCharacters().stream().map(CharacterEntity.CharacterEntityRankingResponse::from).collect(Collectors.toList()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .serverName(CharactersUtil.getServerName(adventure.getServerId()))
                    .characterCount((long) adventure.getCharacters().size())
                    .build();
        }
    }


    @Data
    @Builder
    public static class AdventureDto {
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;


        private Set<CharacterEntity.CharacterEntityDto> characters;

        public static AdventureDto from(Adventure adventure){
            return AdventureDto.builder()
                    .adventureName(adventure.getAdventureName())
                    .representCharacterId(adventure.getRepresentCharacter().getCharacterId())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().stream().map(CharacterEntity.CharacterEntityDto::from).collect(Collectors.toSet()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .build();
        }
    }
}
