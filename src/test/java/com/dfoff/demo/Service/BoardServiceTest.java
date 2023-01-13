package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @InjectMocks BoardService sut;
    @Mock
    BoardRepository boardRepository;


    @Test
    void createArticleTest() {
        //given
        //when
        sut.createArticle(createBoardDto());

        //then
        then(boardRepository).should().save(any(Board.class));
    }

    @Test
    void getArticleTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(createBoardDto().toEntity());

        //when
        sut.getArticle(1L);

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
    }

    @Test
    void getArticlesByKeywordTest(){
        //given
        given(boardRepository.findAllByBoardTitleContainingIgnoreCase(anyString(), Pageable.ofSize(10))).willReturn(Page.empty());

        //when
        sut.getArticlesByKeyword(null, anyString(),"title",Pageable.ofSize(10));

        //then
        then(boardRepository).should().findAllByBoardTitleContainingIgnoreCase(anyString(), Pageable.ofSize(10));
    }
    @Test
    void getArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(null);

        //when
        Throwable throwable = catchThrowable(() -> sut.getArticle(1L));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void deleteArticleByIdTest() {
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findById(any(Long.class))).willReturn(Optional.of(board));

        //when
        sut.deleteArticleById(any(Long.class));

        //then
        then(boardRepository).should().findById(any(Long.class));
        assertThat(board.getIsDeleted()).isEqualTo("Y");
    }

    @Test
    void updateArticleTest() {
        //given
        Board board = createBoardDto().toEntity();
        board.setBoardTitle("test00");
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.updateArticle(any(Long.class),createBoardDto(),"test2");

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardTitle()).isEqualTo("test");
    }

    @Test
    void updateArticleIllegalArgumentExceptionTest() {
        //given
        Board board = createBoardDto().toEntity();
        board.setBoardTitle("test00");
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        Throwable throwable = catchThrowable(()->sut.updateArticle(any(Long.class),createBoardDto(),"test0"));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(null);

        //when
        Throwable throwable = catchThrowable(()->sut.updateArticle(any(Long.class),createBoardDto(),"test0"));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }





    private Board.BoardDto createBoardDto() {
        return Board.BoardDto.builder()
                .boardTitle("test")
                .boardContent("test")
                .userAccount(UserAccount.UserAccountDto.from(createUserAccount()))
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