package com.dfoff.demo.Domain;

import jakarta.persistence.*;
import lombok.*;



@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountCharacterMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", foreignKey = @ForeignKey(name = "fk_user_account_character_mapper_user_account_id"))
    @ToString.Exclude
    private UserAccount userAccount;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "fk_user_account_character_mapper_character_id"))
    @ToString.Exclude
    private CharacterEntity character;

    private UserAccountCharacterMapper(UserAccount userAccount, CharacterEntity character) {
        this.userAccount = userAccount;
        this.character = character;
    }


    public static UserAccountCharacterMapper of(UserAccount userAccount, CharacterEntity character) {
        return new UserAccountCharacterMapper(userAccount, character);
    }

}
