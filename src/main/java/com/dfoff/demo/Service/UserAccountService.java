package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Repository.NotificationRepository;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Bcrypt bcrypt;

    private final NotificationRepository notificationRepository;



    public boolean createAccount(UserAccount.UserAccountSignUpRequest account, SaveFile.SaveFileDTO profileIcon) {
        if (userAccountRepository.existsByUserId(account.getUserId())) {
            throw new EntityExistsException("이미 존재하는 아이디입니다.");
        }
        if (userAccountRepository.existsByEmail(account.getEmail())) {
            throw new EntityExistsException("이미 존재하는 이메일입니다.");
        }
        if (userAccountRepository.existsByNickname(account.getNickname())) {
            throw new EntityExistsException("이미 존재하는 닉네임입니다.");
        }
        log.info("account: {}", account);
        UserAccount account0 = userAccountRepository.save(account.toEntity());
        account0.setPassword(bcrypt.encode(account0.getPassword()));
        account0.setProfileIcon(profileIcon.toEntity());
        return true;
    }


    public boolean existsByUserId(String userId) {
        return userAccountRepository.existsByUserId(userId);
    }

    public boolean existsByEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userAccountRepository.existsByNickname(nickname);
    }



    public void updateAccountDetails(UserAccount.UserAccountDto request) {
        UserAccount account = userAccountRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByEmail(request.email())) {
            log.info("이미 존재하는 이메일입니다.");
            return;
        }
        if (userAccountRepository.existsByNickname(request.nickname())) {
            log.info("이미 존재하는 닉네임입니다.");
            return;
        }
        if(request.email() != null) {
            account.setEmail(request.email());
        }
        if(request.nickname() != null) {
            account.setNickname(request.nickname());
        }
    }



    public boolean changeProfileIcon (UserAccount.UserAccountDto dto, SaveFile.SaveFileDTO iconDto){
        UserAccount account = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setProfileIcon(iconDto.toEntity());
        return true;
    }

    public Page<Notification.UserLogResponse> getUserLog(String userId, Pageable pageable) {
        Page<Notification> userLog = notificationRepository.getUserLogByUserAccount_UserId(userId, pageable);
        userLog.forEach(o-> o.setChecked(Boolean.TRUE));
        return userLog.map(Notification.UserLogResponse::from);
    }

    public Long getUncheckedLogCount(String userId) {
        return notificationRepository.getUnCheckedLogCount(userId);
    }



    @Transactional(readOnly = true)
    public UserAccount.UserAccountMyPageResponse getUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            return UserAccount.UserAccountMyPageResponse.from(userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")));
        }
        return null;
    }


    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsByUserId(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            return userAccountRepository.findBoardCommentsByUserId(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsByUserIdOrderByLikeCount(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            return userAccountRepository.findBoardCommentsByUserIdOrderByLikeCount(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserId(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            return userAccountRepository.findBoardsByUserId(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByLikeCount(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            return userAccountRepository.findBoardsByUserIdOrderByLikeCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByViewCount(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            log.info("userId: {}", userId);
            return userAccountRepository.findBoardsByUserIdOrderByViewCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByComentCount(String userId, Pageable pageable) {
        if(userAccountRepository.existsByUserId(userId)){
            return userAccountRepository.findBoardsByUserIdOrderByCommentCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }


    public void deleteUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            userAccountRepository.deleteBoardByUserAccountId(userId);
            userAccountRepository.deleteBoardCommentByUserAccountId(userId);
            userAccountRepository.deleteById(userId);
        }
    }

    public UserAccount.UserAccountDto loginByUserId(UserAccount.LoginDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return UserAccount.UserAccountDto.builder().build();
    }


    public boolean chagePassword(UserAccount.UserAccountDto accountDTO, String password) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setPassword(bcrypt.encode(password));
        return true;
    }

    public boolean changeNickname(UserAccount.UserAccountDto accountDTO, String nickname) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if(userAccountRepository.existsByNickname(nickname)){
            return false;
        }
        account.setNickname(nickname);
        return true;
    }

    public boolean changeEmail(UserAccount.UserAccountDto accountDTO, String email) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if(userAccountRepository.existsByEmail(email)){
            return false;
        }
        account.setEmail(email);
        return true;
    }
}
