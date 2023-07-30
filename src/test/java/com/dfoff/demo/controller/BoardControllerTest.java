package com.dfoff.demo.controller;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.BoardComment;
import com.dfoff.demo.domain.enums.BoardType;
import com.dfoff.demo.securityconfig.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

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
        mvc.perform(get("/boards/"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    void getBoardListTestDetail() throws Exception {
        mvc.perform(get("/boards/?boardType=NOTICE&searchType=title&keyword=te"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("boardType")).andExpect(model().attributeExists("articles"));
    }

    @Test
    @WithUserDetails("test")
    void deleteBoardTest() throws Exception {
        mvc.perform(delete("/boards").param("boardId", "1")).andExpect(status().isOk());
    }

    @Test
    void deleteBoardExceptionTest() throws Exception {
        mvc.perform(delete("/boards").param("boardId", "5")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("tes")
    void deleteBoardIllegalAccessTest() throws Exception {
        mvc.perform(delete("/boards").param("boardId", "5")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("test")
    void deleteBoardNotFoundExceptionTest() throws Exception {
        mvc.perform(delete("/boards").param("boardId", "50")).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("test")
    void updateBoardTest() throws Exception {
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .id(10L)
                .boardTitle("test")
                .boardContent("test4214124214214121212412")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag(new ArrayList<>())
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();


        mvc.perform(put("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("tes")
    void updateBoardIllegalAccessTest() throws Exception {
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .id(10L)
                .boardTitle("test")
                .boardContent("test4214124214214121212412")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag(new ArrayList<>())
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();


        mvc.perform(put("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails("test")
    void createBoardTest() throws Exception {
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .boardTitle("test")
                .boardContent("test4124124214212114212")
                .boardType(BoardType.FREE)
                .serverId("bakal")
                .hashtag(new ArrayList<>())
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();

        mvc.perform(post("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createBoardExceptionTest() throws Exception {
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .boardTitle("test")
                .boardContent("test")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .hashtag(new ArrayList<>())
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();

        mvc.perform(post("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void increaseViewCountTest() throws Exception {
        mvc.perform(get("/boards/3")).andExpect(status().isOk());

    }

    @Test
    void increaseLikeCountTest() throws Exception {
        mvc.perform(post("/boards/like").param("boardId","3")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateBoardExceptionTest() throws Exception {
        Board.BoardRequest boardRequest = Board.BoardRequest.builder()
                .id(1L)
                .boardTitle("test")
                .boardContent("testftguyguygfuyfviuy")
                .boardType(BoardType.NOTICE)
                .serverId("bakal")
                .characterId("13b99a237d7e1b9369fdcf2ca186b845")
                .boardFiles("")
                .build();
        mvc.perform(put("/boards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardRequest)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void getBoardComments() throws Exception {
        mvc.perform(get("/comments/").param("boardId", "5")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void likeComment() throws Exception {
        mvc.perform(post("/comments/like-comment").param("commentId", "5").param("boardId", "5")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
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
        mvc.perform(delete("/comments").param("commentId", "4")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

    }

    @Test
    @WithUserDetails("test")
    void updateBoardComment() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(3L)
                .commentId(3L)
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
                .andExpect(status().isUnauthorized());
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
        mvc.perform(delete("/comments").param("commentId", "5")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("tes")
    void deleteBoardCommentIllegalAccess() throws Exception {
        mvc.perform(delete("/comments").param("commentId", "5")).andExpect(status().isForbidden());
    }

    @Test
    void updateBoardCommentUnauthorized() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(958L)
                .commentId(1L)
                .commentContent("test22")
                .build();
        mvc.perform(put("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest))).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("tes")
    void updateBoardCommentIllegalAccess() throws Exception {
        BoardComment.BoardCommentRequest boardCommentRequest = BoardComment.BoardCommentRequest.builder()
                .boardId(5L)
                .commentId(5L)
                .commentContent("test22")
                .build();
        mvc.perform(put("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(boardCommentRequest))).andExpect(status().isForbidden());
    }
}