package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.domain.UserAccountCharacterMapper;

public interface UserAccountCharacterMapperRepository extends org.springframework.data.jpa.repository.JpaRepository<UserAccountCharacterMapper, java.lang.Long> {
    UserAccountCharacterMapper findByUserAccountAndCharacter(UserAccount account, CharacterEntity character);

    Boolean existsByUserAccountAndCharacter(UserAccount account, CharacterEntity character);
}

