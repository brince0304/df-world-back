package com.dfoff.demo.controller;

import com.dfoff.demo.annotation.Auth;
import com.dfoff.demo.annotation.BindingErrorCheck;
import com.dfoff.demo.domain.Adventure;
import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.jwt.TokenDto;
import com.dfoff.demo.jwt.TokenProvider;
import com.dfoff.demo.securityconfig.SecurityService;
import com.dfoff.demo.service.*;
import com.dfoff.demo.utils.Bcrypt;
import com.dfoff.demo.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final SaveFileService saveFileService;

    private final CharacterService characterService;

    private final NotificationService notificationService;
    private final RedisService redisService;

    private final Bcrypt bcrypt;

    private final TokenProvider tokenProvider;


    private final SecurityService securityService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @GetMapping("/users/check")
    public boolean checkExist(@RequestParam(required = false) String email,
                             @RequestParam(required = false) String nickname,
                             @RequestParam(required = false) String username) {
        if (email != null) {
            return !userAccountService.existsByEmail(email);
        } else if (nickname != null) {
            return !userAccountService.existsByNickname(nickname);
        } else if (username != null) {
            return !userAccountService.existsByUserId(username);
        }
        return false;
    }

    @PostMapping("/users/adventure")
    public ResponseEntity<?> createUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                                 @RequestBody Adventure.UserAdventureRequest request) throws InterruptedException {
        if (characterService.checkCharacterAdventure(request)) {
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(request.getServerId(), request.getRepresentCharacterId());
            userAccountService.saveUserAdventure(request, UserAccount.UserAccountDto.from(principal), character, characterService.getCharactersByAdventureName(request.getAdventureName()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("다시 시도해주세요.", HttpStatus.BAD_REQUEST);
    }

    @Auth
    @GetMapping("/users/adventure/validate")
    public ResponseEntity<?> getRandomJobNameAndString(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        HashMap<String, String> map = new HashMap<>();
        map.put("randomJobName", characterService.getRandomJobName());
        map.put("randomString", characterService.getRandomString());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Auth
    @GetMapping("/users/adventure/refresh")
    public ResponseEntity<?> refreshUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto principal) throws InterruptedException {
        String adventureName = userAccountService.getUserAdventureNameByUserId(principal.getUsername());
        Adventure.AdventureDto dto = userAccountService.addAllCharactersToUserAdventure(UserAccount.UserAccountDto.from(principal), characterService.getCharactersByAdventureName(adventureName));
        for (CharacterEntity.CharacterEntityDto character : dto.getCharacters()) {
            characterService.getCharacterAbility(character);
        }
        userAccountService.refreshUserAdventure(UserAccount.UserAccountDto.from(principal));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Auth
    @GetMapping("/users/characters/")
    public ResponseEntity<?> searchCharacterForUserAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestParam String serverId,
                                                           @RequestParam String characterName,
                                                           @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable) throws InterruptedException {
        if (serverId.equals("adventure")) {
            return new ResponseEntity<>(characterService.getCharacterByAdventureName(characterName, pageable), HttpStatus.OK);
        }
        List<CharacterEntity.CharacterEntityDto> list = new ArrayList<>();
        List<CharacterEntity.CharacterEntityDto> dtos = characterService.getCharacterDtos(serverId, characterName).join();
        return getCharacterResponse(list, dtos, characterService);
    }


    static ResponseEntity<?> getCharacterResponse(List<CharacterEntity.CharacterEntityDto> list, List<CharacterEntity.CharacterEntityDto> dtos, CharacterService characterService) throws InterruptedException {
        for (CharacterEntity.CharacterEntityDto dto : dtos.subList(0, Math.min(dtos.size(), 15))) {
            if (dto.getLevel() >= 110) {
                list.add(characterService.getCharacterAbility(dto));
            } else {
                list.add(dto);
            }
        }
        int size = Math.min(list.size(), 15);
        return new ResponseEntity<>(list.stream().map(CharacterEntity.CharacterEntityResponse::from).collect(Collectors.toList()).subList(0, size), HttpStatus.OK);
    }

    @Auth
    @PostMapping("/users/adventure/character-icon")
    public ResponseEntity<?> changeProfileIconToCharacterImg(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestParam String serverId, @RequestParam String characterId) throws InterruptedException {
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(serverId, characterId);
        userAccountService.changeProfileIconByAdventureCharacter(character, principal.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @BindingErrorCheck
    @PostMapping("/users")
    public ResponseEntity<?> createAccount(@RequestBody @Valid UserAccount.UserAccountSignUpRequest request, BindingResult bindingResult) {
        request.setPassword(bcrypt.encode(request.getPassword()));
        userAccountService.createAccount(request, saveFileService.getFileByFileName("icon_char_0.png"));
        return new ResponseEntity<>(request.getUserId(), HttpStatus.OK);
    }

    @Auth
    @PostMapping("/users/characters")
    public ResponseEntity<?> addCharacterToUserAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                                       @RequestParam(required = false) String serverId,
                                                       @RequestParam(required = false) String characterId) throws InterruptedException {
        userAccountService.addCharacterToUserAccount(UserAccount.UserAccountDto.from(principal), characterService.getCharacterAbility(characterService.getCharacter(serverId, characterId)));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Auth
    @DeleteMapping("/users/characters")
    public ResponseEntity<?> deleteCharacterFromUserAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                                            @RequestParam(required = false) String serverId,
                                                            @RequestParam(required = false) String characterId
    ) throws InterruptedException {
        userAccountService.deleteCharacterFromUserAccount(UserAccount.UserAccountDto.from(principal), characterService.getCharacter(serverId, characterId));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Auth
    @GetMapping("/users/my-page")
    public ModelAndView getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        ModelAndView mav = new ModelAndView("/mypage/mypage");
        mav.addObject("user", userAccountService.getUserAccountById(principal.getUsername()));
        mav.addObject("uncheckedLogCount", notificationService.getUncheckedNotificationCount(principal.getUsername()));
        if (userAccountService.checkUserAdventureById(principal.getUsername())) {
            mav.addObject("adventure", userAccountService.getUserAdventureById(principal.getUsername()));
        }
        return mav;
    }

    @Auth
    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal, HttpServletRequest request) {
        userAccountService.deleteUserAccountById(principal.getUsername());
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Auth
    @DeleteMapping("/users/adventure")
    public ResponseEntity<?> deleteAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        userAccountService.deleteUserAdventureFromUserAccount(principal.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Auth
    @GetMapping("/users/logs/")
    public ResponseEntity<?> getLog(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                    @RequestParam String type,
                                    @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable,
                                    @RequestParam(required = false) String sortBy) {
        if (type.equals("board")) {
            return new ResponseEntity<>(userAccountService.getUserBoardLogs(UserAccount.UserAccountDto.from(principal),pageable,sortBy), HttpStatus.OK);
        }
        if (type.equals("comment")) {
            return new ResponseEntity<>(userAccountService.getUserCommentLogs(UserAccount.UserAccountDto.from(principal),pageable,sortBy), HttpStatus.OK);
        }
        if (type.equals("log")) {
            return new ResponseEntity<>(notificationService.getUserNotifications(principal.getUsername(), pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Auth
    @PutMapping("/users")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                           @RequestParam(required = false) String nickname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String profileIcon) {
        if (password != null && !password.equals("")) {
            userAccountService.changePassword(UserAccount.UserAccountDto.from(principal), bcrypt.encode(password));
            return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);
        }
        if (profileIcon != null && !profileIcon.equals("")) {
            userAccountService.changeProfileIcon(UserAccount.UserAccountDto.from(principal), saveFileService.getFileByFileName(profileIcon));
            return new ResponseEntity<>("프로필이 변경되었습니다.", HttpStatus.OK);
        }
        if (nickname != null && !nickname.equals("")) {
            userAccountService.changeNickname(UserAccount.UserAccountDto.from(principal), nickname);
            return new ResponseEntity<>("닉네임이 변경되었습니다.", HttpStatus.OK);
        }


        if (email != null && !email.equals("")) {
            userAccountService.changeEmail(UserAccount.UserAccountDto.from(principal), email);
            return new ResponseEntity<>("이메일이 변경되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> getAccessToken(@RequestBody UserAccount.UserLoginRequst request,
                                            HttpServletResponse res) throws Exception {
     try{
         TokenDto token =userAccountService.getToken(request.getUsername(), request.getPassword());
         Map<String,Object> map = new HashMap<>();
         map.put("CURRENT_USER",userAccountService.getLoginResponse(request.getUsername()));
         redisService.set(TokenProvider.REFRESH_TOKEN_NAME+request.getUsername(),token.getRefreshToken(),TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(CookieUtil.createAccessTokenCookie(token.getAccessToken(), TokenProvider.TOKEN_VALIDATION_SECOND));
            res.addCookie(CookieUtil.createRefreshTokenCookie(token.getRefreshToken(), TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND));
            return new ResponseEntity<>(map,HttpStatus.OK);
     }catch(BadCredentialsException e){
            return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
     }
    }

    @GetMapping("/users/logout")
    @Auth
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserAccount.PrincipalDto principal, HttpServletRequest request, HttpServletResponse response) {
        Cookie accessToken = CookieUtil.getCookie(TokenProvider.ACCESS_TOKEN_NAME, request);
        Cookie refreshToken = CookieUtil.getCookie(TokenProvider.REFRESH_TOKEN_NAME, request);
        if(accessToken!=null && refreshToken!=null){
            redisService.delete(TokenProvider.REFRESH_TOKEN_NAME+principal.getUsername());
            redisService.setBlackList(principal.getUsername(),accessToken.getValue(), TokenProvider.TOKEN_VALIDATION_SECOND);
            response.addCookie(CookieUtil.createAccessTokenCookie("", 0));
            response.addCookie(CookieUtil.createRefreshTokenCookie("", 0));
            return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping ("/users/details")
    public ResponseEntity<?> getDetails(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        if(principal!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("notification",notificationService.getUncheckedNotificationCount(principal.getUsername()));
            map.put("user",userAccountService.getLoginResponse(principal.getUsername()));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping("/users/reissue")
    @Auth
    public ResponseEntity<?> validateToken(HttpServletRequest req , HttpServletResponse res ,@AuthenticationPrincipal UserAccount.PrincipalDto principaldto) throws Exception {
        Cookie refreshTokenCookie = CookieUtil.getCookie(TokenProvider.REFRESH_TOKEN_NAME,req);
        if(refreshTokenCookie !=null){
            String refreshToken = refreshTokenCookie.getValue();
            if(redisService.get(refreshToken)!=null){
                String username = redisService.get(refreshToken);
                if(username.equals(principaldto.getUsername())) {
                    String accessToken = tokenProvider.generateToken(principaldto);
                    return new ResponseEntity<>(accessToken, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}



