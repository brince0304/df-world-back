package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.CharacterSkillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterSkillDetailRepository extends JpaRepository<CharacterSkillDetail, Long> {
    Optional<CharacterSkillDetail> getCharacterSkillDetailBySkillIdAndSkillLevel(String skillId, String skillLevel);
}

