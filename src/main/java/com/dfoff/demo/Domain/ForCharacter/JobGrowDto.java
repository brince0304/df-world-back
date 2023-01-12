package com.dfoff.demo.Domain.ForCharacter;

import lombok.*;


@AllArgsConstructor
@Getter
@Builder
@ToString
public record JobGrowDto(String jobGrowId, String jobGrowName) {
}

