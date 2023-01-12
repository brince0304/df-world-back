package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor (access = AccessLevel.PROTECTED)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table (indexes={@Index(columnList = "createdAt"),
    @Index(columnList = "boardTitle"),
    @Index(columnList = "boardContent")})
public class Board extends AuditingFields {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String boardTitle;

    private String boardContent;

    @ManyToOne (fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id")
    private UserAccount userAccount;

    private String isDeleted = "N";

    private String boardViewCount = "0";

    private String boardLikeCount = "0";

    @OneToMany (mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SaveFile> boardFiles = new LinkedHashSet<>();

    @OneToMany (mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BoardComment> boardComments = new LinkedHashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board)) return false;
        return id.equals(board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardDto implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
        private final UserAccount.UserAccountDTO userAccount;
        private final String isDeleted;
        private final String boardViewCount;
        private final String boardLikeCount;
        private final Set<SaveFile.SaveFileDTO> boardFiles;
        private final Set<BoardComment.BoardCommentDto> boardComments;

        public static BoardDto from(Board board){
            return BoardDto.builder()
                    .createdAt(board.getCreatedAt())
                    .createdBy(board.getCreatedBy())
                    .modifiedAt(board.getModifiedAt())
                    .modifiedBy(board.getModifiedBy())
                    .id(board.getId())
                    .boardType(board.getBoardType())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .userAccount(UserAccount.UserAccountDTO.from(board.getUserAccount()))
                    .isDeleted(board.getIsDeleted())
                    .boardViewCount(board.getBoardViewCount())
                    .boardLikeCount(board.getBoardLikeCount())
                    .boardFiles(board.getBoardFiles().stream().map(SaveFile.SaveFileDTO::from).collect(Collectors.toSet()))
                    .boardComments(board.getBoardComments().stream().map(BoardComment.BoardCommentDto::from).collect(Collectors.toSet()))
                    .build();
        }

        public static BoardDto from(BoardRequest request){
            return BoardDto.builder()
                    .boardType(request.getBoardType())
                    .boardTitle(request.getBoardTitle())
                    .boardContent(request.getBoardContent())
                    .build();
        }
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardResponse implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
        private final UserAccount.UserAccountResponse userAccount;
        private final String isDeleted;
        private final String boardViewCount;
        private final String boardLikeCount;
        private final Set<SaveFile.SaveFileResponse> boardFiles;

        private final Set<BoardComment.BoardCommentResponse> boardComments;

        public static BoardResponse from(BoardDto dto){
            return BoardResponse.builder()
                    .createdAt(dto.getCreatedAt())
                    .createdBy(dto.getCreatedBy())
                    .modifiedAt(dto.getModifiedAt())
                    .modifiedBy(dto.getModifiedBy())
                    .id(dto.getId())
                    .boardType(dto.getBoardType())
                    .boardTitle(dto.getBoardTitle())
                    .boardContent(dto.getBoardContent())
                    .userAccount(UserAccount.UserAccountResponse.from(dto.getUserAccount()))
                    .isDeleted(dto.getIsDeleted())
                    .boardViewCount(dto.getBoardViewCount())
                    .boardLikeCount(dto.getBoardLikeCount())
                    .boardFiles(dto.getBoardFiles().stream().map(SaveFile.SaveFileResponse::from).collect(Collectors.toSet()))
                    .boardComments(dto.getBoardComments().stream().map(BoardComment.BoardCommentResponse::from).collect(Collectors.toSet()))
                    .build();
        }
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    public static class BoardRequest implements Serializable {
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
    }
}
