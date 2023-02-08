package com.dfoff.demo.domain.jsondtos;


import com.dfoff.demo.domain.CharacterEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
public class CharacterAbilityDto {


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

        private Integer adventureFame;

        private List<Buff> buff = new ArrayList<>();

        private List<Status__1> status = new ArrayList<>();



        public static CharacterAbilityJSONDto toAbilityJSONDTO(CharacterAbilityDto dto){
            return CharacterAbilityJSONDto.builder()
                    .characterId(dto.getCharacterId())
                    .characterName(dto.getCharacterName())
                    .level(dto.getLevel())
                    .jobId(dto.getJobId())
                    .jobGrowId(dto.getJobGrowId())
                    .jobName(dto.getJobName())
                    .jobGrowName(dto.getJobGrowName())
                    .adventureName(dto.getAdventureName())
                    .guildId(dto.getGuildId())
                    .guildName(dto.getGuildName())
                    .buff(dto.getBuff())
                    .status(dto.getStatus())
                    .build();
        }

        public static CharacterEntity toEntity(CharacterAbilityDto dto,String ServerId){
            return CharacterEntity.builder()
                    .characterId(dto.getCharacterId())
                    .characterName(dto.getCharacterName())
                    .level(dto.getLevel())
                    .jobId(dto.getJobId())
                    .jobGrowId(dto.getJobGrowId())
                    .jobName(dto.getJobName())
                    .jobGrowName(dto.getJobGrowName())
                    .adventureName(dto.getAdventureName())
                    .adventureFame(dto.getAdventureFame())
                    .adventureName(dto.getAdventureName())
                    .serverId(ServerId)
                    .guildId(dto.getGuildId())
                    .guildName(dto.getGuildName())
                    .build();
        }


    @Builder
    public static class CharacterAbilityJSONDto {
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
        @SerializedName("buff")
        @Expose
        private List<Buff> buff = null;
        @SerializedName("status")
        @Expose
        private List<Status__1> status = null;

        /**
         * No args constructor for use in serialization
         */
        public CharacterAbilityJSONDto() {
        }



        public CharacterAbilityDto toDto() {
            return CharacterAbilityDto.builder()
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
                    .buff(buff)
                    .status(status)
                    .build();
        }


