package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Table (indexes=@Index(name = "idx_createdAt" , columnList = "createdAt"))
@Entity
@Getter
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
public class BoardComment extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Length (min = 1, max = 1000)
    private String commentContent;
    @Setter
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Board board;

    @Setter
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private UserAccount userAccount;
    @Setter
    @Builder.Default
    private Integer commentLikeCount = 0;
    @Setter
    @Builder.Default
    private String isDeleted = "N";
    @Setter
    @Builder.Default
    private String isParent = "N";

    @OneToMany (mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private final Set<BoardComment> childrenComments = new LinkedHashSet<>();




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
        private final Integer commentLikeCount;
        private final String isDeleted;

        private final String isParent;

        private final Set<BoardComment.BoardCommentDto> childrenComments;





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
                    .isParent(boardComment.getIsParent())
                    .childrenComments(boardComment.getChildrenComments().stream().map(BoardComment.BoardCommentDto::from).collect(Collectors.toSet()))
                    .build();
        }

        public static BoardCommentDto from(BoardCommentRequest boardCommentRequest) {
            return BoardCommentDto.builder()
                    .commentContent(boardCommentRequest.getCommentContent())
                    .build();
        }

        public static BoardCommentDto from(BoardCommentChildrenRequest boardCommentChildrenRequest) {
            return BoardCommentDto.builder()
                    .id(boardCommentChildrenRequest.getParentId())
                    .commentContent(boardCommentChildrenRequest.getCommentContent())
                    .build();
        }

        public BoardComment toEntity() {
            return BoardComment.builder()
                    .board(this.board.toEntity())
                    .commentContent(this.commentContent)
                    .userAccount(this.userAccount.toEntity())
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
        private final Integer commentLikeCount;
        private final String isDeleted;

        private final String isParent;

        private final Set<BoardComment.BoardCommentResponse> childrenComments;

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
                    .isParent(dto.getIsParent())
                    .childrenComments(dto.getChildrenComments().stream().map(BoardComment.BoardCommentResponse::from).collect(Collectors.toSet()))
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

    @Data
    public static class BoardCommentChildrenRequest implements Serializable {
        private final Long boardId;

        private final Long parentId;
        private final String commentContent;
    }
}
