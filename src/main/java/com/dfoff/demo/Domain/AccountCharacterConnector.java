package com.dfoff.demo.Domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AccountCharacterConnector {
    @Id
    @GeneratedValue (strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_account_id")
    @ManyToOne
    private UserAccount userAccount;
    @NotNull
    private String characterId;
    @NotNull
    private String serverId;

    @Setter
    @AllArgsConstructor
    @Builder
    @Getter
    public static class AccountCharacterConnectorDTO{
        private Long id;
        private String characterId;
        private String serverId;
        private UserAccount.UserAccountDTO userAccountId;


        public static AccountCharacterConnectorDTO from(AccountCharacterConnector accountCharacterConnector){
            return AccountCharacterConnectorDTO.builder()
                    .id(accountCharacterConnector.getId())
                    .serverId(accountCharacterConnector.getServerId())
                    .characterId(accountCharacterConnector.getCharacterId())
                    .userAccountId(UserAccount.UserAccountDTO.from(accountCharacterConnector.getUserAccount()))
                    .build();
        }
    }
}
