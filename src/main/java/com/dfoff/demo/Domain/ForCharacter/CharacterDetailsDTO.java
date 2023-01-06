
package com.dfoff.demo.Domain.ForCharacter;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class CharacterDetailsDTO {

    private String characterId;

    private String characterName;

    private Integer level;

    private String jobId;

    private String jobGrowId;

    private String jobName;

    private String jobGrowName;

    private String adventureName;

    private String guildId;

    private String guildName;
    @Generated("jsonschema2pojo")
    public static class CharacterDetailsJSONDTO {

        @SerializedName("characterId")
        @Expose
        private String characterId;
        @SerializedName("characterName")
        @Expose
        private String characterName;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("jobId")
        @Expose
        private String jobId;
        @SerializedName("jobGrowId")
        @Expose
        private String jobGrowId;
        @SerializedName("jobName")
        @Expose
        private String jobName;
        @SerializedName("jobGrowName")
        @Expose
        private String jobGrowName;
        @SerializedName("adventureName")
        @Expose
        private String adventureName;
        @SerializedName("guildId")
        @Expose
        private String guildId;
        @SerializedName("guildName")
        @Expose
        private String guildName;

        /**
         * No args constructor for use in serialization
         *
         */
        public CharacterDetailsJSONDTO() {
        }

        public CharacterDetailsDTO toDTO(){
            return CharacterDetailsDTO.builder()
                    .characterId(characterId)
                    .characterName(characterName)
                    .level(level)
                    .jobId(jobId)
                    .jobGrowId(jobGrowId)
                    .jobName(jobName)
                    .jobGrowName(jobGrowName)
                    .adventureName(adventureName)
                    .guildId(guildId)
                    .guildName(guildName)
                    .build();
        }

        /**
         *
         * @param jobName
         * @param jobId
         * @param level
         * @param jobGrowId
         * @param characterName
         * @param adventureName
         * @param jobGrowName
         * @param characterId
         * @param guildId
         * @param guildName
         */
        public CharacterDetailsJSONDTO(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName) {
            super();
            this.characterId = characterId;
            this.characterName = characterName;
            this.level = level;
            this.jobId = jobId;
            this.jobGrowId = jobGrowId;
            this.jobName = jobName;
            this.jobGrowName = jobGrowName;
            this.adventureName = adventureName;
            this.guildId = guildId;
            this.guildName = guildName;
        }

        public String getCharacterId() {
            return characterId;
        }

        public void setCharacterId(String characterId) {
            this.characterId = characterId;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJobGrowId() {
            return jobGrowId;
        }

        public void setJobGrowId(String jobGrowId) {
            this.jobGrowId = jobGrowId;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getJobGrowName() {
            return jobGrowName;
        }

        public void setJobGrowName(String jobGrowName) {
            this.jobGrowName = jobGrowName;
        }

        public String getAdventureName() {
            return adventureName;
        }

        public void setAdventureName(String adventureName) {
            this.adventureName = adventureName;
        }

        public String getGuildId() {
            return guildId;
        }

        public void setGuildId(String guildId) {
            this.guildId = guildId;
        }

        public String getGuildName() {
            return guildName;
        }

        public void setGuildName(String guildName) {
            this.guildName = guildName;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(CharacterDetailsJSONDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("characterId");
            sb.append('=');
            sb.append(((this.characterId == null)?"<null>":this.characterId));
            sb.append(',');
            sb.append("characterName");
            sb.append('=');
            sb.append(((this.characterName == null)?"<null>":this.characterName));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
            sb.append(',');
            sb.append("jobId");
            sb.append('=');
            sb.append(((this.jobId == null)?"<null>":this.jobId));
            sb.append(',');
            sb.append("jobGrowId");
            sb.append('=');
            sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
            sb.append(',');
            sb.append("jobName");
            sb.append('=');
            sb.append(((this.jobName == null)?"<null>":this.jobName));
            sb.append(',');
            sb.append("jobGrowName");
            sb.append('=');
            sb.append(((this.jobGrowName == null)?"<null>":this.jobGrowName));
            sb.append(',');
            sb.append("adventureName");
            sb.append('=');
            sb.append(((this.adventureName == null)?"<null>":this.adventureName));
            sb.append(',');
            sb.append("guildId");
            sb.append('=');
            sb.append(((this.guildId == null)?"<null>":this.guildId));
            sb.append(',');
            sb.append("guildName");
            sb.append('=');
            sb.append(((this.guildName == null)?"<null>":this.guildName));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.jobName == null)? 0 :this.jobName.hashCode()));
            result = ((result* 31)+((this.jobId == null)? 0 :this.jobId.hashCode()));
            result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
            result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
            result = ((result* 31)+((this.characterName == null)? 0 :this.characterName.hashCode()));
            result = ((result* 31)+((this.adventureName == null)? 0 :this.adventureName.hashCode()));
            result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
            result = ((result* 31)+((this.characterId == null)? 0 :this.characterId.hashCode()));
            result = ((result* 31)+((this.guildId == null)? 0 :this.guildId.hashCode()));
            result = ((result* 31)+((this.guildName == null)? 0 :this.guildName.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof CharacterDetailsJSONDTO) == false) {
                return false;
            }
            CharacterDetailsJSONDTO rhs = ((CharacterDetailsJSONDTO) other);
            return (((((((((((this.jobName == rhs.jobName)||((this.jobName!= null)&&this.jobName.equals(rhs.jobName)))&&((this.jobId == rhs.jobId)||((this.jobId!= null)&&this.jobId.equals(rhs.jobId))))&&((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level))))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))))&&((this.characterName == rhs.characterName)||((this.characterName!= null)&&this.characterName.equals(rhs.characterName))))&&((this.adventureName == rhs.adventureName)||((this.adventureName!= null)&&this.adventureName.equals(rhs.adventureName))))&&((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName))))&&((this.characterId == rhs.characterId)||((this.characterId!= null)&&this.characterId.equals(rhs.characterId))))&&((this.guildId == rhs.guildId)||((this.guildId!= null)&&this.guildId.equals(rhs.guildId))))&&((this.guildName == rhs.guildName)||((this.guildName!= null)&&this.guildName.equals(rhs.guildName))));
        }

    }

}
