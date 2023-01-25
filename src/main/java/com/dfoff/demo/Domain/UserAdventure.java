package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@SQLDelete(sql = "UPDATE user_adventure SET deleted = true, deleted_at = now() WHERE id = ?")
public class UserAdventure extends AuditingFields {
    @Id
    private String adventureName;

    @OneToOne
    @Setter
    private UserAccount userAccount;

    @OneToMany
    @JoinColumn(name = "user_adventure_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @Builder.Default
    private Set<CharacterEntity> characters = new LinkedHashSet<>();

    @Builder.Default
    private boolean deleted = Boolean.FALSE;


    private LocalDateTime deletedAt;

    private String serverId;

    @OneToOne
    @JoinColumn(name = "user_adventure_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @Setter
    private CharacterEntity representCharacter;


    @Data
    @Builder
    public static class UserAdventureRequest{
        private String adventureName;

        private String representCharacterId;

        private String serverId;

        private String randomJobName;

        private String randomString;





        public UserAdventure toEntity(UserAccount.UserAccountDto userAccountDto, CharacterEntity.CharacterEntityDto characterEntityDto){
            return UserAdventure.builder()
                    .adventureName(adventureName)
                    .userAccount(userAccountDto.toEntity())
                    .representCharacter(CharacterEntity.CharacterEntityDto.toEntity(characterEntityDto))
                    .serverId(serverId)
                    .build();
        }

    }


}
