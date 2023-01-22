
package com.dfoff.demo.Domain.ForCharacter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class EquipmentDetailJsonDto {

    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemRarity")
    @Expose
    private String itemRarity;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemTypeDetail")
    @Expose
    private String itemTypeDetail;
    @SerializedName("itemAvailableLevel")
    @Expose
    private Integer itemAvailableLevel;
    @SerializedName("itemObtainInfo")
    @Expose
    private String itemObtainInfo;
    @SerializedName("itemExplain")
    @Expose
    private String itemExplain;
    @SerializedName("itemExplainDetail")
    @Expose
    private String itemExplainDetail;
    @SerializedName("itemFlavorText")
    @Expose
    private String itemFlavorText;
    @SerializedName("setItemId")
    @Expose
    private Object setItemId;
    @SerializedName("setItemName")
    @Expose
    private Object setItemName;
    @SerializedName("itemStatus")
    @Expose
    private List<Itemstatus> itemStatus = null;
    @SerializedName("growInfo")
    @Expose
    private GrowInfo growInfo;
    @SerializedName("itemBuff")
    @Expose
    private ItemBuff itemBuff;
    @SerializedName("hashtag")
    @Expose
    private List<Object> hashtag = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EquipmentDetailJsonDto() {
    }

    /**
     * 
     * @param itemExplain
     * @param itemAvailableLevel
     * @param itemBuff
     * @param itemType
     * @param itemRarity
     * @param itemObtainInfo
     * @param itemId
     * @param itemName
     * @param itemFlavorText
     * @param itemStatus
     * @param itemTypeDetail
     * @param growInfo
     * @param setItemId
     * @param itemExplainDetail
     * @param setItemName
     * @param hashtag
     */
    public EquipmentDetailJsonDto(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail, Integer itemAvailableLevel, String itemObtainInfo, String itemExplain, String itemExplainDetail, String itemFlavorText, Object setItemId, Object setItemName, List<Itemstatus> itemStatus, GrowInfo growInfo, ItemBuff itemBuff, List<Object> hashtag) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemRarity = itemRarity;
        this.itemType = itemType;
        this.itemTypeDetail = itemTypeDetail;
        this.itemAvailableLevel = itemAvailableLevel;
        this.itemObtainInfo = itemObtainInfo;
        this.itemExplain = itemExplain;
        this.itemExplainDetail = itemExplainDetail;
        this.itemFlavorText = itemFlavorText;
        this.setItemId = setItemId;
        this.setItemName = setItemName;
        this.itemStatus = itemStatus;
        this.growInfo = growInfo;
        this.itemBuff = itemBuff;
        this.hashtag = hashtag;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeDetail() {
        return itemTypeDetail;
    }

    public void setItemTypeDetail(String itemTypeDetail) {
        this.itemTypeDetail = itemTypeDetail;
    }

    public Integer getItemAvailableLevel() {
        return itemAvailableLevel;
    }

    public void setItemAvailableLevel(Integer itemAvailableLevel) {
        this.itemAvailableLevel = itemAvailableLevel;
    }

    public String getItemObtainInfo() {
        return itemObtainInfo;
    }

    public void setItemObtainInfo(String itemObtainInfo) {
        this.itemObtainInfo = itemObtainInfo;
    }

    public String getItemExplain() {
        return itemExplain;
    }

    public void setItemExplain(String itemExplain) {
        this.itemExplain = itemExplain;
    }

    public String getItemExplainDetail() {
        return itemExplainDetail;
    }

    public void setItemExplainDetail(String itemExplainDetail) {
        this.itemExplainDetail = itemExplainDetail;
    }

    public String getItemFlavorText() {
        return itemFlavorText;
    }

    public void setItemFlavorText(String itemFlavorText) {
        this.itemFlavorText = itemFlavorText;
    }

    public Object getSetItemId() {
        return setItemId;
    }

    public void setSetItemId(Object setItemId) {
        this.setItemId = setItemId;
    }

    public Object getSetItemName() {
        return setItemName;
    }

    public void setSetItemName(Object setItemName) {
        this.setItemName = setItemName;
    }

    public List<Itemstatus> getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(List<Itemstatus> itemStatus) {
        this.itemStatus = itemStatus;
    }

    public GrowInfo getGrowInfo() {
        return growInfo;
    }

    public void setGrowInfo(GrowInfo growInfo) {
        this.growInfo = growInfo;
    }

    public ItemBuff getItemBuff() {
        return itemBuff;
    }

    public void setItemBuff(ItemBuff itemBuff) {
        this.itemBuff = itemBuff;
    }

    public List<Object> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<Object> hashtag) {
        this.hashtag = hashtag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EquipmentDetailJsonDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("itemObtainInfo");
        sb.append('=');
        sb.append(((this.itemObtainInfo == null)?"<null>":this.itemObtainInfo));
        sb.append(',');
        sb.append("itemExplain");
        sb.append('=');
        sb.append(((this.itemExplain == null)?"<null>":this.itemExplain));
        sb.append(',');
        sb.append("itemExplainDetail");
        sb.append('=');
        sb.append(((this.itemExplainDetail == null)?"<null>":this.itemExplainDetail));
        sb.append(',');
        sb.append("itemFlavorText");
        sb.append('=');
        sb.append(((this.itemFlavorText == null)?"<null>":this.itemFlavorText));
        sb.append(',');
        sb.append("setItemId");
        sb.append('=');
        sb.append(((this.setItemId == null)?"<null>":this.setItemId));
        sb.append(',');
        sb.append("setItemName");
        sb.append('=');
        sb.append(((this.setItemName == null)?"<null>":this.setItemName));
        sb.append(',');
        sb.append("itemStatus");
        sb.append('=');
        sb.append(((this.itemStatus == null)?"<null>":this.itemStatus));
        sb.append(',');
        sb.append("growInfo");
        sb.append('=');
        sb.append(((this.growInfo == null)?"<null>":this.growInfo));
        sb.append(',');
        sb.append("itemBuff");
        sb.append('=');
        sb.append(((this.itemBuff == null)?"<null>":this.itemBuff));
        sb.append(',');
        sb.append("hashtag");
        sb.append('=');
        sb.append(((this.hashtag == null)?"<null>":this.hashtag));
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
        result = ((result* 31)+((this.itemExplain == null)? 0 :this.itemExplain.hashCode()));
        result = ((result* 31)+((this.itemAvailableLevel == null)? 0 :this.itemAvailableLevel.hashCode()));
        result = ((result* 31)+((this.itemBuff == null)? 0 :this.itemBuff.hashCode()));
        result = ((result* 31)+((this.itemType == null)? 0 :this.itemType.hashCode()));
        result = ((result* 31)+((this.itemRarity == null)? 0 :this.itemRarity.hashCode()));
        result = ((result* 31)+((this.itemObtainInfo == null)? 0 :this.itemObtainInfo.hashCode()));
        result = ((result* 31)+((this.itemId == null)? 0 :this.itemId.hashCode()));
        result = ((result* 31)+((this.itemName == null)? 0 :this.itemName.hashCode()));
        result = ((result* 31)+((this.itemFlavorText == null)? 0 :this.itemFlavorText.hashCode()));
        result = ((result* 31)+((this.itemStatus == null)? 0 :this.itemStatus.hashCode()));
        result = ((result* 31)+((this.itemTypeDetail == null)? 0 :this.itemTypeDetail.hashCode()));
        result = ((result* 31)+((this.growInfo == null)? 0 :this.growInfo.hashCode()));
        result = ((result* 31)+((this.setItemId == null)? 0 :this.setItemId.hashCode()));
        result = ((result* 31)+((this.itemExplainDetail == null)? 0 :this.itemExplainDetail.hashCode()));
        result = ((result* 31)+((this.setItemName == null)? 0 :this.setItemName.hashCode()));
        result = ((result* 31)+((this.hashtag == null)? 0 :this.hashtag.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EquipmentDetailJsonDto) == false) {
            return false;
        }
        EquipmentDetailJsonDto rhs = ((EquipmentDetailJsonDto) other);
        return (((((((((((((((((this.itemExplain == rhs.itemExplain)||((this.itemExplain!= null)&&this.itemExplain.equals(rhs.itemExplain)))&&((this.itemAvailableLevel == rhs.itemAvailableLevel)||((this.itemAvailableLevel!= null)&&this.itemAvailableLevel.equals(rhs.itemAvailableLevel))))&&((this.itemBuff == rhs.itemBuff)||((this.itemBuff!= null)&&this.itemBuff.equals(rhs.itemBuff))))&&((this.itemType == rhs.itemType)||((this.itemType!= null)&&this.itemType.equals(rhs.itemType))))&&((this.itemRarity == rhs.itemRarity)||((this.itemRarity!= null)&&this.itemRarity.equals(rhs.itemRarity))))&&((this.itemObtainInfo == rhs.itemObtainInfo)||((this.itemObtainInfo!= null)&&this.itemObtainInfo.equals(rhs.itemObtainInfo))))&&((this.itemId == rhs.itemId)||((this.itemId!= null)&&this.itemId.equals(rhs.itemId))))&&((this.itemName == rhs.itemName)||((this.itemName!= null)&&this.itemName.equals(rhs.itemName))))&&((this.itemFlavorText == rhs.itemFlavorText)||((this.itemFlavorText!= null)&&this.itemFlavorText.equals(rhs.itemFlavorText))))&&((this.itemStatus == rhs.itemStatus)||((this.itemStatus!= null)&&this.itemStatus.equals(rhs.itemStatus))))&&((this.itemTypeDetail == rhs.itemTypeDetail)||((this.itemTypeDetail!= null)&&this.itemTypeDetail.equals(rhs.itemTypeDetail))))&&((this.growInfo == rhs.growInfo)||((this.growInfo!= null)&&this.growInfo.equals(rhs.growInfo))))&&((this.setItemId == rhs.setItemId)||((this.setItemId!= null)&&this.setItemId.equals(rhs.setItemId))))&&((this.itemExplainDetail == rhs.itemExplainDetail)||((this.itemExplainDetail!= null)&&this.itemExplainDetail.equals(rhs.itemExplainDetail))))&&((this.setItemName == rhs.setItemName)||((this.setItemName!= null)&&this.setItemName.equals(rhs.setItemName))))&&((this.hashtag == rhs.hashtag)||((this.hashtag!= null)&&this.hashtag.equals(rhs.hashtag))));
    }

    @Generated("jsonschema2pojo")
    public static class Total {

        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("level")
        @Expose
        private Integer level;

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

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
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

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.damage == null)? 0 :this.damage.hashCode()));
            result = ((result* 31)+((this.buff == null)? 0 :this.buff.hashCode()));
            result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Total) == false) {
                return false;
            }
            Total rhs = ((Total) other);
            return ((((this.damage == rhs.damage)||((this.damage!= null)&&this.damage.equals(rhs.damage)))&&((this.buff == rhs.buff)||((this.buff!= null)&&this.buff.equals(rhs.buff))))&&((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level))));
        }

    }

    @Generated("jsonschema2pojo")
    public static class Option {

        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("expRate")
        @Expose
        private Integer expRate;
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
         * @param level
         * @param expRate
         * @param buff
         * @param explainDetail
         */
        public Option(Integer level, Integer expRate, String explain, String explainDetail, Integer damage, Integer buff) {
            super();
            this.level = level;
            this.expRate = expRate;
            this.explain = explain;
            this.explainDetail = explainDetail;
            this.damage = damage;
            this.buff = buff;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Integer getExpRate() {
            return expRate;
        }

        public void setExpRate(Integer expRate) {
            this.expRate = expRate;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
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
            result = ((result* 31)+((this.explain == null)? 0 :this.explain.hashCode()));
            result = ((result* 31)+((this.damage == null)? 0 :this.damage.hashCode()));
            result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
            result = ((result* 31)+((this.expRate == null)? 0 :this.expRate.hashCode()));
            result = ((result* 31)+((this.buff == null)? 0 :this.buff.hashCode()));
            result = ((result* 31)+((this.explainDetail == null)? 0 :this.explainDetail.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Option) == false) {
                return false;
            }
            Option rhs = ((Option) other);
            return (((((((this.explain == rhs.explain)||((this.explain!= null)&&this.explain.equals(rhs.explain)))&&((this.damage == rhs.damage)||((this.damage!= null)&&this.damage.equals(rhs.damage))))&&((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level))))&&((this.expRate == rhs.expRate)||((this.expRate!= null)&&this.expRate.equals(rhs.expRate))))&&((this.buff == rhs.buff)||((this.buff!= null)&&this.buff.equals(rhs.buff))))&&((this.explainDetail == rhs.explainDetail)||((this.explainDetail!= null)&&this.explainDetail.equals(rhs.explainDetail))));
        }

    }

    @Generated("jsonschema2pojo")
    public static class Itemstatus {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        /**
         * No args constructor for use in serialization
         *
         */
        public Itemstatus() {
        }

        /**
         *
         * @param name
         * @param value
         */
        public Itemstatus(String name, String value) {
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Itemstatus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Itemstatus) == false) {
                return false;
            }
            Itemstatus rhs = ((Itemstatus) other);
            return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
        }

    }

    @Generated("jsonschema2pojo")
    public static class ItemBuff {

        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        @SerializedName("reinforceSkill")
        @Expose
        private List<Object> reinforceSkill = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public ItemBuff() {
        }

        /**
         *
         * @param explain
         * @param reinforceSkill
         * @param explainDetail
         */
        public ItemBuff(String explain, String explainDetail, List<Object> reinforceSkill) {
            super();
            this.explain = explain;
            this.explainDetail = explainDetail;
            this.reinforceSkill = reinforceSkill;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public List<Object> getReinforceSkill() {
            return reinforceSkill;
        }

        public void setReinforceSkill(List<Object> reinforceSkill) {
            this.reinforceSkill = reinforceSkill;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ItemBuff.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("explain");
            sb.append('=');
            sb.append(((this.explain == null)?"<null>":this.explain));
            sb.append(',');
            sb.append("explainDetail");
            sb.append('=');
            sb.append(((this.explainDetail == null)?"<null>":this.explainDetail));
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

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.explain == null)? 0 :this.explain.hashCode()));
            result = ((result* 31)+((this.reinforceSkill == null)? 0 :this.reinforceSkill.hashCode()));
            result = ((result* 31)+((this.explainDetail == null)? 0 :this.explainDetail.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof ItemBuff) == false) {
                return false;
            }
            ItemBuff rhs = ((ItemBuff) other);
            return ((((this.explain == rhs.explain)||((this.explain!= null)&&this.explain.equals(rhs.explain)))&&((this.reinforceSkill == rhs.reinforceSkill)||((this.reinforceSkill!= null)&&this.reinforceSkill.equals(rhs.reinforceSkill))))&&((this.explainDetail == rhs.explainDetail)||((this.explainDetail!= null)&&this.explainDetail.equals(rhs.explainDetail))));
        }

    }

    @Generated("jsonschema2pojo")
    public static class GrowInfo {

        @SerializedName("total")
        @Expose
        private Total total;
        @SerializedName("options")
        @Expose
        private List<Option> options = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public GrowInfo() {
        }

        /**
         *
         * @param total
         * @param options
         */
        public GrowInfo(Total total, List<Option> options) {
            super();
            this.total = total;
            this.options = options;
        }

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
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
            result = ((result* 31)+((this.options == null)? 0 :this.options.hashCode()));
            result = ((result* 31)+((this.total == null)? 0 :this.total.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof GrowInfo) == false) {
                return false;
            }
            GrowInfo rhs = ((GrowInfo) other);
            return (((this.options == rhs.options)||((this.options!= null)&&this.options.equals(rhs.options)))&&((this.total == rhs.total)||((this.total!= null)&&this.total.equals(rhs.total))));
        }

    }
}
