
package com.dfoff.demo.Domain.ForCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterSkillStyleJsonDto implements Serializable
{

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
    @SerializedName("skill")
    @Expose
    private Skill skill;
    private final static long serialVersionUID = -4224881191893680388L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterSkillStyleJsonDto() {
    }

    /**
     * 
     * @param jobName
     * @param jobId
     * @param level
     * @param jobGrowId
     * @param skill
     * @param characterName
     * @param adventureName
     * @param jobGrowName
     * @param characterId
     * @param guildId
     * @param guildName
     */
    public CharacterSkillStyleJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, Skill skill) {
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
        this.skill = skill;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public CharacterSkillStyleJsonDto withCharacterId(String characterId) {
        this.characterId = characterId;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public CharacterSkillStyleJsonDto withCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CharacterSkillStyleJsonDto withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public CharacterSkillStyleJsonDto withJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getJobGrowId() {
        return jobGrowId;
    }

    public void setJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
    }

    public CharacterSkillStyleJsonDto withJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public CharacterSkillStyleJsonDto withJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGrowName() {
        return jobGrowName;
    }

    public void setJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
    }

    public CharacterSkillStyleJsonDto withJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
        return this;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public CharacterSkillStyleJsonDto withAdventureName(String adventureName) {
        this.adventureName = adventureName;
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public CharacterSkillStyleJsonDto withGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public CharacterSkillStyleJsonDto withGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public CharacterSkillStyleJsonDto withSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterSkillStyleJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("skill");
        sb.append('=');
        sb.append(((this.skill == null)?"<null>":this.skill));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Generated("jsonschema2pojo")
    public static class Style implements Serializable
    {

        @SerializedName("active")
        @Expose
        private List<Active> active = new ArrayList<Active>();
        @SerializedName("passive")
        @Expose
        private List<Passive> passive = new ArrayList<Passive>();
        private final static long serialVersionUID = 2616640876000014997L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Style() {
        }

        /**
         *
         * @param active
         * @param passive
         */
        public Style(List<Active> active, List<Passive> passive) {
            super();
            this.active = active;
            this.passive = passive;
        }

        public List<Active> getActive() {
            return active;
        }

        public void setActive(List<Active> active) {
            this.active = active;
        }

        public Style withActive(List<Active> active) {
            this.active = active;
            return this;
        }

        public List<Passive> getPassive() {
            return passive;
        }

        public void setPassive(List<Passive> passive) {
            this.passive = passive;
        }

        public Style withPassive(List<Passive> passive) {
            this.passive = passive;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Style.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("active");
            sb.append('=');
            sb.append(((this.active == null)?"<null>":this.active));
            sb.append(',');
            sb.append("passive");
            sb.append('=');
            sb.append(((this.passive == null)?"<null>":this.passive));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    @Generated("jsonschema2pojo")
    public static class Skill implements Serializable
    {

        @SerializedName("style")
        @Expose
        private Style style;
        private final static long serialVersionUID = 467559882804617866L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Skill() {
        }

        /**
         *
         * @param style
         */
        public Skill(Style style) {
            super();
            this.style = style;
        }

        public Style getStyle() {
            return style;
        }

        public void setStyle(Style style) {
            this.style = style;
        }

        public Skill withStyle(Style style) {
            this.style = style;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Skill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("style");
            sb.append('=');
            sb.append(((this.style == null)?"<null>":this.style));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    @Generated("jsonschema2pojo")
    public static class Passive implements Serializable
    {

        @SerializedName("skillId")
        @Expose
        private String skillId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("requiredLevel")
        @Expose
        private Integer requiredLevel;
        private final static long serialVersionUID = 579677923654723940L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Passive() {
        }

        /**
         *
         * @param skillId
         * @param level
         * @param requiredLevel
         * @param name
         */
        public Passive(String skillId, String name, Integer level, Integer requiredLevel) {
            super();
            this.skillId = skillId;
            this.name = name;
            this.level = level;
            this.requiredLevel = requiredLevel;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public Passive withSkillId(String skillId) {
            this.skillId = skillId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Passive withName(String name) {
            this.name = name;
            return this;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Passive withLevel(Integer level) {
            this.level = level;
            return this;
        }

        public Integer getRequiredLevel() {
            return requiredLevel;
        }

        public void setRequiredLevel(Integer requiredLevel) {
            this.requiredLevel = requiredLevel;
        }

        public Passive withRequiredLevel(Integer requiredLevel) {
            this.requiredLevel = requiredLevel;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Passive.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillId");
            sb.append('=');
            sb.append(((this.skillId == null)?"<null>":this.skillId));
            sb.append(',');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null)?"<null>":this.name));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
            sb.append(',');
            sb.append("requiredLevel");
            sb.append('=');
            sb.append(((this.requiredLevel == null)?"<null>":this.requiredLevel));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    @Generated("jsonschema2pojo")
    public static class Active implements Serializable
    {

        @SerializedName("skillId")
        @Expose
        private String skillId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("requiredLevel")
        @Expose
        private Integer requiredLevel;
        private final static long serialVersionUID = 2259161861500823328L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Active() {
        }

        /**
         *
         * @param skillId
         * @param level
         * @param requiredLevel
         * @param name
         */
        public Active(String skillId, String name, Integer level, Integer requiredLevel) {
            super();
            this.skillId = skillId;
            this.name = name;
            this.level = level;
            this.requiredLevel = requiredLevel;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public Active withSkillId(String skillId) {
            this.skillId = skillId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Active withName(String name) {
            this.name = name;
            return this;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Active withLevel(Integer level) {
            this.level = level;
            return this;
        }

        public Integer getRequiredLevel() {
            return requiredLevel;
        }

        public void setRequiredLevel(Integer requiredLevel) {
            this.requiredLevel = requiredLevel;
        }

        public Active withRequiredLevel(Integer requiredLevel) {
            this.requiredLevel = requiredLevel;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Active.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillId");
            sb.append('=');
            sb.append(((this.skillId == null)?"<null>":this.skillId));
            sb.append(',');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null)?"<null>":this.name));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
            sb.append(',');
            sb.append("requiredLevel");
            sb.append('=');
            sb.append(((this.requiredLevel == null)?"<null>":this.requiredLevel));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }
}
