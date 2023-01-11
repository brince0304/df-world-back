package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import com.dfoff.demo.UserAccountCharacterMapper;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.micrometer.core.lang.Nullable;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterEntity extends AuditingFields {
    @Id
    private String characterId;

    private String serverId;

    private String characterName;

    private Integer level;

    private String jobId;

    private String jobGrowId;

    private String jobName;

    private String jobGrowName;

    private String adventureFame;

    private String adventureName;

    @OneToMany (fetch = FetchType.LAZY)
    @ToString.Exclude
    @Nullable
    private Set<UserAccountCharacterMapper> userAccount = new LinkedHashSet<>();





}
