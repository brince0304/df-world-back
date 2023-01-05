package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
    @InjectMocks UserAccountService sut;
    @Mock
    UserAccountRepository userAccountRepository;

    @Test
    @DisplayName("createAccount() - 계정생성 테스트")
    void givenNothing_whenCreatingAccount_thenCreateAccount() {
        UserAccount account =  UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        //when&then
        sut.createAccount(UserAccount.UserAccountDTO.from(account));
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
        UserAccount account =  UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(true);
        //when&then
        Throwable throwable = catchThrowable(() -> sut.createAccount(UserAccount.UserAccountDTO.from(account)));
        then(userAccountRepository).should().existsByUserId(account.getUserId());
        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("getUserAccountById() - 계정조회 테스트")
    void givenUserAccountId_whenGettingUserAccount_thenGetsUserAccount() {
        //given
        UserAccount account =  UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        given(userAccountRepository.getReferenceById(account.getUserId())).willReturn(account);
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(true);
        //when
        sut.getUserAccountById(account.getUserId());

        //then
        then(userAccountRepository).should().getReferenceById(account.getUserId());
    }

    @Test
    @DisplayName("getUserAccountById() - 계정조회 테스트 예외 - 존재하지 않는 계정 조회")
    void givenUserAccountId_whenGettingUserAccountButNotExists_thenGetsNullDto() {
        //given
        UserAccount account =  UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        given(userAccountRepository.existsByUserId(account.getUserId())).willReturn(false);
        //when
        UserAccount.UserAccountDTO dto  = sut.getUserAccountById(account.getUserId());

        //then
        assertThat(dto.getUserId()).isNull();
        then(userAccountRepository).should().existsByUserId(account.getUserId());
    }

    @Test
    @DisplayName("updateAccountDetails() - 계정 업데이트 테스트")
    void givenUserAccount_whenUpdatingUserDetails_thenUpdateUserDetail() {
        UserAccount.UserAccountUpdateRequest account =  UserAccount.UserAccountUpdateRequest.builder().
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
    @DisplayName("updateAccountDetails() - 계정 업데이트 테스트 예외 - 비밀번호 불일치")
    void givenUserAccount_whenUpdatingUserDetailsButValidatedFailed_thenDoNothing() {
        UserAccount.UserAccountUpdateRequest account =  UserAccount.UserAccountUpdateRequest.builder().
                password("test2").passwordCheck("test1").
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
        assertThat(userAccountRepository.findById(any()).get().getPassword()).isNotEqualTo(account.getPassword());
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
}