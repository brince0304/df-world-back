package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long>{


    @Query("select count(l) from Notification l where l.userAccount.userId = :userId and l.checked = false")
    Long getUnCheckedLogCount(String userId);

    Page<Notification> getUserLogByUserAccount_UserId(String userId, Pageable pageable);


}
