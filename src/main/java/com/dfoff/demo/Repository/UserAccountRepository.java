package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserAccountRepository  extends JpaRepository<UserAccount, String> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Modifying
    @Query("update Board b set b.isDeleted='Y' where b.userAccount.userId=:id")
    void deleteBoardByUserAccountId(@Param("id") String id);

    @Modifying
    @Query("update BoardComment b set b.isDeleted='Y' where b.userAccount.userId=:id")
    void deleteBoardCommentByUserAccountId(@Param("id") String id);


}
