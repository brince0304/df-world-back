package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.UserAccount.LogType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import static com.dfoff.demo.Util.SearchPageUtil.timesAgo;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Notification extends AuditingFields {
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

    public static Notification of(UserAccount userAccount, Board board, LogType type, String logContent){
        return Notification.builder()
                .boardId(board.getId())
                .userAccount(userAccount)
                .logType(type)
                .logContent(logContent)
                .build();
    }

    public static Notification of(UserAccount userAccount, BoardComment comment, LogType type, String logContent){
        return Notification.builder()
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


        public static UserLogResponse from(Notification notification) {
            return UserLogResponse.builder()
                    .id(notification.getId())
                    .boardId(notification.getBoardId())
                    .logType(notification.getLogType().toString())
                    .isChecked(notification.getIsChecked())
                    .createdDate(timesAgo(notification.getCreatedAt()))
                    .logContent(notification.getLogContent())
                    .build();
        }


    }
}
