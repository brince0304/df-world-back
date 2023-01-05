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
}
