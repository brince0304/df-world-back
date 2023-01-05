package com.dfoff.demo.Repository.Character;

import com.dfoff.demo.Domain.DFCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DFCharacterRepository extends JpaRepository<DFCharacter, Long> {
}
