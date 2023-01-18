package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import com.dfoff.demo.Util.FileUtil;
import com.dfoff.demo.Util.OpenAPIUtil;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
    @Setter
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

    @OneToMany (mappedBy = "board",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private final Set<BoardComment> boardComments = new LinkedHashSet<>();


    @OneToMany (mappedBy = "board")
    @Builder.Default
    @ToString.Exclude
    private final Set<BoardHashtagMapper> hashtags = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn (name = "character_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @Setter
    private CharacterEntity character;






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
                    .build();



        }
            public Board toEntity(){
                return  Board.builder()
                        .id(id)
                        .boardType(boardType)
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .userAccount(userAccount.toEntity()).build();
            }

            public Board toEntity(UserAccount userAccount){
                return  Board.builder()
                        .id(id)
                        .boardType(boardType)
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .userAccount(userAccount).build();
            }




    }

    @Data
    @Builder
    public static class CharacterBoardResponse implements Serializable{
        private final String characterId;
        private final String characterName;
        private final String serverId;

        private final String characterImageUrl ;

        private final String jobName;

        private final String adventureName;

        private final String adventureFame;

        public static CharacterBoardResponse from(CharacterEntity characterEntity){
            return CharacterBoardResponse.builder()
                    .characterId(String.valueOf(characterEntity.getCharacterId()))
                    .characterName(characterEntity.getCharacterName())
                    .serverId(characterEntity.getServerId())
                    .jobName(characterEntity.getJobName())
                    .adventureName(characterEntity.getAdventureName() == null ? "갱신필요" : characterEntity.getAdventureName())
                    .adventureFame(characterEntity.getAdventureFame() == null ? "0" : characterEntity.getAdventureFame())
                    .characterImageUrl(OpenAPIUtil.getCharacterImgUrl(characterEntity.getCharacterId(), characterEntity.getServerId(),"1"))
                    .build();
        }
    }

    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardListResponse implements Serializable {
        private final String createdAt;
        private final String createdBy;
        private final String modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final BoardType boardType;
        private final String boardTitle;
        private final String boardContent;
        private final String isDeleted;
        private final Integer boardViewCount;
        private final Integer boardLikeCount;
        private final String commentCount;

        private final String userId;

        private final String userNickname;

        private final Set<CharacterBoardResponse> characters;


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
                case REPORT -> "사건/사고";
            };
        }
        public static BoardListResponse from(Board board){
            return BoardListResponse.builder()
                    .createdAt(board.getCreatedAt().toString())
                    .createdBy(board.getCreatedBy())
                    .modifiedAt(board.getModifiedAt().toString())
                    .modifiedBy(board.getModifiedBy())
                    .id(board.getId())
                    .boardType(board.getBoardType())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .isDeleted(board.getIsDeleted())
                    .boardViewCount(board.getBoardViewCount())
                    .boardLikeCount(board.getBoardLikeCount())
                    .commentCount(String.valueOf(board.getBoardComments().size()))
                    .userId(board.getUserAccount().getUserId())
                    .userNickname(board.getUserAccount().getNickname())
                    .characters(board.getCharacter()==null  ? new HashSet<>() : Set.of(CharacterBoardResponse.from(board.getCharacter())))
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
        @Size(min = 2, max = 50,message = "제목은 2자 이상 50자 이하로 입력해주세요.")
        private final String boardTitle;
        @Size(min = 12, max = 5000,message = "내용은 5자 이상 5000자 이하로 입력해주세요.")
        private final String boardContent;
        private final String hashtag;

        private final String boardFiles;
        private final String characterId;

        private final String serverId;

        public Board toEntity(UserAccount userAccount){
            return Board.builder()
                    .id(this.id)
                    .boardType(this.boardType)
                    .boardTitle(this.boardTitle)
                    .boardContent(this.boardContent)
                    .userAccount(userAccount)
                    .build();
        }
    }


    /**
     * A DTO for the {@link Board} entity
     */
    @Data
    @Builder
    public static class BoardDetailResponse implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final BoardType boardType;
        @NotNull
        @Length(min = 1, max = 100)
        private final String boardTitle;
        @NotNull
        @Length(min = 1, max = 10000)
        private final String boardContent;
        private final String isDeleted;
        private final Integer boardViewCount;
        private final Integer boardLikeCount;

        private final Set<CharacterBoardResponse> characters;

        private final String userId;

        private final String userNickname;

        private final String userProfileIconPath;

        private final String commentCount;

        private final List<String> hashtags;

        public static BoardDetailResponse from (Board board){
            return BoardDetailResponse.builder()
                    .createdAt(board.getCreatedAt())
                    .createdBy(board.getCreatedBy())
                    .modifiedAt(board.getModifiedAt())
                    .modifiedBy(board.getModifiedBy())
                    .id(board.getId())
                    .boardType(board.getBoardType())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .isDeleted(board.getIsDeleted())
                    .boardViewCount(board.getBoardViewCount())
                    .boardLikeCount(board.getBoardLikeCount())
                    .characters(board.getCharacter()==null  ? new HashSet<>() : Set.of(CharacterBoardResponse.from(board.getCharacter())))
                    .userId(board.getUserAccount().getUserId())
                    .userNickname(board.getUserAccount().getNickname())
                    .userProfileIconPath(FileUtil.getProfileIconPath(board.getUserAccount().getProfileIcon().getFileName()))
                    .commentCount(String.valueOf(board.getBoardComments().size()))
                    .hashtags(board.getHashtags().stream().map(BoardHashtagMapper::getHashtag).map(Hashtag::getName).collect(Collectors.toList()))
                    .build();
        }
    }
}
