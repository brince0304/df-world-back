package com.dfoff.demo.Domain.ForDFCharacter;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Entity
@Getter
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
@EqualsAndHashCode(of = "serverId")
public class DFServer {
    @Id
    String serverId;
    @Setter
    @Column (nullable = false)
    String serverName;

}
