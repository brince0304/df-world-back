package com.dfoff.demo.Domain.ForDFCharacter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor (access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode(of = "jobName")
public class DFJob {

    @Setter
    @Column (nullable = false)
    private String jobId;
    @Id
    @Column (nullable = false)
    private String jobName;


    @Getter
    @AllArgsConstructor
    @Builder
    public static class DFJobDTO{
        private final String jobId;
        private final String jobName;

        public static DFJobDTO from(DFJob dfJob){
            return DFJobDTO.builder()
                    .jobId(dfJob.getJobId())
                    .jobName(dfJob.getJobName())
                    .build();
        }

        public static DFJob toEntity(DFJobDTO dfJobDTO){
            return DFJob.builder()
                    .jobId(dfJobDTO.getJobId())
                    .jobName(dfJobDTO.getJobName())
                    .build();
        }
    }
    @javax.annotation.Generated("jsonschema2pojo")
    public static class DFJobJSONDTO {

        @SerializedName("rows")
        @Expose
        private List<Row> rows = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public DFJobJSONDTO() {
        }

        /**
         *
         * @param rows
         */
        public DFJobJSONDTO(List<Row> rows) {
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
            sb.append(DFJobJSONDTO.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
            if ((other instanceof DFJobJSONDTO) == false) {
                return false;
            }
            DFJobJSONDTO rhs = ((DFJobJSONDTO) other);
            return ((this.rows == rhs.rows)||((this.rows!= null)&&this.rows.equals(rhs.rows)));
        }

        @javax.annotation.Generated("jsonschema2pojo")
        public static class Next {

            @SerializedName("jobGrowId")
            @Expose
            private String jobGrowId;
            @SerializedName("jobGrowName")
            @Expose
            private String jobGrowName;
            @SerializedName("next")
            @Expose
            private Next__1 next;



            /**
             * No args constructor for use in serialization
             *
             */
            public Next() {
            }

            /**
             *
             * @param next
             * @param jobGrowId
             * @param jobGrowName
             */
            public Next(String jobGrowId, String jobGrowName, Next__1 next) {
                super();
                this.jobGrowId = jobGrowId;
                this.jobGrowName = jobGrowName;
                this.next = next;
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

            public Next__1 getNext() {
                return next;
            }

            public void setNext(Next__1 next) {
                this.next = next;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Next.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("jobGrowId");
                sb.append('=');
                sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
                sb.append(',');
                sb.append("jobGrowName");
                sb.append('=');
                sb.append(((this.jobGrowName == null)?"<null>":this.jobGrowName));
                sb.append(',');
                sb.append("next");
                sb.append('=');
                sb.append(((this.next == null)?"<null>":this.next));
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
                result = ((result* 31)+((this.next == null)? 0 :this.next.hashCode()));
                result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
                result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
                return result;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if ((other instanceof Next) == false) {
                    return false;
                }
                Next rhs = ((Next) other);
                return ((((this.next == rhs.next)||((this.next!= null)&&this.next.equals(rhs.next)))&&((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName))))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))));
            }

        }

        @javax.annotation.Generated("jsonschema2pojo")
        public static class Row__1 {

            @SerializedName("jobGrowId")
            @Expose
            private String jobGrowId;
            @SerializedName("jobGrowName")
            @Expose
            private String jobGrowName;
            @SerializedName("next")
            @Expose
            private Next next;

            /**
             * No args constructor for use in serialization
             *
             */
            public Row__1() {
            }

            /**
             *
             * @param next
             * @param jobGrowId
             * @param jobGrowName
             */
            public Row__1(String jobGrowId, String jobGrowName, Next next) {
                super();
                this.jobGrowId = jobGrowId;
                this.jobGrowName = jobGrowName;
                this.next = next;
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

            public Next getNext() {
                return next;
            }

            public void setNext(Next next) {
                this.next = next;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Row__1.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("jobGrowId");
                sb.append('=');
                sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
                sb.append(',');
                sb.append("jobGrowName");
                sb.append('=');
                sb.append(((this.jobGrowName == null)?"<null>":this.jobGrowName));
                sb.append(',');
                sb.append("next");
                sb.append('=');
                sb.append(((this.next == null)?"<null>":this.next));
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
                result = ((result* 31)+((this.next == null)? 0 :this.next.hashCode()));
                result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
                result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
                return result;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if ((other instanceof Row__1) == false) {
                    return false;
                }
                Row__1 rhs = ((Row__1) other);
                return ((((this.next == rhs.next)||((this.next!= null)&&this.next.equals(rhs.next)))&&((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName))))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))));
            }

        }

        @javax.annotation.Generated("jsonschema2pojo")
        public static class Row {

            @SerializedName("jobId")
            @Expose
            private String jobId;
            @SerializedName("jobName")
            @Expose
            private String jobName;
            @SerializedName("rows")
            @Expose
            private List<Row__1> rows = null;

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
             * @param rows
             */
            public Row(String jobId, String jobName, List<Row__1> rows) {
                super();
                this.jobId = jobId;
                this.jobName = jobName;
                this.rows = rows;
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

            public List<Row__1> getRows() {
                return rows;
            }

            public void setRows(List<Row__1> rows) {
                this.rows = rows;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Row.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("jobId");
                sb.append('=');
                sb.append(((this.jobId == null)?"<null>":this.jobId));
                sb.append(',');
                sb.append("jobName");
                sb.append('=');
                sb.append(((this.jobName == null)?"<null>":this.jobName));
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

            @Override
            public int hashCode() {
                int result = 1;
                result = ((result* 31)+((this.jobName == null)? 0 :this.jobName.hashCode()));
                result = ((result* 31)+((this.jobId == null)? 0 :this.jobId.hashCode()));
                result = ((result* 31)+((this.rows == null)? 0 :this.rows.hashCode()));
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
                return ((((this.jobName == rhs.jobName)||((this.jobName!= null)&&this.jobName.equals(rhs.jobName)))&&((this.jobId == rhs.jobId)||((this.jobId!= null)&&this.jobId.equals(rhs.jobId))))&&((this.rows == rhs.rows)||((this.rows!= null)&&this.rows.equals(rhs.rows))));
            }

        }

        @javax.annotation.Generated("jsonschema2pojo")
        public static class Next__2 {

            @SerializedName("jobGrowId")
            @Expose
            private String jobGrowId;
            @SerializedName("jobGrowName")
            @Expose
            private String jobGrowName;

            /**
             * No args constructor for use in serialization
             *
             */
            public Next__2() {
            }

            /**
             *
             * @param jobGrowId
             * @param jobGrowName
             */
            public Next__2(String jobGrowId, String jobGrowName) {
                super();
                this.jobGrowId = jobGrowId;
                this.jobGrowName = jobGrowName;
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

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Next__2.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("jobGrowId");
                sb.append('=');
                sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
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
                result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
                result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
                return result;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if ((other instanceof Next__2) == false) {
                    return false;
                }
                Next__2 rhs = ((Next__2) other);
                return (((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName)))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))));
            }

        }

        @Generated("jsonschema2pojo")
        public static class Next__1 {

            @SerializedName("jobGrowId")
            @Expose
            private String jobGrowId;
            @SerializedName("jobGrowName")
            @Expose
            private String jobGrowName;
            @SerializedName("next")
            @Expose
            private Next__2 next;

            /**
             * No args constructor for use in serialization
             *
             */
            public Next__1() {
            }

            /**
             *
             * @param next
             * @param jobGrowId
             * @param jobGrowName
             */
            public Next__1(String jobGrowId, String jobGrowName, Next__2 next) {
                super();
                this.jobGrowId = jobGrowId;
                this.jobGrowName = jobGrowName;
                this.next = next;
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

            public Next__2 getNext() {
                return next;
            }

            public void setNext(Next__2 next) {
                this.next = next;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Next__1.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("jobGrowId");
                sb.append('=');
                sb.append(((this.jobGrowId == null)?"<null>":this.jobGrowId));
                sb.append(',');
                sb.append("jobGrowName");
                sb.append('=');
                sb.append(((this.jobGrowName == null)?"<null>":this.jobGrowName));
                sb.append(',');
                sb.append("next");
                sb.append('=');
                sb.append(((this.next == null)?"<null>":this.next));
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
                result = ((result* 31)+((this.next == null)? 0 :this.next.hashCode()));
                result = ((result* 31)+((this.jobGrowName == null)? 0 :this.jobGrowName.hashCode()));
                result = ((result* 31)+((this.jobGrowId == null)? 0 :this.jobGrowId.hashCode()));
                return result;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if ((other instanceof Next__1) == false) {
                    return false;
                }
                Next__1 rhs = ((Next__1) other);
                return ((((this.next == rhs.next)||((this.next!= null)&&this.next.equals(rhs.next)))&&((this.jobGrowName == rhs.jobGrowName)||((this.jobGrowName!= null)&&this.jobGrowName.equals(rhs.jobGrowName))))&&((this.jobGrowId == rhs.jobGrowId)||((this.jobGrowId!= null)&&this.jobGrowId.equals(rhs.jobGrowId))));
            }

        }
    }
}
