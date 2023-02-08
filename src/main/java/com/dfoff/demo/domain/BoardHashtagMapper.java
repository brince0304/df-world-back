package com.dfoff.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardHashtagMapper extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    @Setter
    private Board board;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    @Setter
    private Hashtag hashtag;

    public static BoardHashtagMapper of(Board board, Hashtag hashtag) {
        return new BoardHashtagMapper(null, board, hashtag);
    }
}
