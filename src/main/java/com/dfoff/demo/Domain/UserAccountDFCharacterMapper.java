package com.dfoff.demo.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserAccountDFCharacterMapper {
    @Id
    @GeneratedValue (strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @OneToOne (fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @ManyToOne (fetch = FetchType.LAZY)
    private DFCharacter dfCharacter;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserAccountDFCharacterMapperDTO{
        private final Long id;
        private final UserAccount.UserAccountDTO userAccount;
        private final DFCharacter.DFCharacterDTO dfCharacter;


        public UserAccountDFCharacterMapperDTO(UserAccountDFCharacterMapper userAccountDFCharacterMapper) {
            this.id = userAccountDFCharacterMapper.getId();
            this.userAccount = UserAccount.UserAccountDTO.from(userAccountDFCharacterMapper.getUserAccount());
            this.dfCharacter = DFCharacter.DFCharacterDTO.from(userAccountDFCharacterMapper.getDfCharacter());
        }

        public static UserAccountDFCharacterMapperDTO from(UserAccountDFCharacterMapper userAccountDFCharacterMapper) {
            return new UserAccountDFCharacterMapperDTO(userAccountDFCharacterMapper);
        }

        public static Set<UserAccountDFCharacterMapperDTO> from(Set<UserAccountDFCharacterMapper> userAccountDFCharacterMappers) {
            return userAccountDFCharacterMappers.stream().map(UserAccountDFCharacterMapperDTO::from).collect(java.util.stream.Collectors.toSet());
        }

        public static UserAccountDFCharacterMapper toEntity(UserAccountDFCharacterMapperDTO userAccountDFCharacterMapperDTO) {
            return UserAccountDFCharacterMapper.builder()
                    .id(userAccountDFCharacterMapperDTO.getId())
                    .userAccount(UserAccount.UserAccountDTO.toEntity(userAccountDFCharacterMapperDTO.getUserAccount()))
                    .dfCharacter(DFCharacter.DFCharacterDTO.toEntity(userAccountDFCharacterMapperDTO.getDfCharacter()))
                    .build();
        }
    }
}
