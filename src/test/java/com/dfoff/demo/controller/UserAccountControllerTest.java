package com.dfoff.demo.controller;

import com.dfoff.demo.domain.Adventure;
import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.repository.CharacterEntityRepository;
import com.dfoff.demo.repository.SaveFileRepository;
import com.dfoff.demo.repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.securityconfig.SecurityConfig;
import com.dfoff.demo.securityconfig.SecurityService;
import com.dfoff.demo.service.CharacterService;
import com.dfoff.demo.service.SaveFileService;
import com.dfoff.demo.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("View 컨트롤러 - 인증")
@AutoConfigureMockMvc
@SpringBootTest
@Import(SecurityConfig.class)
class UserAccountControllerTest {

    private final ObjectMapper mapper;

    private final MockMvc mvc;
    @Mock
    private final UserAccountService userAccountService;

    @Mock
    private final SecurityService securityService;

    @Mock
    private final SaveFileService saveFileService;

    @Mock
            private final SaveFileRepository saveFileRepository;

    @Mock
            private final CharacterService characterService;
    @Autowired
    private CharacterEntityRepository characterEntityRepository;
    @Autowired
    private UserAccountCharacterMapperRepository userAccountCharacterMapperRepository;


    UserAccountControllerTest(@Autowired ObjectMapper mapper, @Autowired MockMvc mvc, @Autowired UserAccountService userAccountService, @Autowired SecurityService securityService, @Autowired SaveFileService saveFileService, @Autowired SaveFileRepository saveFileRepository,@Autowired CharacterService characterService) {
        this.mapper = mapper;
        this.mvc = mvc;
        this.userAccountService = userAccountService;
        this.securityService = securityService;
        this.saveFileService = saveFileService;
        this.saveFileRepository = saveFileRepository;
        this.characterService = characterService;
    }



    @WithUserDetails("test")
    void setUp() throws Exception {
        Adventure.UserAdventureRequest userAdventureRequest = Adventure.UserAdventureRequest
                .builder()
                .randomString("소라")
                .randomJobName("마법사(여)")
                .adventureName("타치바나소라")
                .representCharacterId("0695392fe27139764fac5856796375c9")
                .serverId("cain")
                .build();
       mvc.perform(post("/users/adventure").content(mapper.writeValueAsString(userAdventureRequest)).contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("[view] [POST] 아이디 중복검사")
    void givenUserId_whenValidateUserId_thenGetsResult() throws Exception {
        mvc.perform(get("/users/check?username=test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 닉네임 중복검사")
    void givenNickname_whenValidateNickname_thenGetsResult() throws Exception {

        mvc.perform(get("/users/check?nickname=test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 이메일 중복검사")
    void givenEmail_whenValidateEmail_thenGetsResult() throws Exception {

        mvc.perform(get("/users/check?email=test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] /api/user - 회원가입 시도")
    void createUserAccountTest() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(UserAccount.UserAccountSignUpRequest.builder()
                                .userId("testr21r")
                                .password("Tjrgus97!@")
                                .passwordCheck("Tjrgus97!@")
                                .nickname("test512512")
                                .email("test521@emwqebqnw.com")
                                .build())))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test")
    void getUserLogTest() throws Exception {

        mvc.perform(get("/users/logs/").param("type","board").param("sortBy","")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    @WithUserDetails ("tes")
    @Disabled
    void changeProfileIconTest() throws Exception {
        mvc.perform(put("/users?profileIcon=icon_char_0.png"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("[view] [POST] /api/user/login - 로그인 시도")
    @Disabled
    void givenSignupDto_whenLogin_thenLogin() throws Exception {
        UserAccount.LoginDto loginDto = UserAccount.LoginDto.builder()
                .username("test")
                .password("123")
                .build();
        mvc.perform(post("/users/login").param("username","test").param("password","123"))
                .andExpect(status().isOk());
    }
    @Test
    @WithUserDetails ("test")
    @Disabled
    void  searchCharTest() throws Exception {
        mvc.perform(get("/users/characters/?serverId=all&characterName=테스트"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }





    @Test
    @WithUserDetails ("test")
    @Disabled
    void addCharacterTest() throws Exception {
        mvc.perform(post("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void addCharacterExceptionTest() throws Exception {

        mvc.perform(post("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithUserDetails ("test")
    @Disabled
    void deleteCharacterTest() throws Exception {
        mvc.perform(delete("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }


    @Test
    @WithUserDetails ("test")
    @Disabled
    void updateProfileTest() throws Exception {
        mvc.perform(put("/users?nickname=테스트&email=테스트"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfileExceptionTest() throws Exception {
        mvc.perform(put("/users?nickname=테스트&email=테스트"))
                .andExpect(status().isUnauthorized());
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

    @Test
    @WithUserDetails ("test")
    @Disabled
    void createUserAdventure() throws Exception {
        Adventure.UserAdventureRequest userAdventureRequest = Adventure.UserAdventureRequest
                .builder()
                .randomString("소라")
                .randomJobName("마법사(여)")
                .adventureName("타치바나소라")
                .representCharacterId("0695392fe27139764fac5856796375c9")
                .serverId("cain")
                .build();
        mvc.perform(post("/users/adventure").content(mapper.writeValueAsString(userAdventureRequest)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails ("test")
    @Disabled
    void getRandomJobNameAndString() throws Exception {
        mvc.perform(get("/users/adventure/validate")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getRandomJobNameAndStringExceptionTest() throws Exception {
        mvc.perform(get("/users/adventure/validate")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails ("test")
    @Disabled
    void refreshUserAdventure() throws Exception {
        mvc.perform(get("/users/adventure/refresh")).andExpect(status().isOk());
    }

    @Test
    void refreshUserAdventureExceptionTest() throws Exception {
        mvc.perform(get("/users/adventure/refresh")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("test")
    @Disabled
    void searchCharacterForUserAccount() throws Exception {
     mvc.perform(get("/users/characters/").param("serverId","cain").param("characterName","소라")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchCharacterForUserAccountExceptionTest() throws Exception {
        mvc.perform(get("/users/characters/").param("serverId","cain").param("characterName","소라")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("test")
    @Disabled
    void getLog() throws Exception {
        mvc.perform(get("/users/logs/").param("type","board").param("sortBy","like")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Disabled
    void getLogExceptionTest() throws Exception {
        mvc.perform(get("/users/logs/").param("type","board").param("sortBy","like")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("test")
    @Disabled
    void refreshUserAdventureTest() throws Exception {
        mvc.perform(get("/users/adventure/refresh")).andExpect(status().isOk());
    }
    @Test
    @WithUserDetails("test")
    @Disabled
    void deleteByAccountByUserIdTest() throws Exception {
        mvc.perform(delete("/users")).andExpect(status().isOk());
    }

    @Test
    void deleteByAccountByUserIdExceptionTest() throws Exception {
        mvc.perform(delete("/users")).andExpect(status().isUnauthorized());
    }
    @Test
    @WithUserDetails("tes")
    @Disabled
    void deleteMyAdventureExceptionTest() throws Exception {
        mvc.perform(delete("/users/adventure")).andExpect(status().isBadRequest());
    }
}