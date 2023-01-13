package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.Service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 인증")
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
class BoardControllerTest {

    private final MockMvc mvc;
    @Mock
    private final BoardService boardService;

    BoardControllerTest(@Autowired MockMvc mvc,@Autowired BoardService boardService) {
        this.mvc = mvc;
        this.boardService = boardService;
    }

    @Test
    void getBoardListTest() throws Exception {
        //when&then
        mvc.perform(get("/board.df"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
    @Test
    void getBoardListTestDetail() throws Exception {
        //when&then
        mvc.perform(get("/board.df?boardType=NOTICE&searchType=title&keyword=te"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("boardType")).andExpect(model().attributeExists("articles"));
    }
}