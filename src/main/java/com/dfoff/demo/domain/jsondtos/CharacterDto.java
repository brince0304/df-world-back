package com.dfoff.demo.domain.jsondtos;


import com.dfoff.demo.domain.CharacterEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;



@Builder
@AllArgsConstructor
@Getter
@ToString
@Setter
public class CharacterDto {

    private String serverId;

    private String characterId;

    private String characterName;

    private Integer level;

    private String jobId;

    private String jobGrowId;

    private String jobName;

    private String jobGrowName;

    private CharacterAbilityDto characterAbilityDTO;


    public CharacterEntity toEntity() {
        return CharacterEntity.builder()
                .characterId(this.getCharacterId())
                .characterName(this.getCharacterName())
                .serverId(this.getServerId())
                .level(this.getLevel())
                .jobId(this.getJobId())
                .jobGrowId(this.getJobGrowId())
                .jobName(this.getJobName())
                .jobGrowName(this.getJobGrowName())
                .build();
    }

    public static class CharacterJSONDto {


        @SerializedName("rows")
        @Expose
        private List<Row> rows = null;

        /**
         * No args constructor for use in serialization
         */
        public CharacterJSONDto() {
        }

        public List<CharacterDto> toDto() {
            List<CharacterDto> characterDtoList = rows.stream().map(row -> CharacterDto.builder()
                    .serverId(row.getServerId())
                    .characterId(row.getCharacterId())
                    .characterName(row.getCharacterName())
                    .level(row.getLevel())
                    .jobId(row.getJobId())
                    .jobGrowId(row.getJobGrowId())
                    .jobName(row.getJobName())
                    .jobGrowName(row.getJobGrowName())
                    .build()).collect(Collectors.toList());
            return characterDtoList;

        }

        /**
         * @param rows
         */
        public CharacterJSONDto(List<Row> rows) {
            super();
            this.rows = rows;
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
            sb.append(CharacterJSONDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("rows");
            sb.append('=');
            sb.append(((this.rows == null) ? "<null>" : this.rows));
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
            result = ((result * 31) + ((this.rows == null) ? 0 : this.rows.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof CharacterJSONDto) == false) {
                return false;
            }
            CharacterJSONDto rhs = ((CharacterJSONDto) other);
            return ((this.rows == rhs.rows) || ((this.rows != null) && this.rows.equals(rhs.rows)));
        }

        public static class Row {

            @SerializedName("serverId")
            @Expose
            private String serverId;
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

            /**
             * No args constructor for use in serialization
             */
            public Row() {
            }

            /**
             * @param jobName
             * @param jobId
             * @param level
             * @param jobGrowId
             * @param characterName
             * @param jobGrowName
             * @param serverId
             * @param characterId
             */
            public Row(String serverId, String characterId, String characterName, Integer level, String jobId, String jobGrowId, String jobName, String jobGrowName) {
                super();
                this.serverId = serverId;
                this.characterId = characterId;
                this.characterName = characterName;
                this.level = level;
                this.jobId = jobId;
                this.jobGrowId = jobGrowId;
                this.jobName = jobName;
                this.jobGrowName = jobGrowName;
            }

            public String getServerId() {
                return serverId;
            }

            public void setServerId(String serverId) {
                this.serverId = serverId;
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

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Row.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("serverId");
                sb.append('=');
                sb.append(((this.serverId == null) ? "<null>" : this.serverId));
                sb.append(',');
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
                result = ((result * 31) + ((this.jobId == null) ? 0 : this.jobId.hashCode()));
                result = ((result * 31) + ((this.level == null) ? 0 : this.level.hashCode()));
                result = ((result * 31) + ((this.jobGrowId == null) ? 0 : this.jobGrowId.hashCode()));
                result = ((result * 31) + ((this.characterName == null) ? 0 : this.characterName.hashCode()));
                result = ((result * 31) + ((this.jobGrowName == null) ? 0 : this.jobGrowName.hashCode()));
                result = ((result * 31) + ((this.serverId == null) ? 0 : this.serverId.hashCode()));
                result = ((result * 31) + ((this.characterId == null) ? 0 : this.characterId.hashCode()));
                return result;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if ((other instanceof Row) == false) {
                    return false;
                }
                Row rhs = ((Row) other);
                return (((((((((this.jobName == rhs.jobName) || ((this.jobName != null) && this.jobName.equals(rhs.jobName))) && ((this.jobId == rhs.jobId) || ((this.jobId != null) && this.jobId.equals(rhs.jobId)))) && ((this.level == rhs.level) || ((this.level != null) && this.level.equals(rhs.level)))) && ((this.jobGrowId == rhs.jobGrowId) || ((this.jobGrowId != null) && this.jobGrowId.equals(rhs.jobGrowId)))) && ((this.characterName == rhs.characterName) || ((this.characterName != null) && this.characterName.equals(rhs.characterName)))) && ((this.jobGrowName == rhs.jobGrowName) || ((this.jobGrowName != null) && this.jobGrowName.equals(rhs.jobGrowName)))) && ((this.serverId == rhs.serverId) || ((this.serverId != null) && this.serverId.equals(rhs.serverId)))) && ((this.characterId == rhs.characterId) || ((this.characterId != null) && this.characterId.equals(rhs.characterId))));
            }

        }

    }
}



