package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Repository.BoardCommentRepository;
import com.dfoff.demo.Repository.NotificationRepository;
import com.dfoff.demo.Util.CharactersUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.dfoff.demo.Service.BoardServiceTest.charactersUtilMockedStatic;
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
    @DisplayName("댓글 단건 조회 성공")
    void findBoardCommentByIdTest() {
        //given

        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(Optional.ofNullable(createBoardComment0()));

        //when
        BoardComment.BoardCommentResponse dto = sut.findBoardCommentById(1L);
        charactersUtilMockedStatic.when(()-> CharactersUtil.timesAgo(any())).thenReturn("1분전");

        //then
        then(boardCommentRepository).should().findBoardCommentById(any(Long.class));
    }

    @Test
    @DisplayName("댓글 리스트 조회 성공")
    void findBoardCommentByBoardIdTest() {
        //given
        given(boardCommentRepository.findBoardCommentByBoardId(any(Long.class))).willReturn(List.of());

        //when
        sut.findBoardCommentByBoardId(1L);

        //then
        then(boardCommentRepository).should().findBoardCommentByBoardId(any(Long.class));
    }

    @Test
    @DisplayName("댓글 등록 성공")
    void createBoardCommentTest() {
        //given
        given(boardCommentRepository.save(any(BoardComment.class))).willReturn(createBoardComment0());

        //when
        sut.createBoardComment(createBoardCommentRequestDto(), UserAccount.UserAccountDto.from(createUserAccount()),createBoardDto());

        //then
        then(boardCommentRepository).should().save(any(BoardComment.class));
    }

    @Test
    @DisplayName("댓글 등록 실패 - 댓글 내용이 없을때 ")
    void createBoardCommentExceptionTest() {
        //given

        //when
        Throwable throwable = catchThrowable(()-> sut.createBoardComment(createBoardCommentRequestExceptionDto(), UserAccount.UserAccountDto.from(createUserAccount()),null));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("자식댓글 조회 성공")
    void findBoardCommentsByParentIdTest() {
        //given
        given(boardCommentRepository.findBoardCommentByParentCommentId(any(Long.class),any(Long.class))).willReturn(List.of());

        //when
        sut.findBoardCommentsByParentId(any(Long.class),any(Long.class));

        //then
        then(boardCommentRepository).should().findBoardCommentByParentCommentId(any(Long.class),any(Long.class));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteBoardCommentTest() {
        //given
        //when
        sut.deleteBoardComment(anyLong());

        //then
        then(boardCommentRepository).should().deleteById(any(Long.class));
    }



    @Test
    @DisplayName("댓글 수정 실패 - 댓글을 수정할 권한이 없습니다")
    void updateBoardCommentExceptionTest() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(Optional.ofNullable(createBoardComment0()));
        //when
        Throwable throwable=catchThrowable(()->sut.updateBoardComment(anyLong(),createBoardCommentRequestDto(),null ));

        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("댓글 좋아요 성공")
    void updateBoardCommentLikeTest() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(Optional.ofNullable(createBoardComment0()));

        //when
        sut.updateBoardCommentLike(eq(1L));

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }

    @Test
    @DisplayName("댓글 좋아요 취소 성공")
    void updateBoardCommentDisLikeTest() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(Optional.ofNullable(createBoardComment0()));

        //when
        sut.updateBoardCommentDisLike(eq(1L));

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }

    @Test
    @DisplayName("대댓글 작성 성공")
    void createChildrenCommentTest() {
        //given
        given(boardCommentRepository.findBoardCommentById(anyLong())).willReturn(Optional.ofNullable(createBoardComment0()));

        //when
        sut.createChildrenComment(eq(1L), createBoardCommentChildrenRequestDto(), UserAccount.UserAccountDto.from(createUserAccount()),createBoardDto());

        //then
        then(boardCommentRepository).should().findBoardCommentById(anyLong());
    }




    @Test
    @DisplayName("게시글 아이디로 베댓 조회 성공")
    void findBestBoardCommentByBoardIdTest() {
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
                .boardId(1L)
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
}