
package com.dfoff.demo.domain.jsondtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterBuffCreatureJsonDto implements Serializable
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
    private final static long serialVersionUID = 1438514166601753875L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterBuffCreatureJsonDto() {
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
    public CharacterBuffCreatureJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, Skill skill) {
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

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterBuffCreatureJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
    public static class SkillInfo implements Serializable
    {

        @SerializedName("skillId")
        @Expose
        private String skillId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("option")
        @Expose
        private Option option;
        private final static long serialVersionUID = 4223788508441122492L;

        /**
         * No args constructor for use in serialization
         *
         */
        public SkillInfo() {
        }

        /**
         *
         * @param skillId
         * @param name
         * @param option
         */
        public SkillInfo(String skillId, String name, Option option) {
            super();
            this.skillId = skillId;
            this.name = name;
            this.option = option;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Option getOption() {
            return option;
        }

        public void setOption(Option option) {
            this.option = option;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(SkillInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillId");
            sb.append('=');
            sb.append(((this.skillId == null)?"<null>":this.skillId));
            sb.append(',');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null)?"<null>":this.name));
            sb.append(',');
            sb.append("option");
            sb.append('=');
            sb.append(((this.option == null)?"<null>":this.option));
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

        @SerializedName("buff")
        @Expose
        private Buff buff;
        private final static long serialVersionUID = 212861324744037445L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Skill() {
        }

        /**
         *
         * @param buff
         */
        public Skill(Buff buff) {
            super();
            this.buff = buff;
        }

        public Buff getBuff() {
            return buff;
        }

        public void setBuff(Buff buff) {
            this.buff = buff;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Skill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null)?"<null>":this.buff));
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
    public static class Option implements Serializable
    {

        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("values")
        @Expose
        private List<String> values = new ArrayList<String>();
        private final static long serialVersionUID = -8888825476410179678L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Option() {
        }

        /**
         *
         * @param level
         * @param values
         * @param desc
         */
        public Option(Integer level, String desc, List<String> values) {
            super();
            this.level = level;
            this.desc = desc;
            this.values = values;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Option.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
            sb.append(',');
            sb.append("desc");
            sb.append('=');
            sb.append(((this.desc == null)?"<null>":this.desc));
            sb.append(',');
            sb.append("values");
            sb.append('=');
            sb.append(((this.values == null)?"<null>":this.values));
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
    public static class Creature implements Serializable
    {

        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("itemRarity")
        @Expose
        private String itemRarity;
        @SerializedName("enchant")
        @Expose
        private Object enchant;
        private final static long serialVersionUID = -7807103058193542738L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Creature() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         * @param itemRarity
         * @param enchant
         */
        public Creature(String itemId, String itemName, String itemRarity, Object enchant) {
            super();
            this.itemId = itemId;
            this.itemName = itemName;
            this.itemRarity = itemRarity;
            this.enchant = enchant;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemRarity() {
            return itemRarity;
        }

        public void setItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
        }

        public Object getEnchant() {
            return enchant;
        }

        public void setEnchant(Object enchant) {
            this.enchant = enchant;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Creature.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("itemId");
            sb.append('=');
            sb.append(((this.itemId == null)?"<null>":this.itemId));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
            sb.append(',');
            sb.append("itemRarity");
            sb.append('=');
            sb.append(((this.itemRarity == null)?"<null>":this.itemRarity));
            sb.append(',');
            sb.append("enchant");
            sb.append('=');
            sb.append(((this.enchant == null)?"<null>":this.enchant));
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
    public static class Buff implements Serializable
    {

        @SerializedName("skillInfo")
        @Expose
        private SkillInfo skillInfo;
        @SerializedName("creature")
        @Expose
        private List<Creature> creature = new ArrayList<Creature>();
        private final static long serialVersionUID = 2524075599940078202L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Buff() {
        }

        /**
         *
         * @param skillInfo
         * @param creature
         */
        public Buff(SkillInfo skillInfo, List<Creature> creature) {
            super();
            this.skillInfo = skillInfo;
            this.creature = creature;
        }

        public SkillInfo getSkillInfo() {
            return skillInfo;
        }

        public void setSkillInfo(SkillInfo skillInfo) {
            this.skillInfo = skillInfo;
        }

        public List<Creature> getCreature() {
            return creature;
        }

        public void setCreature(List<Creature> creature) {
            this.creature = creature;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Buff.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillInfo");
            sb.append('=');
            sb.append(((this.skillInfo == null)?"<null>":this.skillInfo));
            sb.append(',');
            sb.append("creature");
            sb.append('=');
            sb.append(((this.creature == null)?"<null>":this.creature));
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
