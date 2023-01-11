package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.UserAccountCharacterMapper;

import java.util.Set;

public interface UserAccountCharacterMapperRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.UserAccountCharacterMapper, java.lang.Long> {
    UserAccountCharacterMapper findByUserAccountAndCharacter(UserAccount account, CharacterEntity character);
}

