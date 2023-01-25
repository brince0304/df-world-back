package com.dfoff.demo.Repository;

public interface UserAdventureRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.Domain.UserAdventure, String> {
    boolean existsByRepresentCharacter_CharacterId(String characterId);

    boolean existsByUserAccount_UserId(String userId);
}
