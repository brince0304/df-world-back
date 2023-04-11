package com.dfoff.demo.repository;

import com.dfoff.demo.domain.enums.useraccount.NotificationType;
import com.dfoff.demo.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long>{


    @Query("select count(l) from Notification l where l.userAccount.userId = :userId and l.checked = false")
    Long getUnCheckedNotificationCountByUserId(@Param("userId") String userId);

    Page<Notification> getNotificationsByUserAccount_UserId(String userId, Pageable pageable);



    Boolean existsByNotificationContentContainingIgnoreCaseAndBoardIdAndNotificationType (String  logContent, Long boardId, NotificationType notificationType);

   @Query("select count(l) from Notification l where l.userAccount.userId = :userId and l.checked = false")
    Long getNotificationCountByUserId(String userId);
}
