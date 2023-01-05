package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForDFCharacter.DFJob;
import com.dfoff.demo.Domain.ForDFCharacter.DFJobGrow;
import com.dfoff.demo.Domain.ForDFCharacter.DFServer;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor (access = lombok.AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "characterId", unique = true)})
@Builder
public class DFCharacter extends AuditingFields {
    @Id
    @Setter
    private String characterId;

    @Setter
    private String characterName;



    @Setter
    private Integer characterLevel;

    @Setter
    @OneToOne
    private DFServer dfServer;

    @Setter
    @JoinColumn (name = "job_name")
    @OneToOne
    @ToString.Exclude
    private DFJob dfJob;

    @Setter
    @JoinColumn (name = "job_grow_name",nullable = true)
    @OneToOne
    @ToString.Exclude
    private DFJobGrow dfJobGrow;



    @Builder
    @AllArgsConstructor
    @Getter
    public static class DFCharacterDTO{
        private final String characterName;
        private final String characterId;
        private final Integer characterLevel;
        private final DFServer.DFServerDTO dfServer;
        private final DFJob.DFJobDTO dfJob;
        private final DFJobGrow.DFJobGrowDTO dfJobGrow;


        public static DFCharacterDTO from (DFCharacter entity){
            return DFCharacterDTO.builder()
                    .characterName(entity.getCharacterName())
                    .characterId(entity.getCharacterId())
                    .characterLevel(entity.getCharacterLevel())
                    .dfServer(DFServer.DFServerDTO.from(entity.getDfServer()))
                    .dfJob(DFJob.DFJobDTO.from(entity.getDfJob()))
                    .dfJobGrow(DFJobGrow.DFJobGrowDTO.from(entity.getDfJobGrow()))
                    .build();
        }

        public static Set<DFCharacterDTO> fromMapper (Set<UserAccountDFCharacterMapper.UserAccountDFCharacterMapperDTO> dto){
            Set<DFCharacterDTO> set = new HashSet<>();
            dto.stream().map(UserAccountDFCharacterMapper.UserAccountDFCharacterMapperDTO::getDfCharacter).forEach(set::add);
            return set;
        }

        public static Set<DFCharacterDTO> from (Set<DFCharacter> entities){
            return entities.stream().map(DFCharacterDTO::from).collect(Collectors.toSet());
    }
        public static DFCharacter toEntity (DFCharacterDTO dto){
            return DFCharacter.builder()
                    .characterName(dto.getCharacterName())
                    .characterId(dto.getCharacterId())
                    .characterLevel(dto.getCharacterLevel())
                    .dfServer(DFServer.DFServerDTO.toEntity(dto.getDfServer()))
                    .dfJob(DFJob.DFJobDTO.toEntity(dto.getDfJob()))
                    .dfJobGrow(DFJobGrow.DFJobGrowDTO.toEntity(dto.getDfJobGrow()))
                    .build();
        }

        public static Set<DFCharacter> toEntity(Set<DFCharacterDTO> characters) {
            return characters.stream().map(DFCharacterDTO::toEntity).collect(Collectors.toSet());
        }
    }



    @Generated("jsonschema2pojo")
    public static class DFCharacterJSONDTO {



        @SerializedName("rows")
        @Expose
        private List<Row> rows = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public DFCharacterJSONDTO() {
        }

        /**
         *
         * @param rows
         */
        public DFCharacterJSONDTO(List<Row> rows) {
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
            sb.append(DFCharacterJSONDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.rows == null)? 0 :this.rows.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof DFCharacterJSONDTO) == false) {
                return false;
            }
            DFCharacterJSONDTO rhs = ((DFCharacterJSONDTO) other);
            return ((this.rows == rhs.rows)||((this.rows!= null)&&this.rows.equals(rhs.rows)));
        }
        @Generated("jsonschema2pojo")
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
             *
             */
            public Row() {
            }

            /**
             *
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
                sb.append(((this.serverId == null)?"<null>":this.serverId));
                sb.append(',');
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
                result = ((result* 31)+((this.jobName == null)? 0 :this.jobName.hashCode()));
                result = ((result* 31)+((this.jobId == null)? 0 :this.jobId.hashCode()));
                result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
                result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
                result = ((result* 31)+((this.characterName == null)? 0 :this.characterName.hashCode()));
                result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
                result = ((result* 31)+((this.serverId == null)? 0 :this.serverId.hashCode()));
                result = ((result* 31)+((this.characterId == null)? 0 :this.characterId.hashCode()));
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
                return (((((((((this.jobName == rhs.jobName)||((this.jobName!= null)&&this.jobName.equals(rhs.jobName)))&&((this.jobId == rhs.jobId)||((this.jobId!= null)&&this.jobId.equals(rhs.jobId))))&&((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level))))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))))&&((this.characterName == rhs.characterName)||((this.characterName!= null)&&this.characterName.equals(rhs.characterName))))&&((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName))))&&((this.serverId == rhs.serverId)||((this.serverId!= null)&&this.serverId.equals(rhs.serverId))))&&((this.characterId == rhs.characterId)||((this.characterId!= null)&&this.characterId.equals(rhs.characterId))));
            }

        }

    }
}
