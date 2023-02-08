package com.dfoff.demo.service;

import com.dfoff.demo.domain.*;
import com.dfoff.demo.domain.enums.BoardType;
import com.dfoff.demo.repository.BoardRepository;
import com.dfoff.demo.repository.NotificationRepository;
import com.dfoff.demo.utils.CharactersUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
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
    @Mock
    NotificationRepository notificationRepository;


    static MockedStatic<CharactersUtil> charactersUtilMockedStatic;

    @BeforeAll
    static void beforeAll() {
        charactersUtilMockedStatic = org.mockito.Mockito.mockStatic(CharactersUtil.class);
    }







    @Test
    void createArticleTest() throws IllegalAccessException {
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
        sut.createBoard(createBoardRequestDto(),createSaveFileDto(),createUserAccountDto(),null);
        //then
        then(boardRepository).should().save(any(Board.class));
    }

    private UserAccount.UserAccountDto createUserAccountDto() {
        return UserAccount.UserAccountDto.from(createUserAccount());


    }

    private Set<SaveFile.SaveFileDto> createSaveFileDto() {
        return new HashSet<>();
    }

    @Test
    void getArticleTest() {
        //given
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.ofNullable(createBoardDto().toEntity()));

        //when
        sut.getBoardDetailById(1L);
        charactersUtilMockedStatic.when(()-> CharactersUtil.timesAgo(any())).thenReturn("1분전");

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
        given(boardRepository.findBoardById(any(Long.class))).willThrow(EntityNotFoundException.class);

        //when
        Throwable throwable = catchThrowable(() -> sut.getBoardDetailById(1L));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void deleteArticleByIdTest() {
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.ofNullable(board));

        //when
        sut.deleteBoardById(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        then(boardRepository).should().deleteById(any(Long.class));
    }

    @Test
    void updateArticleTest() {
        //given
        Board board = Board.builder()
                        .id(1L)
                                .boardTitle("안녕하세요")
                                        .boardContent("방ㅇ가방가입니다하이하이")
                                                .boardType(BoardType.NOTICE).build();
        String previousTitle = board.getBoardTitle();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.of(board));

        //when
        sut.updateBoard(eq(1L),createBoardRequestDto2(),createSaveFileDto(),null);

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(previousTitle).isEqualTo(board.getBoardTitle());
    }

    @Test
    void updateArticleOtherCaseTest() {
        //given
        Board board = Board.builder()
                .id(1L)
                .boardTitle("안녕하세요")
                .boardContent("방ㅇ가방가입니다하이하이")
                .boardType(BoardType.NOTICE).build();
        String previousTitle = board.getBoardTitle();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.of(board));

        //when
        sut.updateBoard(eq(1L),createBoardRequestDto2(),createSaveFileDto(),null);

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(previousTitle).isEqualTo(board.getBoardTitle());
    }

    @Test
    void updateArticleEntityNotFoundExceptionTest() {
        //given
        given(boardRepository.findBoardById(anyLong())).willThrow(EntityNotFoundException.class);

        //when
        Throwable throwable = catchThrowable(()->sut.updateBoard(anyLong(),createBoardRequestDto2(),createSaveFileDto(),createCharacterEntityDto()));
        //then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void increaseViewCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.ofNullable(board));

        //when
        sut.increaseBoardViewCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardViewCount()).isEqualTo(1);
    }

    @Test
    void increaseLikeCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.ofNullable(board));

        //when
        sut.increaseBoardLikeCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardLikeCount()).isEqualTo(1);
    }


    @Test
    void decreaseLikeCountTest(){
        //given
        Board board = createBoardDto().toEntity();
        given(boardRepository.findBoardById(any(Long.class))).willReturn(Optional.ofNullable(board));

        //when
        sut.decreaseBoardLikeCount(any(Long.class));

        //then
        then(boardRepository).should().findBoardById(any(Long.class));
        assertThat(board.getBoardLikeCount()).isEqualTo(-1);
    }





    private Board.BoardDto createBoardDto() {
        return Board.BoardDto.builder()
                .id(1L)
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.FREE)
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
                .boardTitle("안녕하세요")
                .boardContent("test222222222222")
                .boardType(BoardType.FREE)
                .build();
    }

}