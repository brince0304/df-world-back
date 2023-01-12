package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor (access = AccessLevel.PROTECTED)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table (indexes=@Index(name = "idx_createdAt" , columnList = "createdAt"))
public class BoardComment extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentContent;
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Board board;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserAccount userAccount;

    private String commentLikeCount = "0";

    private String isDeleted = "N";


    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Data
    @Builder
    public static class BoardCommentDto implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final String commentContent;
        private final Board.BoardDto board;
        private final UserAccount.UserAccountDto userAccount;
        private final String commentLikeCount;
        private final String isDeleted;

        public static BoardCommentDto from(BoardComment boardComment) {
            return BoardCommentDto.builder()
                    .createdAt(boardComment.getCreatedAt())
                    .createdBy(boardComment.getCreatedBy())
                    .modifiedAt(boardComment.getModifiedAt())
                    .modifiedBy(boardComment.getModifiedBy())
                    .id(boardComment.getId())
                    .commentContent(boardComment.getCommentContent())
                    .board(Board.BoardDto.from(boardComment.getBoard()))
                    .userAccount(UserAccount.UserAccountDto.from(boardComment.getUserAccount()))
                    .commentLikeCount(boardComment.getCommentLikeCount())
                    .isDeleted(boardComment.getIsDeleted())
                    .build();
        }

        public static BoardCommentDto from(BoardCommentRequest boardCommentRequest) {
            return BoardCommentDto.builder()
                    .commentContent(boardCommentRequest.getCommentContent())
                    .build();
        }
    }

    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Data
    @Builder
    public static class BoardCommentResponse implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final String commentContent;
        private final Board.BoardResponse board;
        private final UserAccount.UserAccountResponse userAccount;
        private final String commentLikeCount;
        private final String isDeleted;

        public static BoardCommentResponse from (BoardCommentDto dto){
            return BoardCommentResponse.builder()
                    .createdAt(dto.getCreatedAt())
                    .createdBy(dto.getCreatedBy())
                    .modifiedAt(dto.getModifiedAt())
                    .modifiedBy(dto.getModifiedBy())
                    .id(dto.getId())
                    .commentContent(dto.getCommentContent())
                    .board(Board.BoardResponse.from(dto.getBoard()))
                    .userAccount(UserAccount.UserAccountResponse.from(dto.getUserAccount()))
                    .commentLikeCount(dto.getCommentLikeCount())
                    .isDeleted(dto.getIsDeleted())
                    .build();

        }
    }

    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Data
    public static class BoardCommentRequest implements Serializable {
        private final Long boardId;
        private final String commentContent;
    }
}
