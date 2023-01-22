
package com.dfoff.demo.Domain.ForCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterBuffEquipmentJsonDto implements Serializable
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
    private final static long serialVersionUID = -5844644343828256908L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterBuffEquipmentJsonDto() {
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
    public CharacterBuffEquipmentJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, Skill skill) {
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

    public CharacterBuffEquipmentJsonDto withCharacterId(String characterId) {
        this.characterId = characterId;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public CharacterBuffEquipmentJsonDto withCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CharacterBuffEquipmentJsonDto withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public CharacterBuffEquipmentJsonDto withJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getJobGrowId() {
        return jobGrowId;
    }

    public void setJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
    }

    public CharacterBuffEquipmentJsonDto withJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public CharacterBuffEquipmentJsonDto withJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGrowName() {
        return jobGrowName;
    }

    public void setJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
    }

    public CharacterBuffEquipmentJsonDto withJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
        return this;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public CharacterBuffEquipmentJsonDto withAdventureName(String adventureName) {
        this.adventureName = adventureName;
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public CharacterBuffEquipmentJsonDto withGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public CharacterBuffEquipmentJsonDto withGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public CharacterBuffEquipmentJsonDto withSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterBuffEquipmentJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
    public static class Status implements Serializable
    {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = 7546449775435991024L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Status() {
        }

        /**
         *
         * @param name
         * @param value
         */
        public Status(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Status withName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Status withValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Status.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null)?"<null>":this.name));
            sb.append(',');
            sb.append("value");
            sb.append('=');
            sb.append(((this.value == null)?"<null>":this.value));
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
        private final static long serialVersionUID = 7567104321988643715L;

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

        public SkillInfo withSkillId(String skillId) {
            this.skillId = skillId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public SkillInfo withName(String name) {
            this.name = name;
            return this;
        }

        public Option getOption() {
            return option;
        }

        public void setOption(Option option) {
            this.option = option;
        }

        public SkillInfo withOption(Option option) {
            this.option = option;
            return this;
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
        private final static long serialVersionUID = 6289712927690370850L;

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

        public Skill withBuff(Buff buff) {
            this.buff = buff;
            return this;
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
        private final static long serialVersionUID = -7345067833807127915L;

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

        public Option withLevel(Integer level) {
            this.level = level;
            return this;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Option withDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public Option withValues(List<String> values) {
            this.values = values;
            return this;
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
    public static class Equipment implements Serializable
    {

        @SerializedName("slotId")
        @Expose
        private String slotId;
        @SerializedName("slotName")
        @Expose
        private String slotName;
        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("itemType")
        @Expose
        private String itemType;
        @SerializedName("itemTypeDetail")
        @Expose
        private String itemTypeDetail;
        @SerializedName("itemAvailableLevel")
        @Expose
        private Integer itemAvailableLevel;
        @SerializedName("itemRarity")
        @Expose
        private String itemRarity;
        @SerializedName("setItemId")
        @Expose
        private Object setItemId;
        @SerializedName("setItemName")
        @Expose
        private Object setItemName;
        @SerializedName("reinforce")
        @Expose
        private Integer reinforce;
        @SerializedName("enchant")
        @Expose
        private Enchant enchant;
        @SerializedName("amplificationName")
        @Expose
        private Object amplificationName;
        @SerializedName("refine")
        @Expose
        private Integer refine;
        private final static long serialVersionUID = -4624565598843405777L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Equipment() {
        }

        /**
         *
         * @param itemAvailableLevel
         * @param itemType
         * @param amplificationName
         * @param itemRarity
         * @param refine
         * @param slotName
         * @param itemId
         * @param itemName
         * @param reinforce
         * @param enchant
         * @param itemTypeDetail
         * @param slotId
         * @param setItemId
         * @param setItemName
         */
        public Equipment(String slotId, String slotName, String itemId, String itemName, String itemType, String itemTypeDetail, Integer itemAvailableLevel, String itemRarity, Object setItemId, Object setItemName, Integer reinforce, Enchant enchant, Object amplificationName, Integer refine) {
            super();
            this.slotId = slotId;
            this.slotName = slotName;
            this.itemId = itemId;
            this.itemName = itemName;
            this.itemType = itemType;
            this.itemTypeDetail = itemTypeDetail;
            this.itemAvailableLevel = itemAvailableLevel;
            this.itemRarity = itemRarity;
            this.setItemId = setItemId;
            this.setItemName = setItemName;
            this.reinforce = reinforce;
            this.enchant = enchant;
            this.amplificationName = amplificationName;
            this.refine = refine;
        }

        public String getSlotId() {
            return slotId;
        }

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public Equipment withSlotId(String slotId) {
            this.slotId = slotId;
            return this;
        }

        public String getSlotName() {
            return slotName;
        }

        public void setSlotName(String slotName) {
            this.slotName = slotName;
        }

        public Equipment withSlotName(String slotName) {
            this.slotName = slotName;
            return this;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Equipment withItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Equipment withItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Equipment withItemType(String itemType) {
            this.itemType = itemType;
            return this;
        }

        public String getItemTypeDetail() {
            return itemTypeDetail;
        }

        public void setItemTypeDetail(String itemTypeDetail) {
            this.itemTypeDetail = itemTypeDetail;
        }

        public Equipment withItemTypeDetail(String itemTypeDetail) {
            this.itemTypeDetail = itemTypeDetail;
            return this;
        }

        public Integer getItemAvailableLevel() {
            return itemAvailableLevel;
        }

        public void setItemAvailableLevel(Integer itemAvailableLevel) {
            this.itemAvailableLevel = itemAvailableLevel;
        }

        public Equipment withItemAvailableLevel(Integer itemAvailableLevel) {
            this.itemAvailableLevel = itemAvailableLevel;
            return this;
        }

        public String getItemRarity() {
            return itemRarity;
        }

        public void setItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
        }

        public Equipment withItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
            return this;
        }

        public Object getSetItemId() {
            return setItemId;
        }

        public void setSetItemId(Object setItemId) {
            this.setItemId = setItemId;
        }

        public Equipment withSetItemId(Object setItemId) {
            this.setItemId = setItemId;
            return this;
        }

        public Object getSetItemName() {
            return setItemName;
        }

        public void setSetItemName(Object setItemName) {
            this.setItemName = setItemName;
        }

        public Equipment withSetItemName(Object setItemName) {
            this.setItemName = setItemName;
            return this;
        }

        public Integer getReinforce() {
            return reinforce;
        }

        public void setReinforce(Integer reinforce) {
            this.reinforce = reinforce;
        }

        public Equipment withReinforce(Integer reinforce) {
            this.reinforce = reinforce;
            return this;
        }

        public Enchant getEnchant() {
            return enchant;
        }

        public void setEnchant(Enchant enchant) {
            this.enchant = enchant;
        }

        public Equipment withEnchant(Enchant enchant) {
            this.enchant = enchant;
            return this;
        }

        public Object getAmplificationName() {
            return amplificationName;
        }

        public void setAmplificationName(Object amplificationName) {
            this.amplificationName = amplificationName;
        }

        public Equipment withAmplificationName(Object amplificationName) {
            this.amplificationName = amplificationName;
            return this;
        }

        public Integer getRefine() {
            return refine;
        }

        public void setRefine(Integer refine) {
            this.refine = refine;
        }

        public Equipment withRefine(Integer refine) {
            this.refine = refine;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Equipment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("slotId");
            sb.append('=');
            sb.append(((this.slotId == null)?"<null>":this.slotId));
            sb.append(',');
            sb.append("slotName");
            sb.append('=');
            sb.append(((this.slotName == null)?"<null>":this.slotName));
            sb.append(',');
            sb.append("itemId");
            sb.append('=');
            sb.append(((this.itemId == null)?"<null>":this.itemId));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
            sb.append(',');
            sb.append("itemType");
            sb.append('=');
            sb.append(((this.itemType == null)?"<null>":this.itemType));
            sb.append(',');
            sb.append("itemTypeDetail");
            sb.append('=');
            sb.append(((this.itemTypeDetail == null)?"<null>":this.itemTypeDetail));
            sb.append(',');
            sb.append("itemAvailableLevel");
            sb.append('=');
            sb.append(((this.itemAvailableLevel == null)?"<null>":this.itemAvailableLevel));
            sb.append(',');
            sb.append("itemRarity");
            sb.append('=');
            sb.append(((this.itemRarity == null)?"<null>":this.itemRarity));
            sb.append(',');
            sb.append("setItemId");
            sb.append('=');
            sb.append(((this.setItemId == null)?"<null>":this.setItemId));
            sb.append(',');
            sb.append("setItemName");
            sb.append('=');
            sb.append(((this.setItemName == null)?"<null>":this.setItemName));
            sb.append(',');
            sb.append("reinforce");
            sb.append('=');
            sb.append(((this.reinforce == null)?"<null>":this.reinforce));
            sb.append(',');
            sb.append("enchant");
            sb.append('=');
            sb.append(((this.enchant == null)?"<null>":this.enchant));
            sb.append(',');
            sb.append("amplificationName");
            sb.append('=');
            sb.append(((this.amplificationName == null)?"<null>":this.amplificationName));
            sb.append(',');
            sb.append("refine");
            sb.append('=');
            sb.append(((this.refine == null)?"<null>":this.refine));
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
    public static class Enchant implements Serializable
    {

        @SerializedName("status")
        @Expose
        private List<Status> status = new ArrayList<Status>();
        private final static long serialVersionUID = 861826415060950749L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Enchant() {
        }

        /**
         *
         * @param status
         */
        public Enchant(List<Status> status) {
            super();
            this.status = status;
        }

        public List<Status> getStatus() {
            return status;
        }

        public void setStatus(List<Status> status) {
            this.status = status;
        }

        public Enchant withStatus(List<Status> status) {
            this.status = status;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Enchant.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("status");
            sb.append('=');
            sb.append(((this.status == null)?"<null>":this.status));
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
        @SerializedName("equipment")
        @Expose
        private List<Equipment> equipment = new ArrayList<Equipment>();
        private final static long serialVersionUID = -7651386568193103761L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Buff() {
        }

        /**
         *
         * @param skillInfo
         * @param equipment
         */
        public Buff(SkillInfo skillInfo, List<Equipment> equipment) {
            super();
            this.skillInfo = skillInfo;
            this.equipment = equipment;
        }

        public SkillInfo getSkillInfo() {
            return skillInfo;
        }

        public void setSkillInfo(SkillInfo skillInfo) {
            this.skillInfo = skillInfo;
        }

        public Buff withSkillInfo(SkillInfo skillInfo) {
            this.skillInfo = skillInfo;
            return this;
        }

        public List<Equipment> getEquipment() {
            return equipment;
        }

        public void setEquipment(List<Equipment> equipment) {
            this.equipment = equipment;
        }

        public Buff withEquipment(List<Equipment> equipment) {
            this.equipment = equipment;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Buff.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillInfo");
            sb.append('=');
            sb.append(((this.skillInfo == null)?"<null>":this.skillInfo));
            sb.append(',');
            sb.append("equipment");
            sb.append('=');
            sb.append(((this.equipment == null)?"<null>":this.equipment));
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
