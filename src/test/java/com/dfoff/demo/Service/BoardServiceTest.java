package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
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
                .hashtags(new HashSet<>())
                .build();
        given(boardRepository.save(any(Board.class))).willReturn(board);
        //when
        sut.createBoard(createBoardDto(), createSaveFileDto(),createCharacterEntityDto());

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
given(boardRepository.findAll(Pageable.ofSize(10))).willReturn(new PageImpl<>(List.of()));

        //when
        sut.getBoardsByKeyword(null,null,null,Pageable.ofSize(10));

        //then
        then(boardRepository).should().findAll(any(Pageable.class));

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
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.deleteBoardById(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        then(boardRepository).should().deleteBoardById(any(Long.class));
    }

    @Test
    void updateArticleTest() {
        //given
        Board board = createBoardDto().toEntity();
        board.setBoardTitle("test00");
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.updateBoard(any(Long.class),createBoardDto(),createSaveFileDto(),null);

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardTitle()).isEqualTo("test");
    }

    @Test
    void updateArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(null);

        //when
        Throwable throwable = catchThrowable(()->sut.updateBoard(any(Long.class),createBoardDto(),createSaveFileDto(),createCharacterEntityDto()));
        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    void increaseViewCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.increaseViewCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardViewCount()).isEqualTo(1);
    }

    @Test
    void increaseLikeCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.increaseLikeCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardLikeCount()).isEqualTo(1);
    }


    @Test
    void decreaseLikeCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(board);

        //when
        sut.decreaseLikeCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardLikeCount()).isEqualTo(-1);
    }





    private Board.BoardDto createBoardDto() {
        return Board.BoardDto.builder()
                .boardTitle("test")
                .boardContent("test")
                .hashtags(createHashtagDto())
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
    private CharacterEntity.CharacterEntityDto createCharacterEntityDto(){
        return CharacterEntity.CharacterEntityDto.builder()
                .characterName("test")
                .serverId("cain")
                .level(1)
                .build();

    }

    private Set<Hashtag.HashtagDto> createHashtagDto(){
        return new HashSet<>(Set.of(Hashtag.HashtagDto.builder().name("test").build()));
    }

    private Page<Board> createBoardPage(){
        return new PageImpl<>(List.of(createBoardDto().toEntity()));
    }


}