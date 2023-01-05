package com.dfoff.demo.Repository.Character;

import com.dfoff.demo.Domain.DFCharacter;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Domain.UserAccountDFCharacterMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserAccountDFCharacterMapperRepository extends JpaRepository<UserAccountDFCharacterMapper,Long> {
    Set<UserAccountDFCharacterMapper> findAllByUserAccount(@Param("user_account_id")UserAccount userAccount);

    UserAccountDFCharacterMapper findByUserAccountAndDfCharacter(String userAccountId, String dfCharacterId);

    boolean existsByUserAccountAndDfCharacter(@Param("user_account_id")  UserAccount userAccount, @Param("df_character_id")DFCharacter character);
}
