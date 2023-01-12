package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.SaveFileRepository;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.SecurityConfig.SecurityService;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setUp() throws Exception {
        saveFileService.saveFile(SaveFile.SaveFileDTO.builder()
                .fileName("icon_char_0.png").filePath("icon_char_0.png").build());
        userAccountService.createAccount(UserAccount.UserAccountDto.builder()
                .userId("test2")
                .password("test2")
                .nickname("test2")
                .email("test2").profileIcon(SaveFile.SaveFileDTO.builder().fileName("test2").filePath("test2").build()).build());

    }

    @Test
    @DisplayName("[view] [POST] 아이디 중복검사")
    void givenUserId_whenValidateUserId_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByUserId(any())).willReturn(true);
        //when
        //then
        mvc.perform(get("/api/user/validate?username=test2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 닉네임 중복검사")
    void givenNickname_whenValidateNickname_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByNickname(any())).willReturn(true);
        //when
        //then
        mvc.perform(get("/api/user/validate?nickname=test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 이메일 중복검사")
    void givenEmail_whenValidateEmail_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByEmail(any())).willReturn(true);
        //when
        //then
        mvc.perform(get("/api/user/validate?email=test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] /api/user - 회원가입 시도")
    void givenUserDetails_whenCreatingUserAccount_thenCreatesUserAccount() throws Exception {
        //given
        given(userAccountService.createAccount(any())).willReturn(true);

        //when&then
        mvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new UserAccount.UserAccountSignUpRequest("test1234", "test", "test", "test1234", "test1234@email.com"))))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails ("test")
    void givenUserDetails_whenChangeProfileIcon_thenChangeProfileIcon() throws Exception {
        given(securityService.loadUserByUsername(any())).willReturn(UserAccount.PrincipalDto.builder()
                .username("test2")
                .password("test2")
                .email("test2")
                .nickname("test2").profileIcon(SaveFile.SaveFileDTO.builder().fileName("test2").filePath("test2").build())
                .build());
        given(saveFileRepository.findByFileName(any())).willReturn(SaveFile.builder()
                        .id(1L)
                .fileName("icon_char_01.png").filePath("icon_char_01.png").build());
        //perform
        mvc.perform(put("/api/user/profile.df?profileIcon=icon_char_01.png"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("[view] [POST] /api/user/login - 로그인 시도")
    void givenSignupDto_whenLogin_thenLogin() throws Exception {
        //given
        UserAccount.LoginDto loginDto = UserAccount.LoginDto.builder()
                .username("test")
                .password("123")
                .build();
        given(securityService.loadUserByUsername(any())).willReturn(UserAccount.PrincipalDto.builder()
                .username("test")
                .password("123")
                .email("test2")
                .nickname("test2")
                .authorities(null)
                .build());

        //when&then
        mvc.perform(post("/api/user/login").content(mapper.writeValueAsString(loginDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithUserDetails ("test")
    void  searchCharTest() throws Exception {
        //when&then
        mvc.perform(get("/api/user/searchChar.df?serverId=all&characterName=테스트"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    @WithUserDetails ("test")
    void addCharacterTest() throws Exception {
        //when&then
        mvc.perform(post("/api/user/char.df?request=add&serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void addCharacterExceptionTest() throws Exception {
        //when&then
        mvc.perform(post("/api/user/char.df?request=add&serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
    @Test
    @WithUserDetails ("test")
    void deleteCharacterTest() throws Exception {
        //when&then
        mvc.perform(post("/api/user/char.df?request=delete&serverId=cain&characterId=77dae44a87261743386852bb3979c03a"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }


    @Test
    @WithUserDetails ("test")
    void updateProfileTest() throws Exception {
        //when&then
        mvc.perform(put("/api/user/profile.df?nickname=테스트&email=테스트"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProfileExceptionTest() throws Exception {
        //when&then
        mvc.perform(put("/api/user/profile.df?nickname=테스트&email=테스트"))
                .andExpect(status().isBadRequest());
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

}