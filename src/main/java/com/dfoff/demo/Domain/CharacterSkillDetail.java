package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForCharacter.CharacterSkillDetailJsonDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterSkillStyleJsonDto;
import com.dfoff.demo.Util.SearchPageUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CharacterSkillDetail {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    private String skillId;

    private String skillLevel;

    private String finishPercent;

    private String skillName;




    @Builder
    @Data
    public static class CharacterSkillDetailDto{
        private final String jobId;
        private final String skillId;
        private final String skillLevel;
        private final String finishPercent;
        private final String skillName;

        public CharacterSkillDetailDto(String jobId, String skillId, String skillLevel, String finishPercent, String skillName) {
            this.jobId = jobId;
            this.skillId = skillId;
            this.skillLevel = skillLevel;
            this.finishPercent = finishPercent;
            this.skillName = skillName;
        }

        public  CharacterSkillDetail toEntity(){
            return CharacterSkillDetail.builder()
                    .jobId(jobId)
                    .skillId(skillId)
                    .skillLevel(skillLevel)
                    .finishPercent(finishPercent)
                    .skillName(skillName)
                    .build();
        }

        public static CharacterSkillDetailDto from(CharacterSkillDetail characterSkillDetail){
            return new CharacterSkillDetailDto(
                    characterSkillDetail.getJobId(),
                    characterSkillDetail.getSkillId(),
                    characterSkillDetail.getSkillLevel(),
                    characterSkillDetail.getFinishPercent(),
                    characterSkillDetail.getSkillName()
            );
        }

        public static  List<CharacterSkillDetail> toEntity(CharacterSkillStyleJsonDto style, CharacterSkillDetailJsonDto detail){
            List<CharacterSkillDetail> list = new ArrayList<>();
            for(CharacterSkillStyleJsonDto.Active active: style.getSkill().getStyle().getActive()){
                for(CharacterSkillDetailJsonDto.Row row: detail.getLevelInfo().getRows()){
                    if(active.getName().equals(detail.getName())){
                        list.add(CharacterSkillDetail.builder()
                                .jobId(style.getJobId())
                                .skillId(active.getSkillId())
                                .skillLevel(String.valueOf(row.getLevel()))
                                .finishPercent(SearchPageUtil.getSkillFinalPercent(detail, row.getLevel()))
                                .skillName(active.getName())
                                .build());
                    }
                }
            }
            return list;
        }


    }
}
