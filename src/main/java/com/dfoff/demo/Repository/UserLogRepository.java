package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserLogRepository extends JpaRepository<UserLog, Long>{


    @Query("select count(l) from UserLog l where l.userAccount.userId = :userId and l.isChecked = 'N'")
    Long getUnCheckedLogCount(String userId);

    Page<UserLog> getUserLogByUserAccount_UserId(String userId, Pageable pageable);


}
