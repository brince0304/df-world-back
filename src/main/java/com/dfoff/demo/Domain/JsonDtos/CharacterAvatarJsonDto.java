
package com.dfoff.demo.Domain.JsonDtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterAvatarJsonDto implements Serializable
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
    @SerializedName("avatar")
    @Expose
    private List<Avatar> avatar = new ArrayList<Avatar>();
    private final static long serialVersionUID = -7578304647404968984L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterAvatarJsonDto() {
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
     * @param avatar
     * @param characterId
     * @param guildId
     * @param guildName
     */
    public CharacterAvatarJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, List<Avatar> avatar) {
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
        this.avatar = avatar;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public CharacterAvatarJsonDto withCharacterId(String characterId) {
        this.characterId = characterId;
        return this;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public CharacterAvatarJsonDto withCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CharacterAvatarJsonDto withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public CharacterAvatarJsonDto withJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getJobGrowId() {
        return jobGrowId;
    }

    public void setJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
    }

    public CharacterAvatarJsonDto withJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public CharacterAvatarJsonDto withJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGrowName() {
        return jobGrowName;
    }

    public void setJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
    }

    public CharacterAvatarJsonDto withJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
        return this;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public CharacterAvatarJsonDto withAdventureName(String adventureName) {
        this.adventureName = adventureName;
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public CharacterAvatarJsonDto withGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public CharacterAvatarJsonDto withGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public List<Avatar> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<Avatar> avatar) {
        this.avatar = avatar;
    }

    public CharacterAvatarJsonDto withAvatar(List<Avatar> avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterAvatarJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("avatar");
        sb.append('=');
        sb.append(((this.avatar == null)?"<null>":this.avatar));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Generated("jsonschema2pojo")
    public static class Random implements Serializable
    {

        @SerializedName("itemId")
        @Expose
        private Object itemId;
        @SerializedName("itemName")
        @Expose
        private Object itemName;
        private final static long serialVersionUID = 3117529533589774524L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Random() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         */
        public Random(Object itemId, Object itemName) {
            super();
            this.itemId = itemId;
            this.itemName = itemName;
        }

        public Object getItemId() {
            return itemId;
        }

        public void setItemId(Object itemId) {
            this.itemId = itemId;
        }

        public Random withItemId(Object itemId) {
            this.itemId = itemId;
            return this;
        }

        public Object getItemName() {
            return itemName;
        }

        public void setItemName(Object itemName) {
            this.itemName = itemName;
        }

        public Random withItemName(Object itemName) {
            this.itemName = itemName;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Random.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
    public static class Emblem implements Serializable
    {

        @SerializedName("slotNo")
        @Expose
        private Integer slotNo;
        @SerializedName("slotColor")
        @Expose
        private String slotColor;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("itemRarity")
        @Expose
        private String itemRarity;
        private final static long serialVersionUID = 7632970361119800045L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Emblem() {
        }

        /**
         *
         * @param itemName
         * @param slotColor
         * @param itemRarity
         * @param slotNo
         */
        public Emblem(Integer slotNo, String slotColor, String itemName, String itemRarity) {
            super();
            this.slotNo = slotNo;
            this.slotColor = slotColor;
            this.itemName = itemName;
            this.itemRarity = itemRarity;
        }

        public Integer getSlotNo() {
            return slotNo;
        }

        public void setSlotNo(Integer slotNo) {
            this.slotNo = slotNo;
        }

        public Emblem withSlotNo(Integer slotNo) {
            this.slotNo = slotNo;
            return this;
        }

        public String getSlotColor() {
            return slotColor;
        }

        public void setSlotColor(String slotColor) {
            this.slotColor = slotColor;
        }

        public Emblem withSlotColor(String slotColor) {
            this.slotColor = slotColor;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Emblem withItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public String getItemRarity() {
            return itemRarity;
        }

        public void setItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
        }

        public Emblem withItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Emblem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("slotNo");
            sb.append('=');
            sb.append(((this.slotNo == null)?"<null>":this.slotNo));
            sb.append(',');
            sb.append("slotColor");
            sb.append('=');
            sb.append(((this.slotColor == null)?"<null>":this.slotColor));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
            sb.append(',');
            sb.append("itemRarity");
            sb.append('=');
            sb.append(((this.itemRarity == null)?"<null>":this.itemRarity));
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
    public static class Clone implements Serializable
    {

        @SerializedName("itemId")
        @Expose
        private Object itemId;
        @SerializedName("itemName")
        @Expose
        private Object itemName;
        private final static long serialVersionUID = -3956773463165700815L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Clone() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         */
        public Clone(Object itemId, Object itemName) {
            super();
            this.itemId = itemId;
            this.itemName = itemName;
        }

        public Object getItemId() {
            return itemId;
        }

        public void setItemId(Object itemId) {
            this.itemId = itemId;
        }

        public Clone withItemId(Object itemId) {
            this.itemId = itemId;
            return this;
        }

        public Object getItemName() {
            return itemName;
        }

        public void setItemName(Object itemName) {
            this.itemName = itemName;
        }

        public Clone withItemName(Object itemName) {
            this.itemName = itemName;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Clone.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
    public static class Avatar implements Serializable
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
        @SerializedName("itemRarity")
        @Expose
        private String itemRarity;
        @SerializedName("clone")
        @Expose
        private Clone clone;
        @SerializedName("random")
        @Expose
        private Random random;
        @SerializedName("optionAbility")
        @Expose
        private Object optionAbility;
        @SerializedName("emblems")
        @Expose
        private List<Emblem> emblems = new ArrayList<Emblem>();
        private final static long serialVersionUID = 4093894512733130854L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Avatar() {
        }

        /**
         *
         * @param slotName
         * @param itemId
         * @param random
         * @param itemName
         * @param emblems
         * @param optionAbility
         * @param itemRarity
         * @param clone
         * @param slotId
         */
        public Avatar(String slotId, String slotName, String itemId, String itemName, String itemRarity, Clone clone, Random random, Object optionAbility, List<Emblem> emblems) {
            super();
            this.slotId = slotId;
            this.slotName = slotName;
            this.itemId = itemId;
            this.itemName = itemName;
            this.itemRarity = itemRarity;
            this.clone = clone;
            this.random = random;
            this.optionAbility = optionAbility;
            this.emblems = emblems;
        }

        public String getSlotId() {
            return slotId;
        }

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public Avatar withSlotId(String slotId) {
            this.slotId = slotId;
            return this;
        }

        public String getSlotName() {
            return slotName;
        }

        public void setSlotName(String slotName) {
            this.slotName = slotName;
        }

        public Avatar withSlotName(String slotName) {
            this.slotName = slotName;
            return this;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Avatar withItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Avatar withItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public String getItemRarity() {
            return itemRarity;
        }

        public void setItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
        }

        public Avatar withItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
            return this;
        }

        public Clone getClone() {
            return clone;
        }

        public void setClone(Clone clone) {
            this.clone = clone;
        }

        public Avatar withClone(Clone clone) {
            this.clone = clone;
            return this;
        }

        public Random getRandom() {
            return random;
        }

        public void setRandom(Random random) {
            this.random = random;
        }

        public Avatar withRandom(Random random) {
            this.random = random;
            return this;
        }

        public Object getOptionAbility() {
            return optionAbility;
        }

        public void setOptionAbility(Object optionAbility) {
            this.optionAbility = optionAbility;
        }

        public Avatar withOptionAbility(Object optionAbility) {
            this.optionAbility = optionAbility;
            return this;
        }

        public List<Emblem> getEmblems() {
            return emblems;
        }

        public void setEmblems(List<Emblem> emblems) {
            this.emblems = emblems;
        }

        public Avatar withEmblems(List<Emblem> emblems) {
            this.emblems = emblems;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Avatar.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
            sb.append("itemRarity");
            sb.append('=');
            sb.append(((this.itemRarity == null)?"<null>":this.itemRarity));
            sb.append(',');
            sb.append("clone");
            sb.append('=');
            sb.append(((this.clone == null)?"<null>":this.clone));
            sb.append(',');
            sb.append("random");
            sb.append('=');
            sb.append(((this.random == null)?"<null>":this.random));
            sb.append(',');
            sb.append("optionAbility");
            sb.append('=');
            sb.append(((this.optionAbility == null)?"<null>":this.optionAbility));
            sb.append(',');
            sb.append("emblems");
            sb.append('=');
            sb.append(((this.emblems == null)?"<null>":this.emblems));
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
