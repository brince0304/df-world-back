package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForDFCharacter.DFJob;
import com.dfoff.demo.Domain.ForDFCharacter.DFServer;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor (access = lombok.AccessLevel.PROTECTED)
public class DFCharacter {
    @Id
    @GeneratedValue (strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String characterName;

    @Setter
    private String characterId;

    @Setter
    @OneToOne
    private DFServer server;

    @Setter
    @JoinColumn (name = "jobId")
    @OneToOne
    @ToString.Exclude
    private DFJob dfJob;



}
