package com.dfoff.demo.service;

import com.dfoff.demo.domain.*;
import com.dfoff.demo.repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.repository.UserAccountRepository;
import com.dfoff.demo.repository.AdventureRepository;
import com.dfoff.demo.utils.Bcrypt;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
    @InjectMocks
    UserAccountService sut;
    @Mock
    UserAccountRepository userAccountRepository;

    @Mock
    AdventureRepository adventureRepository;

    @Mock
    UserAccountCharacterMapperRepository mapperRepository;

    @Mock
    Bcrypt bcrypt;

    @Test
    @DisplayName("계정생성 테스트")
    void createAccountTest() {
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
        sut.createAccount(createUserAccountSignUpRequest(), SaveFile.SaveFileDto.builder().build());
        then(userAccountRepository).should().save(account);
    }

    @Test
    @DisplayName("아이디 중복확인 테스트")
    void existsByUserIdTest() {
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        //when
        boolean result = sut.existsByUserId("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("계정 모험단 연동 해제 테스트")
    void deleteUserAdventureTest(){
        //given

        UserAccount userAccount = UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        Adventure adventure = Adventure.builder().
                adventureName("test")
                        .userAccount(userAccount).

                build();
        userAccount.setAdventure(adventure);
        given(userAccountRepository.findById(any())).willReturn(Optional.of(userAccount));
        //when
        sut.deleteUserAdventureFromUserAccount(userAccount.getUserId());
        //then
        then(userAccountRepository).should().findById(any());
        assertThat(userAccount.getAdventure()).isNull();
    }
    @Test
    @DisplayName("계정 모험단 연동 해제 테스트 예외 - 모험단이 없을때")
    void deleteUserAdventureExceptionTest(){
        //given

        UserAccount userAccount = UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build();
        given(userAccountRepository.findById(any())).willReturn(Optional.of(userAccount));
        //when
        Throwable throwable = catchThrowable(()->sut.deleteUserAdventureFromUserAccount(userAccount.getUserId()));
        //then
        then(userAccountRepository).should().findById(any());
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("닉네임 중복확인 테스트")
    void existsByNicknameTest() {
        //given
        given(userAccountRepository.existsByNickname(any())).willReturn(true);
        //when
        boolean result = sut.existsByNickname("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일 중복확인 테스트")
    void existsByEmailTest() {
        //given
        given(userAccountRepository.existsByEmail(any())).willReturn(true);
        //when
        boolean result = sut.existsByEmail("test");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("계정생성 테스트 예외 - 이미 존재하는 아이디")
    void createAccountExceptionTest() {
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
        Throwable throwable = catchThrowable(() -> sut.createAccount(createUserAccountSignUpRequest(), SaveFile.SaveFileDto.builder().build()));
        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("계정조회 테스트")
    void getUserAccountByIdTest() {
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
    @DisplayName("계정조회 테스트 예외 - 존재하지 않는 계정 조회")
    void getUserAccountByIdExceptionTest() {
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
    @DisplayName("계정 삭제 테스트")
    void deleteUserAccountByIdTest() {
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
    @DisplayName("계정 삭제 테스트 예외 - 존재하지 않는 계정 삭제")
    void deleteUserAccountByIdExceptionTest() {
        //given
        String userId = "test";
        given(userAccountRepository.existsByUserId(userId)).willReturn(false);

        //when
        Throwable throwable = catchThrowable(()->sut.deleteUserAccountById(userId));

        //then
        then(userAccountRepository).should().existsByUserId(userId);
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("계정 모험단 등록 테스트")
    void saveUserAdventureTest(){
        given(adventureRepository.checkAdventureHasUserAccount(any())).willReturn(false);
        given(adventureRepository.existsByRepresentCharacter_CharacterId(any())).willReturn(false);
        given(userAccountRepository.findById(any())).willReturn(java.util.Optional.of(UserAccount.builder().
                userId("test").
                password("test").
                email("test").
                nickname("test").
                build()));
        given(adventureRepository.save(any())).willReturn(Adventure.builder().characters(new HashSet<>()).build());

        sut.saveUserAdventure(Adventure.UserAdventureRequest.builder().adventureName("test")
                .representCharacterId("test").serverId("test").build(),
                UserAccount.UserAccountDto.from(createUserAccount()),
                CharacterEntity.CharacterEntityDto.builder().characterId("test").serverId("test").adventureName("test").build(),
                new ArrayList<>());

            then(adventureRepository).should().save(any());
    }

    @Test
    void updateUserPasswordTest(){
        UserAccount account = createUserAccount();
        String previousAccountPassword = account.getPassword();
        given(userAccountRepository.findById(any())).willReturn(java.util.Optional.of(account));
        sut.changePassword(UserAccount.UserAccountDto.from(account),"biqwj@!908e@");
        then(userAccountRepository).should().findById(any());
       assertThat(account.getPassword()).isNotEqualTo(previousAccountPassword);
    }

    @Test
    @DisplayName("계정 모험단 등록 테스트 - 예외 이미 존재하는 모험단")
    void saveUserAdventureExceptionTest(){
        given(adventureRepository.checkAdventureHasUserAccount(any())).willReturn(true);

        Throwable throwable= catchThrowable(()->sut.saveUserAdventure(Adventure.UserAdventureRequest.builder().adventureName("test")
                        .representCharacterId("test").serverId("test").build(),
                UserAccount.UserAccountDto.from(createUserAccount()),
                CharacterEntity.CharacterEntityDto.builder().characterId("test").serverId("test").adventureName("test").build(),
                new ArrayList<>()));

        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("계정 모험단 등록 테스트 - 예외 이미 등록된 대표 캐릭터")
    void saveUserAdventureException2Test(){
        given(adventureRepository.checkAdventureHasUserAccount(any())).willReturn(false);
        given(adventureRepository.existsByRepresentCharacter_CharacterId(any())).willReturn(true);


        Throwable throwable= catchThrowable(()->sut.saveUserAdventure(Adventure.UserAdventureRequest.builder().adventureName("test")
                        .representCharacterId("test").serverId("test").build(),
                UserAccount.UserAccountDto.from(createUserAccount()),
                CharacterEntity.CharacterEntityDto.builder().characterId("test").serverId("test").adventureName("test").build(),
                new ArrayList<>()));

        assertThat(throwable).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("계정 모험단 갱신 테스트")
    void refreshUserAdventureTest(){
        given(adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(any())).willReturn(true);
        UserAccount account = createUserAccount();
        account.setAdventure(createUserAdventure());
        given(userAccountRepository.findById(any())).willReturn(java.util.Optional.of(account));

        sut.refreshUserAdventure(UserAccount.UserAccountDto.from(account));

        then(adventureRepository).should().existsByUserAccount_UserIdAndDeletedIsFalse(any());
        then(userAccountRepository).should().findById(any());
    }

    @Test
    @DisplayName("유저 모험단 갱신 테스트 - 모험단이 존재하지 않는 경우")
    void refreshUserAdventureExceptionTest(){
        given(adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(any())).willReturn(false);
        UserAccount account = createUserAccount();
        account.setAdventure(createUserAdventure());

        Throwable throwable = catchThrowable(()-> sut.refreshUserAdventure(UserAccount.UserAccountDto.from(account)));

        then(adventureRepository).should().existsByUserAccount_UserIdAndDeletedIsFalse(any());
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("유저 모험단 갱신 테스트 - 계정이 존재하지 않는 경우")
    void refreshUserAdventureExceptionTest2(){
        given(adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(any())).willReturn(true);
        given(userAccountRepository.findById(any())).willThrow(EntityNotFoundException.class);
        UserAccount account = createUserAccount();
        account.setAdventure(createUserAdventure());

        Throwable throwable = catchThrowable(()-> sut.refreshUserAdventure(UserAccount.UserAccountDto.from(account)));

        then(adventureRepository).should().existsByUserAccount_UserIdAndDeletedIsFalse(any());
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("계정 프로필 아이콘 변경 테스트")
    void changeProfileIconTest() {
        //given
        given(userAccountRepository.findById(any())).willReturn(Optional.of(createUserAccount()));
        SaveFile saveFile = SaveFile.builder().id(1L).
                fileName("test").
                filePath("test").
                build();
        //when
        sut.changeProfileIcon(UserAccount.UserAccountDto.from(createUserAccount()), SaveFile.SaveFileDto.from(saveFile));
        //then
        then(userAccountRepository).should().findById(any());
    }

    @Test
    void addCharacter(){
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        given(userAccountRepository.findById(any())).willReturn(Optional.ofNullable(UserAccount.builder().userId("test").build()));

        //when
        sut.addCharacterToUserAccount(UserAccount.UserAccountDto.builder().userId("test").build(),CharacterEntity.CharacterEntityDto.builder().characterId("test").build());

        //then
        then(mapperRepository).should().save(any());
    }

    @Test
    void deleteCharacter(){
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        given(userAccountRepository.findById(any())).willReturn(Optional.ofNullable(UserAccount.builder().userId("test").build()));
        given(mapperRepository.findByUserAccountAndCharacter(any(),any())).willReturn(UserAccountCharacterMapper.of(UserAccount.builder().userId("test").build(),CharacterEntity.builder().characterId("test").build()));
        //when
        sut.deleteCharacterFromUserAccount(UserAccount.UserAccountDto.builder().userId("test").build(),CharacterEntity.CharacterEntityDto.builder().characterId("test").build());

        //then
        then(mapperRepository).should().findByUserAccountAndCharacter(any(),any());
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

    private Adventure createUserAdventure() {
        return Adventure.builder().
                userAccount(createUserAccount())
                .characters(new HashSet<>())
                .adventureName("test")
                .representCharacter(CharacterEntity.builder().build())
                .build();
    }
}