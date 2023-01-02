package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserAccountRepository  extends JpaRepository<UserAccount, String> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
