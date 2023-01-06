package com.dfoff.demo.Controller;

import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.Service.CharacterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 인증")
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
class CharacterControllerTest {
    private final MockMvc mvc;
    @Mock
    private final CharacterService characterService;

    CharacterControllerTest(@Autowired MockMvc mvc, @Autowired CharacterService characterService) {
        this.mvc = mvc;
        this.characterService = characterService;
    }

    @Test
    void getServerAndJobListForUpdate() throws Exception {
        //when&then
        mvc.perform(get("/df/update.df"))
                .andExpect(status().is3xxRedirection());

    }
}