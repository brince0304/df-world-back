package com.dfoff.demo.controller;

import com.dfoff.demo.securityconfig.SecurityConfig;
import com.dfoff.demo.service.CharacterService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @Disabled
    void searchCharacter() throws Exception {
        mvc.perform(get("/characters/").param("characterName","테스트").param("serverId","all"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @Disabled
    void getCharacterDetailsTest() throws Exception {
        mvc.perform((get("/characters/detail").param("serverId", "cain")).param("characterId","0695392fe27139764fac5856796375c9"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    void getCharacterDetailsExceptionTest() throws Exception {
        mvc.perform((get("/characters/detail/").param("serverId", "cain")).param("characterId","0695392fe2"))
                .andExpect(status().isNotFound());
    }
}