        /**
         * @param jobName
         * @param jobId
         * @param level
         * @param jobGrowId
         * @param characterName
         * @param adventureName
         * @param jobGrowName
         * @param characterId
         * @param guildId
         * @param buff
         * @param guildName
         * @param status
         */
        public CharacterAbilityJSONDto(String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, List<Buff> buff, List<Status__1> status) {
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
            this.buff = buff;
            this.status = status;
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

        public List<Buff> getBuff() {
            return buff;
        }

        public void setBuff(List<Buff> buff) {
            this.buff = buff;
        }

        public List<Status__1> getStatus() {
            return status;
        }

        public void setStatus(List<Status__1> status) {
            this.status = status;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(CharacterAbilityJSONDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("characterId");
            sb.append('=');
            sb.append(((this.characterId == null) ? "<null>" : this.characterId));
            sb.append(',');
            sb.append("characterName");
            sb.append('=');
            sb.append(((this.characterName == null) ? "<null>" : this.characterName));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null) ? "<null>" : this.level));
            sb.append(',');
            sb.append("jobId");
            sb.append('=');
            sb.append(((this.jobId == null) ? "<null>" : this.jobId));
            sb.append(',');
            sb.append("jobGrowId");
            sb.append('=');
            sb.append(((this.jobGrowId == null) ? "<null>" : this.jobGrowId));
            sb.append(',');
            sb.append("jobName");
            sb.append('=');
            sb.append(((this.jobName == null) ? "<null>" : this.jobName));
            sb.append(',');
            sb.append("jobGrowName");
            sb.append('=');
            sb.append(((this.jobGrowName == null) ? "<null>" : this.jobGrowName));
            sb.append(',');
            sb.append("adventureName");
            sb.append('=');
            sb.append(((this.adventureName == null) ? "<null>" : this.adventureName));
            sb.append(',');
            sb.append("guildId");
            sb.append('=');
            sb.append(((this.guildId == null) ? "<null>" : this.guildId));
            sb.append(',');
            sb.append("guildName");
            sb.append('=');
            sb.append(((this.guildName == null) ? "<null>" : this.guildName));
            sb.append(',');
            sb.append("buff");
            sb.append('=');
            sb.append(((this.buff == null) ? "<null>" : this.buff));
            sb.append(',');
            sb.append("status");
            sb.append('=');
            sb.append(((this.status == null) ? "<null>" : this.status));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result * 31) + ((this.jobName == null) ? 0 : this.jobName.hashCode()));
            result = ((result * 31) + ((this.level == null) ? 0 : this.level.hashCode()));
            result = ((result * 31) + ((this.adventureName == null) ? 0 : this.adventureName.hashCode()));
            result = ((result * 31) + ((this.jobGrowName == null) ? 0 : this.jobGrowName.hashCode()));
            result = ((result * 31) + ((this.guildId == null) ? 0 : this.guildId.hashCode()));
            result = ((result * 31) + ((this.guildName == null) ? 0 : this.guildName.hashCode()));
            result = ((result * 31) + ((this.jobId == null) ? 0 : this.jobId.hashCode()));
            result = ((result * 31) + ((this.jobGrowId == null) ? 0 : this.jobGrowId.hashCode()));
            result = ((result * 31) + ((this.characterName == null) ? 0 : this.characterName.hashCode()));
            result = ((result * 31) + ((this.characterId == null) ? 0 : this.characterId.hashCode()));
            result = ((result * 31) + ((this.buff == null) ? 0 : this.buff.hashCode()));
            result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof CharacterAbilityJSONDto) == false) {
                return false;
            }
            CharacterAbilityJSONDto rhs = ((CharacterAbilityJSONDto) other);
            return (((((((((((((this.jobName == rhs.jobName) || ((this.jobName != null) && this.jobName.equals(rhs.jobName))) && ((this.level == rhs.level) || ((this.level != null) && this.level.equals(rhs.level)))) && ((this.adventureName == rhs.adventureName) || ((this.adventureName != null) && this.adventureName.equals(rhs.adventureName)))) && ((this.jobGrowName == rhs.jobGrowName) || ((this.jobGrowName != null) && this.jobGrowName.equals(rhs.jobGrowName)))) && ((this.guildId == rhs.guildId) || ((this.guildId != null) && this.guildId.equals(rhs.guildId)))) && ((this.guildName == rhs.guildName) || ((this.guildName != null) && this.guildName.equals(rhs.guildName)))) && ((this.jobId == rhs.jobId) || ((this.jobId != null) && this.jobId.equals(rhs.jobId)))) && ((this.jobGrowId == rhs.jobGrowId) || ((this.jobGrowId != null) && this.jobGrowId.equals(rhs.jobGrowId)))) && ((this.characterName == rhs.characterName) || ((this.characterName != null) && this.characterName.equals(rhs.characterName)))) && ((this.characterId == rhs.characterId) || ((this.characterId != null) && this.characterId.equals(rhs.characterId)))) && ((this.buff == rhs.buff) || ((this.buff != null) && this.buff.equals(rhs.buff)))) && ((this.status == rhs.status) || ((this.status != null) && this.status.equals(rhs.status))));
        }

    }

    public static class Buff {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("status")
        @Expose
        private List<Status> status = new ArrayList<>();

        /**
         * No args constructor for use in serialization
         */
        public Buff() {
        }

        /**
         * @param level
         * @param name
         * @param status
         */
        public Buff(String name, Integer level, List<Status> status) {
            super();
            this.name = name;
            this.level = level;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public List<Status> getStatus() {
            return status;
        }

        public void setStatus(List<Status> status) {
            this.status = status;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Buff.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null) ? "<null>" : this.name));
            sb.append(',');
            sb.append("level");
            sb.append('=');
            sb.append(((this.level == null) ? "<null>" : this.level));
            sb.append(',');
            sb.append("status");
            sb.append('=');
            sb.append(((this.status == null) ? "<null>" : this.status));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
            result = ((result * 31) + ((this.level == null) ? 0 : this.level.hashCode()));
            result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Buff) == false) {
                return false;
            }
            Buff rhs = ((Buff) other);
            return ((((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))) && ((this.level == rhs.level) || ((this.level != null) && this.level.equals(rhs.level)))) && ((this.status == rhs.status) || ((this.status != null) && this.status.equals(rhs.status))));
        }

    }

    public static class Status__1 {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        /**
         * No args constructor for use in serialization
         */
        public Status__1() {
        }

        /**
         * @param name
         * @param value
         */
        public Status__1(String name, String value) {
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
            sb.append(Status__1.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null) ? "<null>" : this.name));
            sb.append(',');
            sb.append("value");
            sb.append('=');
            sb.append(((this.value == null) ? "<null>" : this.value));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
            result = ((result * 31) + ((this.value == null) ? 0 : this.value.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Status__1) == false) {
                return false;
            }
            Status__1 rhs = ((Status__1) other);
            return (((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))) && ((this.value == rhs.value) || ((this.value != null) && this.value.equals(rhs.value))));
        }

    }

    public static class Status {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private Double value;

        /**
         * No args constructor for use in serialization
         */
        public Status() {
        }

        /**
         * @param name
         * @param value
         */
        public Status(String name, Double value) {
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

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Status.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null) ? "<null>" : this.name));
            sb.append(',');
            sb.append("value");
            sb.append('=');
            sb.append(((this.value == null) ? "<null>" : this.value));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
            result = ((result * 31) + ((this.value == null) ? 0 : this.value.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Status) == false) {
                return false;
            }
            Status rhs = ((Status) other);
            return (((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))) && ((this.value == rhs.value) || ((this.value != null) && this.value.equals(rhs.value))));
        }

    }
}
