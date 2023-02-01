package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static reactor.core.publisher.Mono.when;


@ExtendWith(MockitoExtension.class)

public class NotificationServiceTest {
    @InjectMocks
    NotificationService sut;

    @Mock
    NotificationRepository notificationRepository;

    @Test
    void saveBoardCommentNotificationTest(){
        //when&then
        sut.saveBoardCommentNotification(UserAccount.UserAccountDto.from(createUserAccoun0()), BoardComment.BoardCommentDto.builder().id(1L).boardDto(createBoardDto()).commentContent("hagaga").build(),anyString(), NotificationType.COMMENT);
        then(notificationRepository).should().save(any());
    }


    @Test
    void saveBoardNotificationTest(){
        //when&then
        sut.saveBoardNotification(UserAccount.UserAccountDto.from(createUserAccoun0()), Board.BoardDto.builder().userAccount(UserAccount.UserAccountDto.from(createUserAccount())).id(1L).build(),anyString(), NotificationType.COMMENT);
        then(notificationRepository).should().save(any());
    }

    @Test
    void getUserNotificationsTest(){
        given(notificationRepository.getNotificationsByUserAccount_UserId(anyString(),any())).willReturn(Page.empty());
        sut.getUserNotifications(anyString(),any(Pageable.class));
        then(notificationRepository).should().getNotificationsByUserAccount_UserId(anyString(),any());
    }

    @Test
    void getUncheckedNotificationCount(){
        given(notificationRepository.getUnCheckedNotificationCountByUserId(any())).willReturn(1L);
        sut.getUncheckedNotificationCount(anyString());
        then(notificationRepository).should().getUnCheckedNotificationCountByUserId(any());
    }

    @Test
    void checkNotificationTest(){
        //when&then
        given(notificationRepository.findById(any())).willReturn(java.util.Optional.ofNullable(Notification.builder().build()));
        sut.checkNotification(any());
        then(notificationRepository).should().findById(any());
    }

    @Test
    void checkNotificationExceptionTest(){
        //when&then
        given(notificationRepository.findById(any())).willThrow(new IllegalArgumentException());
        Throwable throwable =  catchThrowable(()->sut.checkNotification(any()));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }


    private UserAccount createUserAccount(){
        UserAccount account =  UserAccount.builder().
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

    private Board.BoardRequest createBoardRequestDto(){
        return Board.BoardRequest.builder()
                .boardTitle("test2")
                .boardContent("tes2321331312231212")
                .boardType(BoardType.FREE)
                .build();
    }

    private Board.BoardRequest createBoardRequestDto2(){
        return Board.BoardRequest.builder()
                .boardTitle("test2")
                .boardContent("test222222222222")
                .boardType(BoardType.FREE)
                .build();
    }

    private BoardComment createBoardComment0(){
        return BoardComment.builder()
                .id(1L)
                .userAccount(createUserAccount())
                .board(createBoard())
                .commentContent("test")
                .build();
    }

    private BoardComment createBoardComment1(){
        return BoardComment.builder()
                .userAccount(createUserAccoun0())
                .board(createBoard())
                .commentContent("test")
                .build();
    }

    private Board createBoard(){
        return Board.builder()
                .id(1L)
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.FREE)
                .userAccount(createUserAccount())
                .build();
    }


    private UserAccount createUserAccoun0(){
        UserAccount account =  UserAccount.builder().
                userId("test20").
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

    private BoardComment.BoardCommentRequest boardCommentRequest() {
        return BoardComment.BoardCommentRequest.builder()
                .commentContent("test")
                .build();
    }

    private Board.BoardDto createBoardDto() {
        return Board.BoardDto.builder()
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.FREE)
                .userAccount(UserAccount.UserAccountDto.from(createUserAccount()))
                .build();
    }

}
