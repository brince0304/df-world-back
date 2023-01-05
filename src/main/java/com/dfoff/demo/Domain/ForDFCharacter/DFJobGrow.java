package com.dfoff.demo.Domain.ForDFCharacter;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@EqualsAndHashCode(of = "jobGrowName")
public class DFJobGrow {
    @Setter
    @Column (nullable = false)
    private String jobGrowId;
    @Id
    @Column (nullable = false)
    private String jobGrowName;

    @ManyToOne
    @Setter
    @JoinColumn (name = "jobName")
    private DFJob jobName;

    @AllArgsConstructor
    @Getter
    @Builder
    public static class DFJobGrowDTO{
        private final String jobGrowId;
        private final String jobGrowName;
        private final DFJob.DFJobDTO jobName;

        public static DFJobGrowDTO from(DFJobGrow dfJobGrow){
            return DFJobGrowDTO.builder()
                    .jobGrowId(dfJobGrow.getJobGrowId())
                    .jobGrowName(dfJobGrow.getJobGrowName())
                    .jobName(DFJob.DFJobDTO.from(dfJobGrow.getJobName()))
                    .build();
        }

        public static DFJobGrow toEntity(DFJobGrowDTO dfJobGrowDTO){
            return DFJobGrow.builder()
                    .jobGrowId(dfJobGrowDTO.getJobGrowId())
                    .jobGrowName(dfJobGrowDTO.getJobGrowName())
                    .jobName(DFJob.DFJobDTO.toEntity(dfJobGrowDTO.getJobName()))
                    .build();
        }
    }
}
