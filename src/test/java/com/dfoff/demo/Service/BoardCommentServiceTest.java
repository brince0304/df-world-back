package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardCommentRepository;
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

    @Test
    void findBoardCommentById() {
        //given
        given(boardCommentRepository.findBoardCommentById(any(Long.class))).willReturn(createBoardCommentDto().toEntity());

        //when
        BoardComment.BoardCommentDto dto = sut.findBoardCommentById(1L);

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
        given(boardCommentRepository.save(any(BoardComment.class))).willReturn(createBoardCommentDto().toEntity());

        //when
        sut.createBoardComment(createBoardCommentDto());

        //then
        then(boardCommentRepository).should().save(any(BoardComment.class));
    }

    @Test
    void createBoardCommentException() {
        //given

        //when
        Throwable throwable = catchThrowable(()-> sut.createBoardComment(createBoardCommentContentExceptionDto()));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createBoardCommentAccountNotException() {
        //given

        //when
        Throwable throwable = catchThrowable(()-> sut.createBoardComment(createBoardCommentAccountOrBoardExceptionDto()));

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

        //when
        sut.deleteBoardComment(any(Long.class));

        //then
        then(boardCommentRepository).should().deleteBoardCommentById(any(Long.class));
    }



    @Test
    void updateBoardCommentException() {
        //given
        given(boardCommentRepository.findBoardCommentById(any(Long.class))).willReturn(createBoardCommentDto().toEntity());
        //when
        Throwable throwable=catchThrowable(()->sut.updateBoardComment(1L, BoardComment.BoardCommentRequest.builder().build(), eq(createBoardCommentDto().getUserAccount().userId())));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void updateBoardCommentLike() {
        //given
        given(boardCommentRepository.findBoardCommentById(any(Long.class))).willReturn(createBoardCommentDto().toEntity());

        //when
        sut.updateBoardCommentLike(eq(1L) );

        //then
        then(boardCommentRepository).should().findBoardCommentById(any(Long.class));
    }

    @Test
    void updateBoardCommentDisLike() {

        //given
        given(boardCommentRepository.findBoardCommentById(any(Long.class))).willReturn(createBoardCommentDto().toEntity());

        //when
        sut.updateBoardCommentDisLike(eq(1L) );

        //then
        then(boardCommentRepository).should().findBoardCommentById(any(Long.class));
    }

    @Test
    void createChildrenComment() {
        //given
        given(boardCommentRepository.findBoardCommentById(any(Long.class))).willReturn(createBoardCommentDto().toEntity());
        given(boardCommentRepository.save(any(BoardComment.class))).willReturn(createBoardCommentDto().toEntity());

        //when
        sut.createChildrenComment(eq(1L), createBoardCommentDto());

        //then
        then(boardCommentRepository).should().save(any(BoardComment.class));
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

    private BoardComment.BoardCommentDto createBoardCommentDto() {
        return BoardComment.BoardCommentDto.builder()
                .id(1L)
                .commentContent("content")
                .userAccount(UserAccount.UserAccountDto.from(createUserAccount()))
                .board(createBoardDto())
                .build();
    }

    private BoardComment.BoardCommentDto createBoardCommentContentExceptionDto() {
        return BoardComment.BoardCommentDto.builder()
                .id(1L)
                .commentContent("")
                .userAccount(UserAccount.UserAccountDto.from(createUserAccount()))
                .board(createBoardDto())
                .build();
    }

    private BoardComment.BoardCommentDto createBoardCommentAccountOrBoardExceptionDto() {
        return BoardComment.BoardCommentDto.builder()
                .id(1L)
                .commentContent("")
                .userAccount(null)
                .board(createBoardDto())
                .build();
    }


    private Board.BoardDto createBoardDto() {
        return Board.BoardDto.builder()
                .boardTitle("test")
                .boardContent("test")
                .userAccount(UserAccount.UserAccountDto.from(createUserAccount()))
                .hashtags(new HashSet<>())
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
}