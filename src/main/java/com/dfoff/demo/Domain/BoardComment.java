package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import com.dfoff.demo.Util.FileUtil;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dfoff.demo.Domain.Board.Chrono.timesAgo;
import static com.dfoff.demo.Domain.Board.Chrono.timesAgo;


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
        private final String isDeleted;
        private final String isParent;

        private final String userProfileImgUrl;
        private final Set<BoardCommentResponse> childrenComments;

        private final BoardType boardType;

        public String getBoardType(BoardType type){
            if(type==null){
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

        public static BoardCommentResponse from (BoardComment boardComment) {
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
                    .isDeleted(boardComment.getIsDeleted())
                    .isParent(boardComment.getIsParent())
                    .childrenComments(boardComment.getChildrenComments().stream().map(BoardCommentResponse::from).collect(Collectors.toSet()))
                    .userProfileImgUrl(FileUtil.getProfileIconPath(boardComment.getUserAccount().getProfileIcon().getFileName()))
                    .boardType(boardComment.getBoard().getBoardType())
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
        private final String isDeleted;
        private final String isParent;


        private final String childrenCommentsSize;

        private final BoardType boardType;

        public String getBoardType(BoardType type){
            if(type==null){
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

        public static BoardCommentMyPageResponse from (BoardComment boardComment) {
            return BoardCommentMyPageResponse.builder()
                    .createdAt(timesAgo(boardComment.getCreatedAt()))
                    .createdBy(boardComment.getCreatedBy())
                    .modifiedAt(timesAgo(boardComment.getModifiedAt()))
                    .modifiedBy(boardComment.getModifiedBy())
                    .id(boardComment.getId())
                    .commentContent(boardComment.getCommentContent())
                    .boardId(boardComment.getBoard().getId().toString())
                    .commentLikeCount(boardComment.getCommentLikeCount())
                    .isDeleted(boardComment.getIsDeleted())
                    .isParent(boardComment.getIsParent())
                    .childrenCommentsSize(boardComment.getChildrenComments().size()+"")
                    .boardType(boardComment.getBoard().getBoardType())
                    .build();

        }
    }

    /**
     * A DTO for the {@link BoardComment} entity
     */
    @Data
    @Builder
    @AllArgsConstructor
    public static class BoardCommentRequest implements Serializable {
        private final Long boardId;
        private final Long commentId;
        @Size (min = 1, max = 1000, message = "댓글은 1자 이상 1000자 이하로 작성해주세요.")
        private final String commentContent;

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
}
