package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.SecurityConfig.SecurityConfig;
import com.dfoff.demo.SecurityConfig.SecurityService;
import com.dfoff.demo.Service.UserAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    UserAccountControllerTest(@Autowired ObjectMapper mapper, @Autowired MockMvc mvc, @Autowired UserAccountService userAccountService,@Autowired SecurityService securityService) {
        this.mapper = mapper;
        this.mvc = mvc;
        this.userAccountService = userAccountService;
        this.securityService = securityService;
    }
    @BeforeEach
    void setUp() throws Exception {
        mvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(UserAccount.UserAccountSignUpRequest.builder()
                .userId("test")
                .password("test")
                .passwordCheck("test")
                .build())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 아이디 중복검사")
    void givenUserId_whenValidateUserId_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByUserId(any())).willReturn(true);
        Map<String,String> map = new HashMap<>();
        map.put("username","test");
        //when
        //then
        mvc.perform(post("/api/user/id").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 닉네임 중복검사")
    void givenNickname_whenValidateNickname_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByNickname(any())).willReturn(true);
        Map<String,String> map = new HashMap<>();
        map.put("nickname","test");
        //when
        //then
        mvc.perform(post("/api/user/nickname").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[view] [POST] 이메일 중복검사")
    void givenEmail_whenValidateEmail_thenGetsResult() throws Exception {
        //given
        given(userAccountService.existsByEmail(any())).willReturn(true);
        Map<String,String> map = new HashMap<>();
        map.put("email","test");
        //when
        //then
        mvc.perform(post("/api/user/email").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)))
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
    @DisplayName("[view] [POST] /api/user/login - 로그인 시도")
    void givenSignupDto_whenLogin_thenLogin() throws Exception {
        //given
        UserAccount.LoginDto loginDto = UserAccount.LoginDto.builder()
                .username("test")
                .password("test")
                .build();
        given(securityService.loadUserByUsername(any())).willReturn(UserAccount.PrincipalDto.builder()
                .username("test")
                .password("test")
                .email("test")
                .nickname("test")
                        .authorities(null)
                .build());

        //when&then
        mvc.perform(post("/api/user/login").content(mapper.writeValueAsString(loginDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}