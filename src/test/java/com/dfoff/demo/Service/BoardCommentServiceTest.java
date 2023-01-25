package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Repository.BoardCommentRepository;
import com.dfoff.demo.Repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardCommentServiceTest {
    @InjectMocks BoardCommentService sut;

    @Mock
    BoardCommentRepository boardCommentRepository;

    @Mock
    NotificationRepository notificationRepository;

    @Test
    void findBoardCommentById() {
        //given

        given(boardCommentRepository.existsById(anyLong())).willReturn(true);
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(createBoardComment0());

        //when
        BoardComment.BoardCommentResponse dto = sut.findBoardCommentById(1L);

        //then
        then(boardCommentRepository).should().findBoardCommentById(any(Long.class));
    }

    @Test
    void findBoardCommentByBoardId() {
        //given
        given(boardCommentRepository.findBoardCommentByBoardId(any(Long.class))).willReturn(List.of());

        //when
        sut.findBoardCommentByBoardId(1L);

        //then
        then(boardCommentRepository).should().findBoardCommentByBoardId(any(Long.class));
    }

    @Test
    void createBoardComment() {
        //given
        given(boardCommentRepository.save(any(BoardComment.class))).willReturn(any());

        //when
        sut.createBoardComment(createBoardCommentRequestDto(), UserAccount.UserAccountDto.from(createUserAccount()),createBoardDto());

        //then
        then(boardCommentRepository).should().save(any(BoardComment.class));
    }

    @Test
    void createBoardCommentException() {
        //given

        //when
        Throwable throwable = catchThrowable(()-> sut.createBoardComment(createBoardCommentRequestExceptionDto(), UserAccount.UserAccountDto.from(createUserAccount()),null));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createBoardCommentAccountNotException() {
        //given

        //when
        Throwable throwable = catchThrowable(()-> sut.createBoardComment(createBoardCommentRequestDto(),null,createBoardDto()));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findBoardCommentsByParentId() {
        //given
        given(boardCommentRepository.findBoardCommentByParentCommentId(any(Long.class),any(Long.class))).willReturn(List.of());

        //when
        sut.findBoardCommentsByParentId(any(Long.class),any(Long.class));

        //then
        then(boardCommentRepository).should().findBoardCommentByParentCommentId(any(Long.class),any(Long.class));
    }

    @Test
    void deleteBoardComment() {
        //given
        given(boardCommentRepository.existsById(any(Long.class))).willReturn(true);
        //when
        sut.deleteBoardComment(anyLong());

        //then
        then(boardCommentRepository).should().deleteById(any(Long.class));
    }



    @Test
    void updateBoardCommentException() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(createBoardComment0());
        //when
        Throwable throwable=catchThrowable(()->sut.updateBoardComment(anyLong(),createBoardCommentRequestDto(),null ));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void updateBoardCommentLike() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(createBoardComment0());

        //when
        sut.updateBoardCommentLike(eq(1L),"" );

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }

    @Test
    void updateBoardCommentDisLike() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(createBoardComment0());

        //when
        sut.updateBoardCommentDisLike(eq(1L),"");

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }

    @Test
    void createChildrenComment() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(createBoardComment0());

        //when
        sut.createChildrenComment(eq(1L), createBoardCommentChildrenRequestDto(), UserAccount.UserAccountDto.from(createUserAccount()),createBoardDto());

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }

    @Test
    void createNotificationTest(){
        //given
        given(boardCommentRepository.save(any())).willReturn(createBoardComment0());

        //when
        sut.createBoardComment(createBoardCommentRequestDto(), UserAccount.UserAccountDto.from(createUserAccoun0()),createBoardDto());

        //then
        then(boardCommentRepository).should().save(any());
        assertThat(createUserAccoun0().getNotifications().size()).isEqualTo(1);
    }



    @Test
    void findBestBoardCommentByBoardId() {
        //given
        given(boardCommentRepository.findBoardCommentByLikeCount(any(Long.class))).willReturn(List.of());

        //when
        sut.findBestBoardCommentByBoardId(any(Long.class));

        //then
        then(boardCommentRepository).should().findBoardCommentByLikeCount(any(Long.class));
    }






    private BoardComment.BoardCommentRequest createBoardCommentRequestDto(){
        return BoardComment.BoardCommentRequest.builder()
                .commentContent("content")
                .build();
    }

    private BoardComment.BoardCommentRequest createBoardCommentChildrenRequestDto(){
        return BoardComment.BoardCommentRequest.builder()
                .commentId(1L)
                .commentContent("content")
                .build();
    }

    private BoardComment.BoardCommentRequest createBoardCommentRequestExceptionDto(){
        return BoardComment.BoardCommentRequest.builder()
                .commentContent("")
                .build();
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

    private CharacterEntity.CharacterEntityDto createCharacterEntityDto(){
        return CharacterEntity.CharacterEntityDto.builder()
                .characterName("test")
                .serverId("cain")
                .level(1)
                .build();

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
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.FREE)
                .userAccount(createUserAccount())
                .build();
    }
}