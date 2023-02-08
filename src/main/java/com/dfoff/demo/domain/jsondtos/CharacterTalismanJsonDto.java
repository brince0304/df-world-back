
package com.dfoff.demo.domain.jsondtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterTalismanJsonDto implements Serializable
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
    @SerializedName("talismans")
    @Expose
    private List<Talisman> talismans = new ArrayList<Talisman>();
    private final static long serialVersionUID = 2083754917137148941L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CharacterTalismanJsonDto() {
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
     * @param talismans
     * @param characterId
     * @param guildId
     * @param guildName
     */
    public CharacterTalismanJsonDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, List<Talisman> talismans) {
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
        this.talismans = talismans;
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

    public List<Talisman> getTalismans() {
        return talismans;
    }

    public void setTalismans(List<Talisman> talismans) {
        this.talismans = talismans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CharacterTalismanJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("talismans");
        sb.append('=');
        sb.append(((this.talismans == null)?"<null>":this.talismans));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Generated("jsonschema2pojo")
    public static class Talisman__1 implements Serializable
    {

        @SerializedName("slotNo")
        @Expose
        private Integer slotNo;
        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("runeTypes")
        @Expose
        private List<String> runeTypes = new ArrayList<String>();
        private final static long serialVersionUID = 1280662048321047124L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Talisman__1() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         * @param slotNo
         * @param runeTypes
         */
        public Talisman__1(Integer slotNo, String itemId, String itemName, List<String> runeTypes) {
            super();
            this.slotNo = slotNo;
            this.itemId = itemId;
            this.itemName = itemName;
            this.runeTypes = runeTypes;
        }

        public Integer getSlotNo() {
            return slotNo;
        }

        public void setSlotNo(Integer slotNo) {
            this.slotNo = slotNo;
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

        public List<String> getRuneTypes() {
            return runeTypes;
        }

        public void setRuneTypes(List<String> runeTypes) {
            this.runeTypes = runeTypes;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Talisman__1.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("slotNo");
            sb.append('=');
            sb.append(((this.slotNo == null)?"<null>":this.slotNo));
            sb.append(',');
            sb.append("itemId");
            sb.append('=');
            sb.append(((this.itemId == null)?"<null>":this.itemId));
            sb.append(',');
            sb.append("itemName");
            sb.append('=');
            sb.append(((this.itemName == null)?"<null>":this.itemName));
            sb.append(',');
            sb.append("runeTypes");
            sb.append('=');
            sb.append(((this.runeTypes == null)?"<null>":this.runeTypes));
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
    public static class Talisman implements Serializable
    {

        @SerializedName("talisman")
        @Expose
        private Talisman__1 talisman;
        @SerializedName("runes")
        @Expose
        private List<Rune> runes = new ArrayList<Rune>();
        private final static long serialVersionUID = 230245150981676054L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Talisman() {
        }

        /**
         *
         * @param runes
         * @param talisman
         */
        public Talisman(Talisman__1 talisman, List<Rune> runes) {
            super();
            this.talisman = talisman;
            this.runes = runes;
        }

        public Talisman__1 getTalisman() {
            return talisman;
        }

        public void setTalisman(Talisman__1 talisman) {
            this.talisman = talisman;
        }

        public List<Rune> getRunes() {
            return runes;
        }

        public void setRunes(List<Rune> runes) {
            this.runes = runes;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Talisman.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("talisman");
            sb.append('=');
            sb.append(((this.talisman == null)?"<null>":this.talisman));
            sb.append(',');
            sb.append("runes");
            sb.append('=');
            sb.append(((this.runes == null)?"<null>":this.runes));
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
    public static class Rune implements Serializable
    {

        @SerializedName("slotNo")
        @Expose
        private Integer slotNo;
        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        private final static long serialVersionUID = 7896969644528375259L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Rune() {
        }

        /**
         *
         * @param itemId
         * @param itemName
         * @param slotNo
         */
        public Rune(Integer slotNo, String itemId, String itemName) {
            super();
            this.slotNo = slotNo;
            this.itemId = itemId;
            this.itemName = itemName;
        }

        public Integer getSlotNo() {
            return slotNo;
        }

        public void setSlotNo(Integer slotNo) {
            this.slotNo = slotNo;
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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Rune.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("slotNo");
            sb.append('=');
            sb.append(((this.slotNo == null)?"<null>":this.slotNo));
            sb.append(',');
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
}
