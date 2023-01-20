package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Domain.UserAccountCharacterMapper;

public interface UserAccountCharacterMapperRepository extends org.springframework.data.jpa.repository.JpaRepository<UserAccountCharacterMapper, java.lang.Long> {
    UserAccountCharacterMapper findByUserAccountAndCharacter(UserAccount account, CharacterEntity character);
}

