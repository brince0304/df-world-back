package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Adventure;

public interface AdventureRepository extends org.springframework.data.jpa.repository.JpaRepository<Adventure, String>, AdventureCustomRepository {
    boolean existsByRepresentCharacter_CharacterId(String characterId);

    boolean existsByUserAccount_UserIdAndDeletedIsFalse(String userId);



}
