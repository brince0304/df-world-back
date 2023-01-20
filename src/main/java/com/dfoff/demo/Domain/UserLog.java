package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.UserAccount.LogType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import static com.dfoff.demo.Domain.Board.Chrono.timesAgo;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLog extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long boardId;

    @ManyToOne (fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Setter
    @Enumerated
    private LogType logType;

    @Setter
    @Builder.Default
    private String isChecked = "N";


    @Setter
    private String logContent;

    public static UserLog of(UserAccount userAccount,Board board,LogType type,String logContent){
        return UserLog.builder()
                .boardId(board.getId())
                .userAccount(userAccount)
                .logType(type)
                .logContent(logContent)
                .build();
    }

    public static UserLog of(UserAccount userAccount,BoardComment comment,LogType type,String logContent){
        return UserLog.builder()
                .boardId(comment.getBoard().getId())
                .userAccount(userAccount)
                .logType(type)
                .logContent(logContent)
                .build();
    }

    @Data
    @Builder
    public static class UserLogResponse {

        private final Long id;
        private final Long boardId;


        private final String logType;

        private final String isChecked;

        private final String createdDate;

        private final String logContent;


        public static UserLogResponse from(UserLog userLog) {
            return UserLogResponse.builder()
                    .id(userLog.getId())
                    .boardId(userLog.getBoardId())
                    .logType(userLog.getLogType().toString())
                    .isChecked(userLog.getIsChecked())
                    .createdDate(timesAgo(userLog.getCreatedAt()))
                    .logContent(userLog.getLogContent())
                    .build();
        }


    }
}
