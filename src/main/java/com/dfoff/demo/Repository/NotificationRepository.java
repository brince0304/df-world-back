package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long>{


    @Query("select count(l) from Notification l where l.userAccount.userId = :userId and l.checked = false")
    Long getUnCheckedNotificationCountByUserId(String userId);

    Page<Notification> getNotificationsByUserAccount_UserId(String userId, Pageable pageable);



    Boolean existsByLogContentContainingIgnoreCaseAndBoardIdAndNotificationType (String  logContent, Long boardId, NotificationType notificationType);


}
