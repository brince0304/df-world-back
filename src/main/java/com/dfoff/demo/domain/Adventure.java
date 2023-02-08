package com.dfoff.demo.domain;

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
    public static class UserAdventureResponse{
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;

        private String serverName;



        private Set<Board.CharacterBoardResponse> characters;

        public static UserAdventureResponse from(Adventure adventure){
            return UserAdventureResponse.builder()
                    .adventureName(adventure.getAdventureName())
                    .representCharacterId(adventure.getRepresentCharacter().getCharacterId())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toSet()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .serverName(CharacterEntity.CharacterEntityDto.getServerName(adventure.getServerId()))
                    .build();
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAdventureMainPageResponse{
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;

        private String serverName;

        private List<Board.CharacterBoardResponse> characters;

        public static UserAdventureMainPageResponse from(Adventure adventure){
            return UserAdventureMainPageResponse.builder()
                    .adventureName(adventure.getAdventureName())
                    .serverId(adventure.getServerId())
                    .characters(adventure.getCharacters().size()>4? adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toList()).subList(0,3)
                            : adventure.getCharacters().stream().map(Board.CharacterBoardResponse::from).collect(Collectors.toList()))
                    .adventureFame(adventure.getAdventureFame())
                    .adventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower())
                    .serverName(CharacterEntity.CharacterEntityDto.getServerName(adventure.getServerId()))
                    .build();
        }
    }


    @Data
    @Builder
    public static class UserAdventureDto {
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private Integer adventureFame;

        private Integer adventureDamageIncreaseAndBuffPower;


        private Set<CharacterEntity.CharacterEntityDto> characters;

        public static UserAdventureDto from(Adventure adventure){
            return UserAdventureDto.builder()
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
