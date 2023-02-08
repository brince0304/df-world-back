package com.dfoff.demo.repository;

import com.dfoff.demo.domain.CharacterSkillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterSkillDetailRepository extends JpaRepository<CharacterSkillDetail, Long> {
    Optional<CharacterSkillDetail> getCharacterSkillDetailBySkillIdAndSkillLevel(String skillId, String skillLevel);
}

