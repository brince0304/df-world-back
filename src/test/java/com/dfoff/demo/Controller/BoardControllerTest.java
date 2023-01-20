package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.Service.BoardService;
import com.dfoff.demo.Service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 인증")
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
class BoardControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MockMvc mvc;


    private final ObjectMapper objectMapper;


    BoardControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;

        this.objectMapper = objectMapper;
    }

    @Test
    void getBoardListTest() throws Exception {
        //when&then
        mvc.perform(get("/boards/"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    void getBoardListTestDetail() throws Exception {
        //when&then
        mvc.perform(get("/boards/?boardType=NOTICE&searchType=title&keyword=te"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("boardType")).andExpect(model().attributeExists("articles"));
    }

    @Test
    @WithUserDetails("test")
    void deleteBoardTest() throws Exception {
        //when&then
        mvc.perform(delete("/boards").param("id", "1")).andExpect(status().isOk());
    }

    @Test
    void deleteBoardExceptionTest() throws Exception {
        //when&then
        mvc.perform(delete("/boards").param("id", "2")).andExpect(status().isForbidden());
    }

    @Test
    void deleteBoardNotFoundExceptionTest() throws Exception {
        //when&then
        mvc.perform(delete("/boards").param("id", "1")).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("test")
    void updateBoardTest() throws Exception {
        //given
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .id(2L)
                .boardTitle("test")
                .boardContent("test4214124214214121212412")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag("#test")
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();


        //when&then
        mvc.perform(put("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("test")
    void createBoardTest() throws Exception {
        //given
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .boardTitle("test")
                .boardContent("test4124124214212114212")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag("#test")
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();

        //when&then
        mvc.perform(post("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createBoardExceptionTest() throws Exception {
        //given
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag("#test")
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();

        //when&then
        mvc.perform(post("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void increaseViewCountTest() throws Exception {
        //when&then
        mvc.perform(get("/boards/like-board")).andExpect(status().isOk());

    }

    @Test
    void increaseLikeCountTest() throws Exception {
        //when&then
        mvc.perform(post("/boards/like-board")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateBoardExceptionTest() throws Exception {
        //given
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .id(2L)
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();
        //when&then
        mvc.perform(put("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isForbidden());
    }


    @Test
    void getBoardComments() throws Exception {
        mvc.perform(get("/comments/").param("boardId", "5")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void likeComment() throws Exception {
        mvc.perform(post("/comments/like-comment").param("commentId", "1").param("boardId", "958")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails("test")
    void createBoardComment() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(1L)
                .commentContent("test")
                .build();
        mvc.perform(post("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    @WithUserDetails("test")
    void deleteBoardComment() throws Exception {
        mvc.perform(delete("/comments").param("commentId", "1")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

    }

    @Test
    @WithUserDetails("test")
    void updateBoardComment() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(958L)
                .commentId(2L)
                .commentContent("test22")
                .build();
        mvc.perform(put("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest))).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void createBoardCommentUnauthorized() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(1L)
                .commentContent("test")
                .build();
        mvc.perform(post("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("test")
    void deleteBoardCommentNotFound() throws Exception {
        mvc.perform(delete("/comments").param("commentId", "5918")).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("test")
    void updateBoardCommentNotFound() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(958L)
                .commentId(42141L)
                .commentContent("test22")
                .build();
        mvc.perform(put("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest))).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("test")
    void createBoardCommentNotFound() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(41241L)
                .commentContent("test")
                .build();
        mvc.perform(post("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBoardCommentUnauthorized() throws Exception {
        mvc.perform(delete("/comments").param("commentId", "1")).andExpect(status().isUnauthorized());
    }

    @Test
    void updateBoardCommentUnauthorized() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(958L)
                .commentId(1L)
                .commentContent("test22")
                .build();
        mvc.perform(put("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest))).andExpect(status().isForbidden());
    }
}