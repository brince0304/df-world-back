package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Domain.UserAdventure;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.SaveFileRepository;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.SecurityConfig.SecurityService;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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


    void setUp() throws Exception {
        UserAdventure.UserAdventureRequest userAdventureRequest = UserAdventure.UserAdventureRequest
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
    @WithUserDetails ("test")
    void changeProfileIconTest() throws Exception {
        mvc.perform(put("/users?profileIcon=icon_char_0.png"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("[view] [POST] /api/user/login - 로그인 시도")
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
    void  searchCharTest() throws Exception {
        mvc.perform(get("/users/characters/?serverId=all&characterName=테스트"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }





    @Test
    @WithUserDetails ("test")
    void addCharacterTest() throws Exception {
        mvc.perform(post("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void addCharacterExceptionTest() throws Exception {

        mvc.perform(post("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithUserDetails ("test")
    void deleteCharacterTest() throws Exception {
        mvc.perform(delete("/users/characters?serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }


    @Test
    @WithUserDetails ("test")
    void updateProfileTest() throws Exception {
        mvc.perform(put("/users?nickname=테스트&email=테스트"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfileExceptionTest() throws Exception {
        mvc.perform(put("/users?nickname=테스트&email=테스트"))
                .andExpect(status().isForbidden());
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
    void createUserAdventure() throws Exception {
        UserAdventure.UserAdventureRequest userAdventureRequest = UserAdventure.UserAdventureRequest
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
    void getRandomJobNameAndString() throws Exception {
        mvc.perform(get("/users/adventure/validate")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getRandomJobNameAndStringExceptionTest() throws Exception {
        mvc.perform(get("/users/adventure/validate")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails ("test")
    void refreshUserAdventure() throws Exception {
        mvc.perform(get("/users/adventure/refresh")).andExpect(status().isOk());
    }

    @Test
    void refreshUserAdventureExceptionTest() throws Exception {
        mvc.perform(get("/users/adventure/refresh")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("test")
    void searchCharacterForUserAccount() throws Exception {
     mvc.perform(get("/users/characters/").param("serverId","cain").param("characterName","소라")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchCharacterForUserAccountExceptionTest() throws Exception {
        mvc.perform(get("/users/characters/").param("serverId","cain").param("characterName","소라")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("test")
    void getLog() throws Exception {
        mvc.perform(get("/users/logs/").param("type","board").param("sortBy","like")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getLogExceptionTest() throws Exception {
        mvc.perform(get("/users/logs/").param("type","board").param("sortBy","like")).andExpect(status().isForbidden());
    }
}