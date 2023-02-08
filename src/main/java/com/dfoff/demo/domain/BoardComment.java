package com.dfoff.demo.domain;

import com.dfoff.demo.domain.enums.BoardType;
import com.dfoff.demo.utils.FileUtil;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dfoff.demo.utils.CharactersUtil.timesAgo;


@Table(indexes = @Index(columnList = "createdAt"))
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SQLDelete(sql = "UPDATE board_comment SET deleted = true, deleted_at = now() WHERE id = ?")
public class BoardComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Length(min = 1, max = 1000)
    private String commentContent;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private UserAccount userAccount;
    @Setter
    @Builder.Default
    private Integer commentLikeCount = 0;
    @Setter
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;
    @Setter
    @Builder.Default
    private Boolean isParent = Boolean.TRUE;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final Set<BoardComment> childrenComments = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "parent_id")
    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BoardComment parentComment;

    private LocalDateTime deletedAt;


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
        private final String boardId;
        private final String userId;

        private final String userNickname;
        private final Integer commentLikeCount;
        private final Boolean isParent;

        private final String userProfileImgUrl;
        private final Set<BoardCommentResponse> childrenComments;

        private final String profileCharacterIcon;

        private final String profileCharacterIconClassName;

        private final BoardType boardType;

        public String getBoardType(BoardType type) {
            if (type == null) {
                return null;
            }
            return switch (type) {
                case NOTICE -> "공지";
                case FREE -> "자유";
                case QUESTION -> "Q&A";
                case RECRUITMENT -> "구인";
                case MARKET -> "거래";
                case REPORT -> "사건/사고";
            };
        }

        public static BoardCommentResponse from(BoardComment boardComment) {
            return BoardCommentResponse.builder()
                    .createdAt(timesAgo(boardComment.getCreatedAt()))
                    .createdBy(boardComment.getCreatedBy())
                    .modifiedAt(timesAgo(boardComment.getModifiedAt()))
                    .modifiedBy(boardComment.getModifiedBy())
                    .id(boardComment.getId())
                    .commentContent(boardComment.getCommentContent())
                    .boardId(boardComment.getBoard().getId().toString())
                    .userId(boardComment.getUserAccount().getUserId())
                    .userNickname(boardComment.getUserAccount().getNickname())
                    .commentLikeCount(boardComment.getCommentLikeCount())
                    .isParent(boardComment.getIsParent())
                    .childrenComments(boardComment.getChildrenComments().stream().map(BoardCommentResponse::from).collect(Collectors.toSet()))
                    .userProfileImgUrl(FileUtil.getProfileIconPath(boardComment.getUserAccount().getProfileIcon().getFileName()))
                    .boardType(boardComment.getBoard().getBoardType())
                    .profileCharacterIconClassName(boardComment.getUserAccount().getProfileCharacterIconClassName()==null?"":boardComment.getUserAccount().getProfileCharacterIconClassName())
                    .profileCharacterIcon(boardComment.getUserAccount().getProfileCharacterIcon()==null?"":boardComment.getUserAccount().getProfileCharacterIcon())
                    .build();

        }
    }

    @Data
    @Builder
    public static class BoardCommentMyPageResponse implements Serializable {
        private final String createdAt;
        private final String createdBy;
        private final String modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final String commentContent;
        private final String boardId;


        private final Integer commentLikeCount;
        private final Boolean deleted;
        private final Boolean isParent;


        private final String childrenCommentsSize;

        private final BoardType boardType;

        public String getBoardType(BoardType type) {
            if (type == null) {
                return null;
            }
            return switch (type) {
                case NOTICE -> "공지";
                case FREE -> "자유";
                case QUESTION -> "Q&A";
                case RECRUITMENT -> "구인";
                case MARKET -> "거래";
                case REPORT -> "사건/사고";
            };
        }

        public static BoardCommentMyPageResponse from(BoardComment boardComment) {
            return BoardCommentMyPageResponse.builder()
                    .createdAt(timesAgo(boardComment.getCreatedAt()))
                    .createdBy(boardComment.getCreatedBy())
                    .modifiedAt(timesAgo(boardComment.getModifiedAt()))
                    .modifiedBy(boardComment.getModifiedBy())
                    .id(boardComment.getId())
                    .commentContent(boardComment.getCommentContent())
                    .boardId(boardComment.getBoard().getId().toString())
                    .commentLikeCount(boardComment.getCommentLikeCount())
                    .deleted(boardComment.getDeleted())
                    .isParent(boardComment.getIsParent())
                    .childrenCommentsSize(boardComment.getChildrenComments().size() + "")
                    .boardType(boardComment.getBoard().getBoardType())
                    .build();

        }
    }

    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Builder
    public record BoardCommentRequest(
            Long boardId,
            Long commentId,
            @Size(min = 1, max = 1000, message = "댓글은 1자 이상 1000자 이하로 작성해주세요.")
            String commentContent
    ) implements Serializable {


        public BoardComment toEntity(UserAccount.UserAccountDto dto, Board.BoardDto boardDto) {
            return BoardComment.builder()
                    .commentContent(this.commentContent)
                    .userAccount(dto.toEntity())
                    .board(boardDto.toEntity())
                    .build();
        }
    }

    @Data
    public static class BoardCommentChildrenRequest implements Serializable {
        private final Long boardId;

        private final Long parentId;
        private final String commentContent;
    }

    @Data
    @Builder
    public static class BoardCommentDto implements Serializable {
        private final Long id;
        private final String commentContent;
        private final Long boardId;
        private final String userId;
        private final String userNickname;
        private final Boolean isParent;
        private final BoardType boardType;

        private final String commentLikeCount;

        private final Board.BoardDto boardDto;

        private final UserAccount.UserAccountDto userAccountDto;

        public static BoardCommentDto from(BoardComment boardComment) {
            return BoardCommentDto.builder()
                    .id(boardComment.getId())
                    .commentContent(boardComment.getCommentContent())
                    .boardId(boardComment.getBoard().getId())
                    .userId(boardComment.getUserAccount().getUserId())
                    .userNickname(boardComment.getUserAccount().getNickname())
                    .isParent(boardComment.getIsParent())
                    .boardType(boardComment.getBoard().getBoardType())
                    .boardDto(Board.BoardDto.from(boardComment.getBoard()))
                    .userAccountDto(UserAccount.UserAccountDto.from(boardComment.getUserAccount()))
                    .commentLikeCount(boardComment.getCommentLikeCount() + "")
                    .build();

        }

        public BoardComment toEntity() {
            return BoardComment.builder()
                    .id(this.id)
                    .commentContent(this.commentContent)
                    .board(this.boardDto != null ? this.boardDto.toEntity() : null)
                    .userAccount(this.userAccountDto != null ? this.userAccountDto.toEntity() : null)
                    .build();
        }
    }
}
