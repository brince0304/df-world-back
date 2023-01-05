package com.dfoff.demo.Domain.ForDFCharacter;

import lombok.*;

import javax.persistence.*;

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


}
