
package com.dfoff.demo.domain.jsondtos;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterSkillDetailJsonDto implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("costType")
    @Expose
    private String costType;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("descDetail")
    @Expose
    private String descDetail;
    @SerializedName("consumeItem")
    @Expose
    private ConsumeItem consumeItem;
    @SerializedName("descSpecial")
    @Expose
    private String descSpecial;
    @SerializedName("maxLevel")
    @Expose
    private Integer maxLevel;
    @SerializedName("requiredLevel")
    @Expose
    private Integer requiredLevel;
    @SerializedName("requiredLevelRange")
    @Expose
    private Integer requiredLevelRange;
    @SerializedName("preRequiredSkill")
    @Expose
    private List<PreRequiredSkill> preRequiredSkill = null;
    @SerializedName("jobId")
    @Expose
    private String jobId;
    @SerializedName("jobName")
    @Expose
    private String jobName;
    @SerializedName("jobGrowLevel")
    @Expose
    private List<JobGrowLevel> jobGrowLevel = null;
    @SerializedName("levelInfo")
    @Expose
    private LevelInfo levelInfo;
    private final static long serialVersionUID = -5890464199514130057L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterSkillDetailJsonDto() {
    }

    /**
     * 
     * @param maxLevel
     * @param jobName
     * @param preRequiredSkill
     * @param requiredLevel
     * @param descSpecial
     * @param type
     * @param consumeItem
     * @param descDetail
     * @param jobGrowLevel
     * @param jobId
     * @param levelInfo
     * @param requiredLevelRange
     * @param costType
     * @param name
     * @param desc
     */
    public CharacterSkillDetailJsonDto(String name, String type, String costType, String desc, String descDetail, ConsumeItem consumeItem, String descSpecial, Integer maxLevel, Integer requiredLevel, Integer requiredLevelRange, List<PreRequiredSkill> preRequiredSkill, String jobId, String jobName, List<JobGrowLevel> jobGrowLevel, LevelInfo levelInfo) {
        super();
        this.name = name;
        this.type = type;
        this.costType = costType;
        this.desc = desc;
        this.descDetail = descDetail;
        this.consumeItem = consumeItem;
        this.descSpecial = descSpecial;
        this.maxLevel = maxLevel;
        this.requiredLevel = requiredLevel;
        this.requiredLevelRange = requiredLevelRange;
        this.preRequiredSkill = preRequiredSkill;
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobGrowLevel = jobGrowLevel;
        this.levelInfo = levelInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescDetail() {
        return descDetail;
    }

    public void setDescDetail(String descDetail) {
        this.descDetail = descDetail;
    }

    public ConsumeItem getConsumeItem() {
        return consumeItem;
    }

    public void setConsumeItem(ConsumeItem consumeItem) {
        this.consumeItem = consumeItem;
    }

    public String getDescSpecial() {
        return descSpecial;
    }

    public void setDescSpecial(String descSpecial) {
        this.descSpecial = descSpecial;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public Integer getRequiredLevelRange() {
        return requiredLevelRange;
    }

    public void setRequiredLevelRange(Integer requiredLevelRange) {
        this.requiredLevelRange = requiredLevelRange;
    }

    public List<PreRequiredSkill> getPreRequiredSkill() {
        return preRequiredSkill;
    }

    public void setPreRequiredSkill(List<PreRequiredSkill> preRequiredSkill) {
        this.preRequiredSkill = preRequiredSkill;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<JobGrowLevel> getJobGrowLevel() {
        return jobGrowLevel;
    }

    public void setJobGrowLevel(List<JobGrowLevel> jobGrowLevel) {
        this.jobGrowLevel = jobGrowLevel;
    }

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterSkillDetailJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("costType");
        sb.append('=');
        sb.append(((this.costType == null)?"<null>":this.costType));
        sb.append(',');
        sb.append("desc");
        sb.append('=');
        sb.append(((this.desc == null)?"<null>":this.desc));
        sb.append(',');
        sb.append("descDetail");
        sb.append('=');
        sb.append(((this.descDetail == null)?"<null>":this.descDetail));
        sb.append(',');
        sb.append("consumeItem");
        sb.append('=');
        sb.append(((this.consumeItem == null)?"<null>":this.consumeItem));
        sb.append(',');
        sb.append("descSpecial");
        sb.append('=');
        sb.append(((this.descSpecial == null)?"<null>":this.descSpecial));
        sb.append(',');
        sb.append("maxLevel");
        sb.append('=');
        sb.append(((this.maxLevel == null)?"<null>":this.maxLevel));
        sb.append(',');
        sb.append("requiredLevel");
        sb.append('=');
        sb.append(((this.requiredLevel == null)?"<null>":this.requiredLevel));
        sb.append(',');
        sb.append("requiredLevelRange");
        sb.append('=');
        sb.append(((this.requiredLevelRange == null)?"<null>":this.requiredLevelRange));
        sb.append(',');
        sb.append("preRequiredSkill");
        sb.append('=');
        sb.append(((this.preRequiredSkill == null)?"<null>":this.preRequiredSkill));
        sb.append(',');
        sb.append("jobId");
        sb.append('=');
        sb.append(((this.jobId == null)?"<null>":this.jobId));
        sb.append(',');
        sb.append("jobName");
        sb.append('=');
        sb.append(((this.jobName == null)?"<null>":this.jobName));
        sb.append(',');
        sb.append("jobGrowLevel");
        sb.append('=');
        sb.append(((this.jobGrowLevel == null)?"<null>":this.jobGrowLevel));
        sb.append(',');
        sb.append("levelInfo");
        sb.append('=');
        sb.append(((this.levelInfo == null)?"<null>":this.levelInfo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Generated("jsonschema2pojo")
    public static class Row implements Serializable
    {

        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("consumeMp")
        @Expose
        private Integer consumeMp;
        @SerializedName("coolTime")
        @Expose
        private Float coolTime;
        @SerializedName("castingTime")
        @Expose
        private Object castingTime;
        @SerializedName("optionValue")
        @Expose
        private OptionValue optionValue;
        private final static long serialVersionUID = 5837410785983940062L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Row() {
        }

        /**
         *
         * @param castingTime
         * @param level
         * @param optionValue
         * @param consumeMp
         * @param coolTime
         */
        public Row(Integer level, Integer consumeMp, Float coolTime, Object castingTime, OptionValue optionValue) {
            super();
            this.level = level;
            this.consumeMp = consumeMp;
            this.coolTime = coolTime;
            this.castingTime = castingTime;
            this.optionValue = optionValue;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Integer getConsumeMp() {
            return consumeMp;
        }

        public void setConsumeMp(Integer consumeMp) {
            this.consumeMp = consumeMp;
        }

        public Float getCoolTime() {
            return coolTime;
        }

        public void setCoolTime(Float coolTime) {
            this.coolTime = coolTime;
        }

        public Object getCastingTime() {
            return castingTime;
        }

        public void setCastingTime(Object castingTime) {
            this.castingTime = castingTime;
        }

        public OptionValue getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(OptionValue optionValue) {
            this.optionValue = optionValue;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Row.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null)?"<null>":this.level));
            sb.append(',');
            sb.append("consumeMp");
            sb.append('=');
            sb.append(((this.consumeMp == null)?"<null>":this.consumeMp));
            sb.append(',');
            sb.append("coolTime");
            sb.append('=');
            sb.append(((this.coolTime == null)?"<null>":this.coolTime));
            sb.append(',');
            sb.append("castingTime");
            sb.append('=');
            sb.append(((this.castingTime == null)?"<null>":this.castingTime));
            sb.append(',');
            sb.append("optionValue");
            sb.append('=');
            sb.append(((this.optionValue == null)?"<null>":this.optionValue));
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
    public static class PreRequiredSkill implements Serializable
    {

        @SerializedName("skillId")
        @Expose
        private String skillId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("level")
        @Expose
        private String level;
        private final static long serialVersionUID = 5269576031669354255L;

        /**
         * No args constructor for use in serialization
         *
         */
        public PreRequiredSkill() {
        }

        /**
         *
         * @param skillId
         * @param level
         * @param name
         */
        public PreRequiredSkill(String skillId, String name, String level) {
            super();
            this.skillId = skillId;
            this.name = name;
            this.level = level;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(PreRequiredSkill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    @Generated("jsonschema2pojo")
    public class OptionValue implements Serializable
    {

        @SerializedName("value1")
        @Expose
        private String value1;
        @SerializedName("value2")
        @Expose
        private String value2;
        @SerializedName("value3")
        @Expose
        private String value3;
        @SerializedName("value4")
        @Expose
        private String value4;
        @SerializedName("value5")
        @Expose
        private String value5;
        @SerializedName("value6")
        @Expose
        private String value6;
        @SerializedName("value7")
        @Expose
        private String value7;
        @SerializedName("value8")
        @Expose
        private String value8;
        @SerializedName("value9")
        @Expose
        private String value9;

        @SerializedName("value10")
        @Expose
        private String value10;
        private final static long serialVersionUID = 3104201765010513409L;

        /**
         * No args constructor for use in serialization
         *
         */
        public OptionValue() {
        }

        /**
         *
         * @param value6
         * @param value5
         * @param value8
         * @param value7
         * @param value2
         * @param value1
         * @param value4
         * @param value3
         * @param value9
         */
        public OptionValue(String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9,String value10) {
            super();
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
            this.value5 = value5;
            this.value6 = value6;
            this.value7 = value7;
            this.value8 = value8;
            this.value9 = value9;
            this.value10 = value10;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        public String getValue4() {
            return value4;
        }

        public void setValue4(String value4) {
            this.value4 = value4;
        }

        public String getValue5() {
            return value5;
        }

        public void setValue5(String value5) {
            this.value5 = value5;
        }

        public String getValue6() {
            return value6;
        }

        public void setValue6(String value6) {
            this.value6 = value6;
        }

        public String getValue7() {
            return value7;
        }

        public void setValue7(String value7) {
            this.value7 = value7;
        }

        public String getValue8() {
            return value8;
        }

        public void setValue8(String value8) {
            this.value8 = value8;
        }

        public String getValue9() {
            return value9;
        }

        public void setValue9(String value9) {
            this.value9 = value9;
        }

        public String getValue10() {
            return value10;
        }

        public void setValue10(String value9) {
            this.value10 = value10;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(OptionValue.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("value1");
            sb.append('=');
            sb.append(((this.value1 == null)?"<null>":this.value1));
            sb.append(',');
            sb.append("value2");
            sb.append('=');
            sb.append(((this.value2 == null)?"<null>":this.value2));
            sb.append(',');
            sb.append("value3");
            sb.append('=');
            sb.append(((this.value3 == null)?"<null>":this.value3));
            sb.append(',');
            sb.append("value4");
            sb.append('=');
            sb.append(((this.value4 == null)?"<null>":this.value4));
            sb.append(',');
            sb.append("value5");
            sb.append('=');
            sb.append(((this.value5 == null)?"<null>":this.value5));
            sb.append(',');
            sb.append("value6");
            sb.append('=');
            sb.append(((this.value6 == null)?"<null>":this.value6));
            sb.append(',');
            sb.append("value7");
            sb.append('=');
            sb.append(((this.value7 == null)?"<null>":this.value7));
            sb.append(',');
            sb.append("value8");
            sb.append('=');
            sb.append(((this.value8 == null)?"<null>":this.value8));
            sb.append(',');
            sb.append("value9");
            sb.append('=');
            sb.append(((this.value9 == null)?"<null>":this.value9));
            sb.append(',');
            sb.append("value10");
            sb.append('=');
            sb.append(((this.value10 == null)?"<null>":this.value10));
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
    public static class LevelInfo implements Serializable
    {

        @SerializedName("optionDesc")
        @Expose
        private String optionDesc;
        @SerializedName("rows")
        @Expose
        private List<Row> rows = null;
        private final static long serialVersionUID = -2955890755908375942L;

        /**
         * No args constructor for use in serialization
         *
         */
        public LevelInfo() {
        }

        /**
         *
         * @param optionDesc
         * @param rows
         */
        public LevelInfo(String optionDesc, List<Row> rows) {
            super();
            this.optionDesc = optionDesc;
            this.rows = rows;
        }

        public String getOptionDesc() {
            return optionDesc;
        }

        public void setOptionDesc(String optionDesc) {
            this.optionDesc = optionDesc;
        }

        public List<Row> getRows() {
            return rows;
        }

        public void setRows(List<Row> rows) {
            this.rows = rows;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(LevelInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("optionDesc");
            sb.append('=');
            sb.append(((this.optionDesc == null)?"<null>":this.optionDesc));
            sb.append(',');
            sb.append("rows");
            sb.append('=');
            sb.append(((this.rows == null)?"<null>":this.rows));
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
    public static class JobGrowLevel implements Serializable
    {

        @SerializedName("jobGrowId")
        @Expose
        private String jobGrowId;
        @SerializedName("jobGrowName")
        @Expose
        private String jobGrowName;
        @SerializedName("masterLevel")
        @Expose
        private Integer masterLevel;
        private final static long serialVersionUID = 1236447105622274935L;

        /**
         * No args constructor for use in serialization
         *
         */
        public JobGrowLevel() {
        }

        /**
         *
         * @param masterLevel
         * @param jobGrowId
         * @param jobGrowName
         */
        public JobGrowLevel(String jobGrowId, String jobGrowName, Integer masterLevel) {
            super();
            this.jobGrowId = jobGrowId;
            this.jobGrowName = jobGrowName;
            this.masterLevel = masterLevel;
        }

        public String getJobGrowId() {
            return jobGrowId;
        }

        public void setJobGrowId(String jobGrowId) {
            this.jobGrowId = jobGrowId;
        }

        public String getJobGrowName() {
            return jobGrowName;
        }

        public void setJobGrowName(String jobGrowName) {
            this.jobGrowName = jobGrowName;
        }

        public Integer getMasterLevel() {
            return masterLevel;
        }

        public void setMasterLevel(Integer masterLevel) {
            this.masterLevel = masterLevel;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(JobGrowLevel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("jobGrowId");
            sb.append('=');
            sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
            sb.append(',');
            sb.append("jobGrowName");
            sb.append('=');
            sb.append(((this.jobGrowName == null)?"<null>":this.jobGrowName));
            sb.append(',');
            sb.append("masterLevel");
            sb.append('=');
            sb.append(((this.masterLevel == null)?"<null>":this.masterLevel));
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
    public static class ConsumeItem implements Serializable
    {

        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("value")
        @Expose
        private Integer value;
        private final static long serialVersionUID = -5561653700880265341L;

        /**
         * No args constructor for use in serialization
         *
         */
        public ConsumeItem() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         * @param value
         */
        public ConsumeItem(String itemId, String itemName, Integer value) {
            super();
            this.itemId = itemId;
            this.itemName = itemName;
            this.value = value;
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

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ConsumeItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("itemId");
            sb.append('=');
            sb.append(((this.itemId == null)?"<null>":this.itemId));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
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
}
