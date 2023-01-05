package com.dfoff.demo.Repository.Character;

import com.dfoff.demo.Domain.DFCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DFCharacterRepository extends JpaRepository<DFCharacter, String> {
    Page<DFCharacter> findByCharacterNameContainingIgnoreCase(String characterName, Pageable pageable);

    DFCharacter findByCharacterId(String characterId);
}