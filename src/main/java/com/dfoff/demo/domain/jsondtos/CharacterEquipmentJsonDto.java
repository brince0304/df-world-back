
package com.dfoff.demo.domain.jsondtos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CharacterEquipmentJsonDto {

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
    private List<Equipment> equipment;
    @SerializedName("setItemInfo")
    @Expose
    private List<Object> setItemInfo;

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

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Object> getSetItemInfo() {
        return setItemInfo;
    }

    public void setSetItemInfo(List<Object> setItemInfo) {
        this.setItemInfo = setItemInfo;
    }

    @Generated("jsonschema2pojo")
    public static class Option__1 {

        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("expRate")
        @Expose
        private Double expRate;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("default")
        @Expose
        private Default _default;
        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("transfer")
        @Expose
        private Boolean transfer;

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Double getExpRate() {
            return expRate;
        }

        public void setExpRate(Double expRate) {
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

        public Default getDefault() {
            return _default;
        }

        public void setDefault(Default _default) {
            this._default = _default;
        }

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
        }

        public Boolean getTransfer() {
            return transfer;
        }

        public void setTransfer(Boolean transfer) {
            this.transfer = transfer;
        }

    }

    @Generated("jsonschema2pojo")
    public static class Option__2 {

        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;
        @SerializedName("damage")
        @Expose
        private Integer damage;

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
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

    }

    @Generated("jsonschema2pojo")
    public static class UpgradeInfo {

        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;

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

    }

    @Generated("jsonschema2pojo")
    public static class Option__4 {

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

    }

    @Generated("jsonschema2pojo")
    public static class Status {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

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

    }

    @Generated("jsonschema2pojo")
    public static class GrowInfo {

        @SerializedName("total")
        @Expose
        private Total total;
        @SerializedName("options")
        @Expose
        private List<Option__1> options;
        @SerializedName("transfer")
        @Expose
        private Boolean transfer;

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public List<Option__1> getOptions() {
            return options;
        }

        public void setOptions(List<Option__1> options) {
            this.options = options;
        }

        public Boolean getTransfer() {
            return transfer;
        }

        public void setTransfer(Boolean transfer) {
            this.transfer = transfer;
        }

    }

    @Generated("jsonschema2pojo")
    public static class Option__3 {

        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
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

    }

    @Generated("jsonschema2pojo")
    public static class Option {

        @SerializedName("buff")
        @Expose
        private Integer buff;
        @SerializedName("explain")
        @Expose
        private String explain;
        @SerializedName("explainDetail")
        @Expose
        private String explainDetail;

        public Integer getBuff() {
            return buff;
        }

        public void setBuff(Integer buff) {
            this.buff = buff;
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

    }

    @Generated("jsonschema2pojo")
    public static class IspinsInfo {

        @SerializedName("options")
        @Expose
        private List<Option__3> options;

        public List<Option__3> getOptions() {
            return options;
        }

        public void setOptions(List<Option__3> options) {
            this.options = options;
        }

    }

    @Generated("jsonschema2pojo")
    public static class MachineRevolutionInfo {

        @SerializedName("options")
        @Expose
        private List<Option__2> options;

        public List<Option__2> getOptions() {
            return options;
        }

        public void setOptions(List<Option__2> options) {
            this.options = options;
        }

    }

    @Generated("jsonschema2pojo")
    public static class Equipment {

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
        @SerializedName("itemTypeId")
        @Expose
        private String itemTypeId;
        @SerializedName("itemType")
        @Expose
        private String itemType;
        @SerializedName("itemTypeDetailId")
        @Expose
        private String itemTypeDetailId;
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
        @SerializedName("bakalInfo")
        @Expose
        private BakalInfo bakalInfo;
        @SerializedName("upgradeInfo")
        @Expose
        private UpgradeInfo upgradeInfo;
        @SerializedName("growInfo")
        @Expose
        private GrowInfo growInfo;
        @SerializedName("engraveName")
        @Expose
        private Boolean engraveName;
        @SerializedName("machineRevolutionInfo")
        @Expose
        private MachineRevolutionInfo machineRevolutionInfo;
        @SerializedName("ispinsInfo")
        @Expose
        private IspinsInfo ispinsInfo;
        @SerializedName("dimensionCloisterInfo")
        @Expose
        private DimensionCloisterInfo dimensionCloisterInfo;

        public String getSlotId() {
            return slotId;
        }

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public String getSlotName() {
            return slotName;
        }

        public void setSlotName(String slotName) {
            this.slotName = slotName;
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

        public String getItemTypeId() {
            return itemTypeId;
        }

        public void setItemTypeId(String itemTypeId) {
            this.itemTypeId = itemTypeId;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemTypeDetailId() {
            return itemTypeDetailId;
        }

        public void setItemTypeDetailId(String itemTypeDetailId) {
            this.itemTypeDetailId = itemTypeDetailId;
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

        public String getItemRarity() {
            return itemRarity;
        }

        public void setItemRarity(String itemRarity) {
            this.itemRarity = itemRarity;
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

        public Integer getReinforce() {
            return reinforce;
        }

        public void setReinforce(Integer reinforce) {
            this.reinforce = reinforce;
        }

        public String getItemGradeName() {
            return itemGradeName;
        }

        public void setItemGradeName(String itemGradeName) {
            this.itemGradeName = itemGradeName;
        }

        public Enchant getEnchant() {
            return enchant;
        }

        public void setEnchant(Enchant enchant) {
            this.enchant = enchant;
        }

        public String getAmplificationName() {
            return amplificationName;
        }

        public void setAmplificationName(String amplificationName) {
            this.amplificationName = amplificationName;
        }

        public Integer getRefine() {
            return refine;
        }

        public void setRefine(Integer refine) {
            this.refine = refine;
        }

        public BakalInfo getBakalInfo() {
            return bakalInfo;
        }

        public void setBakalInfo(BakalInfo bakalInfo) {
            this.bakalInfo = bakalInfo;
        }

        public UpgradeInfo getUpgradeInfo() {
            return upgradeInfo;
        }

        public void setUpgradeInfo(UpgradeInfo upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
        }

        public GrowInfo getGrowInfo() {
            return growInfo;
        }

        public void setGrowInfo(GrowInfo growInfo) {
            this.growInfo = growInfo;
        }

        public Boolean getEngraveName() {
            return engraveName;
        }

        public void setEngraveName(Boolean engraveName) {
            this.engraveName = engraveName;
        }

        public MachineRevolutionInfo getMachineRevolutionInfo() {
            return machineRevolutionInfo;
        }

        public void setMachineRevolutionInfo(MachineRevolutionInfo machineRevolutionInfo) {
            this.machineRevolutionInfo = machineRevolutionInfo;
        }

        public IspinsInfo getIspinsInfo() {
            return ispinsInfo;
        }

        public void setIspinsInfo(IspinsInfo ispinsInfo) {
            this.ispinsInfo = ispinsInfo;
        }

        public DimensionCloisterInfo getDimensionCloisterInfo() {
            return dimensionCloisterInfo;
        }

        public void setDimensionCloisterInfo(DimensionCloisterInfo dimensionCloisterInfo) {
            this.dimensionCloisterInfo = dimensionCloisterInfo;
        }

    }

    @Generated("jsonschema2pojo")
    public static class Enchant {

        @SerializedName("status")
        @Expose
        private List<Status> status;
        @SerializedName("explain")
        @Expose
        private String explain;

        public List<Status> getStatus() {
            return status;
        }

        public void setStatus(List<Status> status) {
            this.status = status;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

    }

    @Generated("jsonschema2pojo")
    public static class DimensionCloisterInfo {

        @SerializedName("options")
        @Expose
        private List<Option__4> options;

        public List<Option__4> getOptions() {
            return options;
        }

        public void setOptions(List<Option__4> options) {
            this.options = options;
        }

    }

    @Generated("jsonschema2pojo")
    public static class Default {

        @SerializedName("damage")
        @Expose
        private Integer damage;
        @SerializedName("buff")
        @Expose
        private Integer buff;

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

    }

    @Generated("jsonschema2pojo")
    public static class BakalInfo {

        @SerializedName("options")
        @Expose
        private List<Option> options;

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }

    }
}
