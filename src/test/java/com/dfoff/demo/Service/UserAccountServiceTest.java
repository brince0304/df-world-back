package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.SaveFileRepository;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Util.Bcrypt;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
    @InjectMocks
    UserAccountService sut;
    @Mock
    UserAccountRepository userAccountRepository;
    @Mock
    SaveFileRepository saveFileRepository;
    @Mock
    Bcrypt bcrypt;

    @Test
    @DisplayName("createAccount() - 계정생성 테스트")
    void givenNothing_whenCreatingAccount_thenCreateAccount() {
        UserAccount account = UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        SaveFile saveFile = SaveFile.builder().
                fileName("test").
                filePath("test").
                build();
        account.setProfileIcon(saveFile);
        given(userAccountRepository.save(any())).willReturn(account);
        //when&then
        sut.createAccount(createUserAccountSignUpRequest(), SaveFile.SaveFileDTO.builder().build());
        then(userAccountRepository).should().save(account);
    }

    @Test
    void givenUserId_whenValidateUserId_thenReturnBoolean() {
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        //when
        boolean result = sut.existsByUserId("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    void givenNickname_whenValidateUserId_thenReturnBoolean() {
        //given
        given(userAccountRepository.existsByNickname(any())).willReturn(true);
        //when
        boolean result = sut.existsByNickname("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    void givenEmail_whenValidateUserId_thenReturnBoolean() {
        //given
        given(userAccountRepository.existsByEmail(any())).willReturn(true);
        //when
        boolean result = sut.existsByEmail("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("createAccount() - 계정생성 테스트 예외 - 이미 존재하는 아이디")
    void givenExistsAccountId_whenCreatingUserAccount_thenDoNotCreateAccount() {
        //given
        UserAccount account = UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        SaveFile saveFile = SaveFile.builder().
                fileName("test").
                filePath("test").
                build();
        account.setProfileIcon(saveFile);
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(true);
        //when&then
        Throwable throwable = catchThrowable(() -> sut.createAccount(createUserAccountSignUpRequest(), SaveFile.SaveFileDTO.builder().build()));
        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("getUserAccountById() - 계정조회 테스트")
    void givenUserAccountId_whenGettingUserAccount_thenGetsUserAccount() {
        //given
        UserAccount account = UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        SaveFile saveFile = SaveFile.builder().id(1L).
                fileName("test").
                filePath("test").
                build();
        account.setProfileIcon(saveFile);
        given(userAccountRepository.findById(account.getUserId())).willReturn(java.util.Optional.of(account));
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(true);
        //when
        sut.getUserAccountById(account.getUserId());

        //then
        then(userAccountRepository).should().findById(account.getUserId());
    }

    @Test
    @DisplayName("getUserAccountById() - 계정조회 테스트 예외 - 존재하지 않는 계정 조회")
    void givenUserAccountId_whenGettingUserAccountButNotExists_thenGetsNullDto() {
        //given
        UserAccount account = UserAccount.builder().
                userId("test2").
                password("test2").
                email("test").
                nickname("test").
                build();
        SaveFile saveFile = SaveFile.builder().
                fileName("test").
                filePath("test").
                build();
        account.setProfileIcon(saveFile);
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(false);
        //when
        UserAccount.UserAccountMyPageResponse dto = sut.getUserAccountById(account.getUserId());

        //then
        assertThat(dto).isNull();
        then(userAccountRepository).should().existsByUserId(account.getUserId());
    }

    @Test
    @DisplayName("updateAccountDetails() - 계정 업데이트 테스트")
    void givenUserAccount_whenUpdatingUserDetails_thenUpdateUserDetail() {
        UserAccount.UserAccountUpdateRequest account = UserAccount.UserAccountUpdateRequest.builder().
                password("test2").passwordCheck("test2").
                email("test2").
                nickname("test2").
                build();
        given(userAccountRepository.findById(any())).willReturn(java.util.Optional.of(UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build()));
        //when&then
        sut.updateAccountDetails(account.toDto());
        then(userAccountRepository).should().findById(any());
    }



    @Test
    @DisplayName("deleteUserAccountById() - 계정 삭제 테스트")
    void givenUserAccountId_whenDeletingUserAccount_thenDeletesUserAccount() {
        //given
        String userId = "test";
        given(userAccountRepository.existsByUserId(userId)).willReturn(true);

        //when
        sut.deleteUserAccountById(userId);

        //then
        then(userAccountRepository).should().existsByUserId(userId);
        then(userAccountRepository).should().deleteById(userId);
    }

    @Test
    @DisplayName("deleteUserAccountById() - 계정 삭제 테스트 예외 - 존재하지 않는 계정 삭제")
    void givenUserAccountId_whenDeletingUserAccountButNotExists_thenDoNothing() {
        //given
        String userId = "test";
        given(userAccountRepository.existsByUserId(userId)).willReturn(false);

        //when
        sut.deleteUserAccountById(userId);

        //then
        then(userAccountRepository).should().existsByUserId(userId);
    }

    @Test
    void givenUserAccountAndProfileIcon_whenChangeProfilIcon_thenChangesProfileIcon() {
        //given
        given(userAccountRepository.findById(any())).willReturn(Optional.of(createUserAccount()));
        SaveFile saveFile = SaveFile.builder().id(1L).
                fileName("test").
                filePath("test").
                build();
        //when
        sut.changeProfileIcon(UserAccount.UserAccountDto.from(createUserAccount()), SaveFile.SaveFileDTO.from(saveFile));

        //then
        then(userAccountRepository).should().findById(any());
    }

    private UserAccount createUserAccount() {
        UserAccount account = UserAccount.builder().
                userId("test2").
                password("test2").
                email("test").
                nickname("test").
                build();
        SaveFile saveFile = SaveFile.builder().
                fileName("test").
                filePath("test").
                build();
        account.setProfileIcon(saveFile);
        return account;
    }

    private UserAccount.UserAccountSignUpRequest createUserAccountSignUpRequest() {
        return UserAccount.UserAccountSignUpRequest.builder().
                userId("test").
                password("test").
                passwordCheck("test").
                email("test").
                nickname("test").
                build();
    }
}