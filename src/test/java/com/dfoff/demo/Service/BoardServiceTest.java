package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
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
        Board board = Board.builder()
                .boardTitle("title")
                .boardContent("content")
                .boardType(BoardType.FREE)
                .userAccount(UserAccount.builder().build())
                .build();
        given(boardRepository.save(any(Board.class))).willReturn(board);
        //when
        sut.createBoard(createBoardDto(), createSaveFileDto());

        //then
        then(boardRepository).should().save(any(Board.class));
    }

    private Set<SaveFile.SaveFileDTO> createSaveFileDto() {
        return new HashSet<>();
    }

    @Test
    void getArticleTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(createBoardDto().toEntity());

        //when
        sut.getBoardDetail(1L);

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
    }

    @Test
    void getArticlesByKeywordTest(){
        //given
        given(boardRepository.findAllByBoardTitleContainingIgnoreCase(eq("te"), Pageable.ofSize(10))).willReturn(Page.empty());

        //when
        sut.getBoardsByKeyword(null, eq("te"),eq("title"),Pageable.ofSize(10));

        //then
        then(boardRepository).should().findAllByBoardTitleContainingIgnoreCase(eq("te"), Pageable.ofSize(10));
    }
    @Test
    void getArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(null);

        //when
        Throwable throwable = catchThrowable(() -> sut.getBoardDetail(1L));

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
        sut.deleteBoardById(any(Long.class));

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
        sut.updateBoard(any(Long.class),createBoardDto(),createSaveFileDto());

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardTitle()).isEqualTo("test");
    }

    @Test
    void updateArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(null);

        //when
        Throwable throwable = catchThrowable(()->sut.updateBoard(any(Long.class),createBoardDto(),createSaveFileDto()));

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