package com.dfoff.demo.Domain;

import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.JpaAuditing.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static com.dfoff.demo.Util.CharactersUtil.timesAgo;


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
    private NotificationType notificationType;

    @Setter
    @Builder.Default
    private Boolean checked = Boolean.FALSE;


    @Setter
    private String notificationContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Notification of(UserAccount userAccount, Board board, NotificationType type, String logContent){
        return Notification.builder()
                .boardId(board.getId())
                .userAccount(userAccount)
                .notificationType(type)
                .notificationContent(logContent)
                .build();
    }

    public static Notification of(UserAccount userAccount, BoardComment comment, NotificationType type, String logContent){
        return Notification.builder()
                .boardId(comment.getBoard().getId())
                .userAccount(userAccount)
                .notificationType(type)
                .notificationContent(logContent)
                .build();
    }



    @Data
    @Builder
    public static class UserLogResponse {

        private final Long id;
        private final Long boardId;


        private final String notificationType;

        private final Boolean checked;

        private final String createdDate;

        private final String notificationContent;


        public static UserLogResponse from(Notification notification) {
            return UserLogResponse.builder()
                    .id(notification.getId())
                    .boardId(notification.getBoardId())
                    .notificationType(notification.getNotificationType().toString())
                    .checked(notification.getChecked())
                    .createdDate(timesAgo(notification.getCreatedAt()))
                    .notificationContent(notification.getNotificationContent())
                    .build();
        }


    }
}
