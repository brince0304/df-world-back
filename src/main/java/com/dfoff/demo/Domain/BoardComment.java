package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dfoff.demo.Domain.Board.BoardResponse.Chrono.timesAgo;


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
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Board board;

    @ManyToOne (fetch = FetchType.LAZY)
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
    private String isParent = "Y";

    @OneToMany (mappedBy = "parentComment",fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private final Set<BoardComment> childrenComments = new LinkedHashSet<>();

    @ManyToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn (name = "parent_id")
    @Setter
    private BoardComment parentComment;





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
                    .userAccount(UserAccount.UserAccountDto.from(boardComment.getUserAccount()))
                    .commentLikeCount(boardComment.getCommentLikeCount())
                    .isDeleted(boardComment.getIsDeleted())
                    .isParent(boardComment.getIsParent())
                    .childrenComments(boardComment.getChildrenComments().stream().map(BoardComment.BoardCommentDto::from).collect(Collectors.toSet()))
                    .build();
        }

        public static BoardCommentDto from(BoardCommentRequest boardCommentRequest, UserAccount.UserAccountDto userAccountDto, Board.BoardDto boardDto) {
            return BoardCommentDto.builder()
                    .commentContent(boardCommentRequest.getCommentContent())
                    .userAccount(userAccountDto)
                    .board(boardDto)
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
                    .id(id)
                    .board(board.toEntity())
                    .commentContent(this.commentContent)
                    .userAccount(userAccount.toEntity())
                    .build();
        }


    }

    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Data
    @Builder
    public static class BoardCommentResponse implements Serializable {
        private final String createdAt;
        private final String createdBy;
        private final String modifiedAt;
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
                    .createdAt(timesAgo(dto.getCreatedAt()))
                    .createdBy(dto.getCreatedBy())
                    .modifiedAt(timesAgo(dto.getModifiedAt()))
                    .modifiedBy(dto.getModifiedBy())
                    .id(dto.getId())
                    .commentContent(dto.getCommentContent())
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
        private final Long commentId;
        @Size (min = 1, max = 1000, message = "댓글은 1자 이상 1000자 이하로 작성해주세요.")
        private final String commentContent;
    }

    @Data
    public static class BoardCommentChildrenRequest implements Serializable {
        private final Long boardId;

        private final Long parentId;
        private final String commentContent;
    }
}
