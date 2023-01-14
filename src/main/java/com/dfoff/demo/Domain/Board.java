package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.dfoff.demo.Domain.Board.BoardResponse.Chrono.timesAgo;
import static com.dfoff.demo.Util.BoardUtil.converter;

@Entity
@Getter
@Table (indexes={@Index(columnList = "createdAt")})
@Builder
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Board extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    @Setter
    @NotNull
    @Length (min = 1, max = 100)
    private String boardTitle;
    @Setter
    @NotNull
    @Length (min = 1, max = 10000)
    private String boardContent;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    @Setter
    @NotNull
    private UserAccount userAccount;

    @Setter
    @Column (nullable = false)
    @Builder.Default
    private String isDeleted = "N";
    @Setter
    @Builder.Default
    private Integer boardViewCount = 0;
    @Setter
    @Builder.Default
    private Integer boardLikeCount = 0;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    @JoinColumn (name = "board_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private final Set<SaveFile> boardFiles = new LinkedHashSet<>();

    @OneToMany (mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private final Set<BoardComment> boardComments = new LinkedHashSet<>();


    @OneToMany (mappedBy = "board")
    @Builder.Default
    @ToString.Exclude
    private final Set<BoardHashtagMapper> hashtags = new LinkedHashSet<>();






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
        private final UserAccount.UserAccountDto userAccount;
        private final String isDeleted;
        private final Integer boardViewCount;
        private final Integer boardLikeCount;
        private final Set<SaveFile.SaveFileDTO> boardFiles;
        private final Set<BoardComment.BoardCommentDto> boardComments;

        private final Set<Hashtag.HashtagDto> hashtags;

        public static BoardDto from(Board board) {
            return BoardDto.builder()
                    .createdAt(board.getCreatedAt())
                    .createdBy(board.getCreatedBy())
                    .modifiedAt(board.getModifiedAt())
                    .modifiedBy(board.getModifiedBy())
                    .id(board.getId())
                    .boardType(board.getBoardType())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .userAccount(UserAccount.UserAccountDto.from(board.getUserAccount()))
                    .isDeleted(board.getIsDeleted())
                    .boardViewCount(board.getBoardViewCount())
                    .boardLikeCount(board.getBoardLikeCount())
                    .boardFiles(board.getBoardFiles().stream().map(SaveFile.SaveFileDTO::from).collect(Collectors.toSet()))
                    .boardComments(board.getBoardComments().stream().map(BoardComment.BoardCommentDto::from).collect(Collectors.toSet()))
                    .hashtags(board.getHashtags().stream().map(BoardHashtagMapper::getHashtag).map(Hashtag.HashtagDto::from).collect(Collectors.toSet()))
                    .build();
        }
            public Board toEntity(){
                return  Board.builder()
                        .boardType(boardType)
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .userAccount(userAccount.toEntity()).build();
            }

        public static BoardDto from(BoardRequest request, UserAccount.UserAccountDto accountDto){
            Set<Hashtag.HashtagDto> hashtags = new HashSet<>();
            if(!request.getHashtag().equals("")) {
                StringTokenizer st = new StringTokenizer(request.getHashtag().replaceAll(" ",""), "#");
                while (st.hasMoreTokens()) {
                    hashtags.add(Hashtag.HashtagDto.builder().name(st.nextToken()).build());
                }
            }

            return BoardDto.builder()
                    .boardType(request.getBoardType())
                    .boardTitle(request.getBoardTitle())
                    .boardContent(request.getBoardContent())
                    .userAccount(accountDto)
                    .hashtags(hashtags)
                    .build();
        }
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardResponse implements Serializable {
        private final String createdAt;
        private final String createdBy;
        private final String modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
        private final UserAccount.UserAccountResponse userAccount;
        private final String isDeleted;
        private final Integer boardViewCount;
        private final Integer boardLikeCount;
        private final Set<SaveFile.SaveFileResponse> boardFiles;

        private final Set<BoardComment.BoardCommentResponse> boardComments;

        private final Set<Hashtag.HashtagResponse> hashtags;


        public String convert(String date){
            return converter.convert(date);
        }
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
            };
        }
        public static BoardResponse from(BoardDto dto){
            return BoardResponse.builder()
                    .createdAt(timesAgo(dto.createdAt))
                    .createdBy(dto.getCreatedBy())
                    .modifiedAt(timesAgo(dto.modifiedAt))
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
                    .hashtags(dto.getHashtags().stream().map(Hashtag.HashtagResponse::from).collect(Collectors.toSet()))
                    .build();
        }

        public static class Chrono {

            public static long dPlus(LocalDateTime dayBefore) {
                return ChronoUnit.DAYS.between(dayBefore, LocalDateTime.now());
            }

            public static long dMinus(LocalDateTime dayAfter) {
                return ChronoUnit.DAYS.between(dayAfter, LocalDateTime.now());
            }

            public static String timesAgo(LocalDateTime dayBefore) {
                long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());
                String word;
                if (gap == 0){
                    word = "방금 전";
                }else if (gap < 60) {
                    word = gap + "분 전";
                }else if (gap < 60 * 24){
                    word = (gap/60) + "시간 전";
                }else if (gap < 60 * 24 * 10) {
                    word = (gap/60/24) + "일 전";
                } else {
                    word = dayBefore.format(DateTimeFormatter.ofPattern("MM월 dd일"));
                }
                return word;
            }

            public static String customForm(LocalDateTime date) {
                return date.format(DateTimeFormatter.ofPattern("MM월 dd일"));
            }
        }
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardRequest implements Serializable {
        private final Long id;
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
        private final String hashtag;

        private final String boardFiles;
    }


}
