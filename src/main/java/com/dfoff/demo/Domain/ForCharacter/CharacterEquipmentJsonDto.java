
package com.dfoff.demo.Domain.ForCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterEquipmentJsonDto implements Serializable
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
    @SerializedName("equipment")
    @Expose
    private List<Equipment> equipment = new ArrayList<Equipment>();
    @SerializedName("setItemInfo")
    @Expose
    private List<Object> setItemInfo = new ArrayList<Object>();
    private final static long serialVersionUID = -3118113708703648442L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterEquipmentJsonDto() {
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
     * @param equipment
     * @param characterId
     * @param guildId
     * @param setItemInfo
     * @param guildName
     */
    public CharacterEquipmentJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, List<Equipment> equipment, List<Object> setItemInfo) {
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
        this.equipment = equipment;
        this.setItemInfo = setItemInfo;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public CharacterEquipmentJsonDto withCharacterId(String characterId) {
        this.characterId = characterId;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public CharacterEquipmentJsonDto withCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CharacterEquipmentJsonDto withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public CharacterEquipmentJsonDto withJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getJobGrowId() {
        return jobGrowId;
    }

    public void setJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
    }

    public CharacterEquipmentJsonDto withJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public CharacterEquipmentJsonDto withJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGrowName() {
        return jobGrowName;
    }

    public void setJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
    }

    public CharacterEquipmentJsonDto withJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
        return this;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public CharacterEquipmentJsonDto withAdventureName(String adventureName) {
        this.adventureName = adventureName;
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public CharacterEquipmentJsonDto withGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public CharacterEquipmentJsonDto withGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public CharacterEquipmentJsonDto withEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
        return this;
    }

    public List<Object> getSetItemInfo() {
        return setItemInfo;
    }

    public void setSetItemInfo(List<Object> setItemInfo) {
        this.setItemInfo = setItemInfo;
    }

    public CharacterEquipmentJsonDto withSetItemInfo(List<Object> setItemInfo) {
        this.setItemInfo = setItemInfo;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterEquipmentJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("equipment");
        sb.append('=');
        sb.append(((this.equipment == null)?"<null>":this.equipment));
        sb.append(',');
        sb.append("setItemInfo");
        sb.append('=');
        sb.append(((this.setItemInfo == null)?"<null>":this.setItemInfo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Generated("jsonschema2pojo")
    public static class UpgradeInfo implements Serializable
    {

        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        private final static long serialVersionUID = -7416345421927605762L;

        /**
         * No args constructor for use in serialization
         *
         */
        public UpgradeInfo() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         */
        public UpgradeInfo(String itemId, String itemName) {
            super();
            this.itemId = itemId;
            this.itemName = itemName;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public UpgradeInfo withItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public UpgradeInfo withItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(UpgradeInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("itemId");
            sb.append('=');
            sb.append(((this.itemId == null)?"<null>":this.itemId));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
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
    public static class Total implements Serializable
    {

        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("level")
        @Expose
        private Integer level;
        private final static long serialVersionUID = -8772085972094215791L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Total() {
        }

        /**
         *
         * @param damage
         * @param level
         * @param buff
         */
        public Total(Integer damage, Integer buff, Integer level) {
            super();
            this.damage = damage;
            this.buff = buff;
            this.level = level;
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Total withDamage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Total withBuff(Integer buff) {
            this.buff = buff;
            return this;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Total withLevel(Integer level) {
            this.level = level;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Total.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("damage");
            sb.append('=');
            sb.append(((this.damage == null)?"<null>":this.damage));
            sb.append(',');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null)?"<null>":this.buff));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
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
    public static class Skill implements Serializable
    {

        @SerializedName("skillId")
        @Expose
        private String skillId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = 1352022034005048708L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Skill() {
        }

        /**
         *
         * @param skillId
         * @param name
         * @param value
         */
        public Skill(String skillId, String name, String value) {
            super();
            this.skillId = skillId;
            this.name = name;
            this.value = value;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public Skill withSkillId(String skillId) {
            this.skillId = skillId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Skill withName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Skill withValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Skill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("skillId");
            sb.append('=');
            sb.append(((this.skillId == null)?"<null>":this.skillId));
            sb.append(',');
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
    public static class ReinforceSkill implements Serializable
    {

        @SerializedName("jobId")
        @Expose
        private String jobId;
        @SerializedName("jobName")
        @Expose
        private String jobName;
        @SerializedName("skills")
        @Expose
        private List<Skill> skills = new ArrayList<Skill>();
        private final static long serialVersionUID = 210422089820397118L;

        /**
         * No args constructor for use in serialization
         *
         */
        public ReinforceSkill() {
        }

        /**
         *
         * @param jobName
         * @param skills
         * @param jobId
         */
        public ReinforceSkill(String jobId, String jobName, List<Skill> skills) {
            super();
            this.jobId = jobId;
            this.jobName = jobName;
            this.skills = skills;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public ReinforceSkill withJobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public ReinforceSkill withJobName(String jobName) {
            this.jobName = jobName;
            return this;
        }

        public List<Skill> getSkills() {
            return skills;
        }

        public void setSkills(List<Skill> skills) {
            this.skills = skills;
        }

        public ReinforceSkill withSkills(List<Skill> skills) {
            this.skills = skills;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ReinforceSkill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("jobId");
            sb.append('=');
            sb.append(((this.jobId == null)?"<null>":this.jobId));
            sb.append(',');
            sb.append("jobName");
            sb.append('=');
            sb.append(((this.jobName == null)?"<null>":this.jobName));
            sb.append(',');
            sb.append("skills");
            sb.append('=');
            sb.append(((this.skills == null)?"<null>":this.skills));
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
    public static class Option__2 implements Serializable
    {

        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        private final static long serialVersionUID = -8581490030948774676L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Option__2() {
        }

        /**
         *
         * @param explain
         * @param buff
         * @param explainDetail
         */
        public Option__2(Integer buff, String explain, String explainDetail) {
            super();
            this.buff = buff;
            this.explain = explain;
            this.explainDetail = explainDetail;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Option__2 withBuff(Integer buff) {
            this.buff = buff;
            return this;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public Option__2 withExplain(String explain) {
            this.explain = explain;
            return this;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public Option__2 withExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Option__2.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null)?"<null>":this.buff));
            sb.append(',');
            sb.append("explain");
            sb.append('=');
            sb.append(((this.explain == null)?"<null>":this.explain));
            sb.append(',');
            sb.append("explainDetail");
            sb.append('=');
            sb.append(((this.explainDetail == null)?"<null>":this.explainDetail));
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
    public static class Option__1 implements Serializable
    {

        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        private final static long serialVersionUID = -5894363128374569840L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Option__1() {
        }

        /**
         *
         * @param explain
         * @param damage
         * @param buff
         * @param explainDetail
         */
        public Option__1(Integer damage, Integer buff, String explain, String explainDetail) {
            super();
            this.damage = damage;
            this.buff = buff;
            this.explain = explain;
            this.explainDetail = explainDetail;
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Option__1 withDamage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Option__1 withBuff(Integer buff) {
            this.buff = buff;
            return this;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public Option__1 withExplain(String explain) {
            this.explain = explain;
            return this;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public Option__1 withExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Option__1.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("damage");
            sb.append('=');
            sb.append(((this.damage == null)?"<null>":this.damage));
            sb.append(',');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null)?"<null>":this.buff));
            sb.append(',');
            sb.append("explain");
            sb.append('=');
            sb.append(((this.explain == null)?"<null>":this.explain));
            sb.append(',');
            sb.append("explainDetail");
            sb.append('=');
            sb.append(((this.explainDetail == null)?"<null>":this.explainDetail));
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
        @SerializedName("expRate")
        @Expose
        private Float expRate;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("transfer")
        @Expose
        private Boolean transfer;
        private final static long serialVersionUID = 8899619035130389283L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Option() {
        }

        /**
         *
         * @param explain
         * @param damage
         * @param transfer
         * @param level
         * @param expRate
         * @param buff
         * @param explainDetail
         */
        public Option(Integer level, Float expRate, String explain, String explainDetail, Integer damage, Integer buff, Boolean transfer) {
            super();
            this.level = level;
            this.expRate = expRate;
            this.explain = explain;
            this.explainDetail = explainDetail;
            this.damage = damage;
            this.buff = buff;
            this.transfer = transfer;
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

        public Float getExpRate() {
            return expRate;
        }

        public void setExpRate(Float expRate) {
            this.expRate = expRate;
        }

        public Option withExpRate(Float expRate) {
            this.expRate = expRate;
            return this;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public Option withExplain(String explain) {
            this.explain = explain;
            return this;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public Option withExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
            return this;
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Option withDamage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Option withBuff(Integer buff) {
            this.buff = buff;
            return this;
        }

        public Boolean getTransfer() {
            return transfer;
        }

        public void setTransfer(Boolean transfer) {
            this.transfer = transfer;
        }

        public Option withTransfer(Boolean transfer) {
            this.transfer = transfer;
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
            sb.append("expRate");
            sb.append('=');
            sb.append(((this.expRate == null)?"<null>":this.expRate));
            sb.append(',');
            sb.append("explain");
            sb.append('=');
            sb.append(((this.explain == null)?"<null>":this.explain));
            sb.append(',');
            sb.append("explainDetail");
            sb.append('=');
            sb.append(((this.explainDetail == null)?"<null>":this.explainDetail));
            sb.append(',');
            sb.append("damage");
            sb.append('=');
            sb.append(((this.damage == null)?"<null>":this.damage));
            sb.append(',');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null)?"<null>":this.buff));
            sb.append(',');
            sb.append("transfer");
            sb.append('=');
            sb.append(((this.transfer == null)?"<null>":this.transfer));
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
    public static class MachineRevolutionInfo implements Serializable
    {

        @SerializedName("options")
        @Expose
        private List<Option__1> options = new ArrayList<Option__1>();
        private final static long serialVersionUID = 8598633801899472456L;

        /**
         * No args constructor for use in serialization
         *
         */
        public MachineRevolutionInfo() {
        }

        /**
         *
         * @param options
         */
        public MachineRevolutionInfo(List<Option__1> options) {
            super();
            this.options = options;
        }

        public List<Option__1> getOptions() {
            return options;
        }

        public void setOptions(List<Option__1> options) {
            this.options = options;
        }

        public MachineRevolutionInfo withOptions(List<Option__1> options) {
            this.options = options;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(MachineRevolutionInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("options");
            sb.append('=');
            sb.append(((this.options == null)?"<null>":this.options));
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
    public static class IspinsInfo implements Serializable
    {

        @SerializedName("options")
        @Expose
        private List<Option__2> options = new ArrayList<Option__2>();
        private final static long serialVersionUID = -5985806504522786907L;

        /**
         * No args constructor for use in serialization
         *
         */
        public IspinsInfo() {
        }

        /**
         *
         * @param options
         */
        public IspinsInfo(List<Option__2> options) {
            super();
            this.options = options;
        }

        public List<Option__2> getOptions() {
            return options;
        }

        public void setOptions(List<Option__2> options) {
            this.options = options;
        }

        public IspinsInfo withOptions(List<Option__2> options) {
            this.options = options;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(IspinsInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("options");
            sb.append('=');
            sb.append(((this.options == null)?"<null>":this.options));
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
    public static class GrowInfo implements Serializable
    {

        @SerializedName("total")
        @Expose
        private Total total;
        @SerializedName("options")
        @Expose
        private List<Option> options = new ArrayList<Option>();
        @SerializedName("transfer")
        @Expose
        private Boolean transfer;
        private final static long serialVersionUID = 8330266154419795355L;

        /**
         * No args constructor for use in serialization
         *
         */
        public GrowInfo() {
        }

        /**
         *
         * @param total
         * @param transfer
         * @param options
         */
        public GrowInfo(Total total, List<Option> options, Boolean transfer) {
            super();
            this.total = total;
            this.options = options;
            this.transfer = transfer;
        }

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public GrowInfo withTotal(Total total) {
            this.total = total;
            return this;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }

        public GrowInfo withOptions(List<Option> options) {
            this.options = options;
            return this;
        }

        public Boolean getTransfer() {
            return transfer;
        }

        public void setTransfer(Boolean transfer) {
            this.transfer = transfer;
        }

        public GrowInfo withTransfer(Boolean transfer) {
            this.transfer = transfer;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(GrowInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("total");
            sb.append('=');
            sb.append(((this.total == null)?"<null>":this.total));
            sb.append(',');
            sb.append("options");
            sb.append('=');
            sb.append(((this.options == null)?"<null>":this.options));
            sb.append(',');
            sb.append("transfer");
            sb.append('=');
            sb.append(((this.transfer == null)?"<null>":this.transfer));
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
        @SerializedName("itemGradeName")
        @Expose
        private String itemGradeName;
        @SerializedName("enchant")
        @Expose
        private Enchant enchant;
        @SerializedName("amplificationName")
        @Expose
        private String amplificationName;
        @SerializedName("refine")
        @Expose
        private Integer refine;
        @SerializedName("growInfo")
        @Expose
        private GrowInfo growInfo;
        @SerializedName("machineRevolutionInfo")
        @Expose
        private MachineRevolutionInfo machineRevolutionInfo;
        @SerializedName("upgradeInfo")
        @Expose
        private UpgradeInfo upgradeInfo;
        @SerializedName("ispinsInfo")
        @Expose
        private IspinsInfo ispinsInfo;
        private final static long serialVersionUID = 2781362068607577249L;

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
         * @param upgradeInfo
         * @param itemRarity
         * @param refine
         * @param ispinsInfo
         * @param slotName
         * @param itemId
         * @param itemName
         * @param reinforce
         * @param enchant
         * @param itemTypeDetail
         * @param growInfo
         * @param slotId
         * @param setItemId
         * @param itemGradeName
         * @param machineRevolutionInfo
         * @param setItemName
         */
        public Equipment(String slotId, String slotName, String itemId, String itemName, String itemType, String itemTypeDetail, Integer itemAvailableLevel, String itemRarity, Object setItemId, Object setItemName, Integer reinforce, String itemGradeName, Enchant enchant, String amplificationName, Integer refine, GrowInfo growInfo, MachineRevolutionInfo machineRevolutionInfo, UpgradeInfo upgradeInfo, IspinsInfo ispinsInfo) {
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
            this.itemGradeName = itemGradeName;
            this.enchant = enchant;
            this.amplificationName = amplificationName;
            this.refine = refine;
            this.growInfo = growInfo;
            this.machineRevolutionInfo = machineRevolutionInfo;
            this.upgradeInfo = upgradeInfo;
            this.ispinsInfo = ispinsInfo;
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

        public String getItemGradeName() {
            return itemGradeName;
        }

        public void setItemGradeName(String itemGradeName) {
            this.itemGradeName = itemGradeName;
        }

        public Equipment withItemGradeName(String itemGradeName) {
            this.itemGradeName = itemGradeName;
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

        public String getAmplificationName() {
            return amplificationName;
        }

        public void setAmplificationName(String amplificationName) {
            this.amplificationName = amplificationName;
        }

        public Equipment withAmplificationName(String amplificationName) {
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

        public GrowInfo getGrowInfo() {
            return growInfo;
        }

        public void setGrowInfo(GrowInfo growInfo) {
            this.growInfo = growInfo;
        }

        public Equipment withGrowInfo(GrowInfo growInfo) {
            this.growInfo = growInfo;
            return this;
        }

        public MachineRevolutionInfo getMachineRevolutionInfo() {
            return machineRevolutionInfo;
        }

        public void setMachineRevolutionInfo(MachineRevolutionInfo machineRevolutionInfo) {
            this.machineRevolutionInfo = machineRevolutionInfo;
        }

        public Equipment withMachineRevolutionInfo(MachineRevolutionInfo machineRevolutionInfo) {
            this.machineRevolutionInfo = machineRevolutionInfo;
            return this;
        }

        public UpgradeInfo getUpgradeInfo() {
            return upgradeInfo;
        }

        public void setUpgradeInfo(UpgradeInfo upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
        }

        public Equipment withUpgradeInfo(UpgradeInfo upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
            return this;
        }

        public IspinsInfo getIspinsInfo() {
            return ispinsInfo;
        }

        public void setIspinsInfo(IspinsInfo ispinsInfo) {
            this.ispinsInfo = ispinsInfo;
        }

        public Equipment withIspinsInfo(IspinsInfo ispinsInfo) {
            this.ispinsInfo = ispinsInfo;
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
            sb.append("itemGradeName");
            sb.append('=');
            sb.append(((this.itemGradeName == null)?"<null>":this.itemGradeName));
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
            sb.append("growInfo");
            sb.append('=');
            sb.append(((this.growInfo == null)?"<null>":this.growInfo));
            sb.append(',');
            sb.append("machineRevolutionInfo");
            sb.append('=');
            sb.append(((this.machineRevolutionInfo == null)?"<null>":this.machineRevolutionInfo));
            sb.append(',');
            sb.append("upgradeInfo");
            sb.append('=');
            sb.append(((this.upgradeInfo == null)?"<null>":this.upgradeInfo));
            sb.append(',');
            sb.append("ispinsInfo");
            sb.append('=');
            sb.append(((this.ispinsInfo == null)?"<null>":this.ispinsInfo));
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
        @SerializedName("reinforceSkill")
        @Expose
        private List<ReinforceSkill> reinforceSkill = new ArrayList<ReinforceSkill>();
        private final static long serialVersionUID = 170665409896873801L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Enchant() {
        }

        /**
         *
         * @param reinforceSkill
         * @param status
         */
        public Enchant(List<Status> status, List<ReinforceSkill> reinforceSkill) {
            super();
            this.status = status;
            this.reinforceSkill = reinforceSkill;
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

        public List<ReinforceSkill> getReinforceSkill() {
            return reinforceSkill;
        }

        public void setReinforceSkill(List<ReinforceSkill> reinforceSkill) {
            this.reinforceSkill = reinforceSkill;
        }

        public Enchant withReinforceSkill(List<ReinforceSkill> reinforceSkill) {
            this.reinforceSkill = reinforceSkill;
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
            sb.append("reinforceSkill");
            sb.append('=');
            sb.append(((this.reinforceSkill == null)?"<null>":this.reinforceSkill));
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
