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
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @ToString.Exclude
    private UserAccount userAccount;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
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
