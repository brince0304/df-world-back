package com.dfoff.demo.controller;

import com.dfoff.demo.annotation.Auth;
import com.dfoff.demo.annotation.BindingErrorCheck;
import com.dfoff.demo.domain.Adventure;
import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.jwt.TokenDto;
import com.dfoff.demo.jwt.TokenProvider;
import com.dfoff.demo.oauth.KakaoLoginParams;
import com.dfoff.demo.oauth.OAuthDto;
import com.dfoff.demo.service.*;
import com.dfoff.demo.utils.Bcrypt;
import com.dfoff.demo.utils.CookieUtil;
import com.dfoff.demo.utils.FileUtil;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final SaveFileService saveFileService;
    private final OAuthLoginService oAuthLoginService;
    private final CharacterService characterService;
    private final NotificationService notificationService;
    private final RedisService redisService;
    private final Bcrypt bcrypt;


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
        return new ResponseEntity<>(HttpStatus.OK);
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
    @GetMapping("/users/")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        HashMap<String,Object> modelMap = new HashMap<>();
        modelMap.put("userDetail", userAccountService.getUserAccountById(principal.getUsername()));
        if (userAccountService.checkUserAdventureById(principal.getUsername())) {
            modelMap.put("userAdventure", userAccountService.getUserAdventureById(principal.getUsername()));
        }
        return new ResponseEntity<>(modelMap, HttpStatus.OK);
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
    @GetMapping("/users/activities/")
    public ResponseEntity<?> getLog(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                    @RequestParam String type,
                                    @PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable,
                                    @RequestParam(required = false) String sortBy) {
        if (type.equals("board")) {
            return new ResponseEntity<>(userAccountService.getUserBoardLogs(UserAccount.UserAccountDto.from(principal),pageable,sortBy), HttpStatus.OK);
        }
        if (type.equals("comment")) {
            return new ResponseEntity<>(userAccountService.getUserCommentLogs(UserAccount.UserAccountDto.from(principal),pageable,sortBy), HttpStatus.OK);
        }
        if (type.equals("notification")) {
            return new ResponseEntity<>(notificationService.getUserNotifications(principal.getUsername(), pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Auth
    @PutMapping ("/users/avatar")
    public ResponseEntity<?> changeAvatar(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                          @RequestPart MultipartFile file) throws IOException {
        SaveFile.SaveFileDto dto = saveFileService.saveFile(FileUtil.getFileDtoFromMultiPartFile(file));
        SaveFile.SaveFileDto prevDto = userAccountService.changeProfileIcon(UserAccount.UserAccountDto.from(principal), dto);
        if(prevDto.id()>16){
            saveFileService.deleteFileByFileName(prevDto.fileName());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Auth
    @PutMapping("/users")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                            @RequestParam (required = false) String password,
                                            @RequestParam (required = false) String newPassword ,
                                            @RequestParam(required = false) String nickname,
                                            @RequestParam(required = false) String profileIcon,
                                            HttpServletResponse res) {
        if (nickname != null && !nickname.equals("")) {
            userAccountService.changeNickname(UserAccount.UserAccountDto.from(principal), nickname);
            return new ResponseEntity<>("닉네임이 변경되었습니다.", HttpStatus.OK);
        }
        if (password != null && !password.equals("") && newPassword != null && !newPassword.equals("")) {
            userAccountService.changePassword(UserAccount.UserAccountDto.from(principal),password,newPassword);
            return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);
        }
        if (profileIcon != null && !profileIcon.equals("")) {
            userAccountService.changeProfileIcon(UserAccount.UserAccountDto.from(principal), saveFileService.getFileByFileName(profileIcon));
            return new ResponseEntity<>("프로필 사진이 변경되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> getAccessToken(@RequestBody UserAccount.UserLoginRequst request,
                                            HttpServletResponse res) throws Exception {
     try{
         TokenDto token =userAccountService.getToken(request.getUsername(), request.getPassword());
         redisService.set(TokenProvider.REFRESH_TOKEN_NAME+request.getUsername(),token.getRefreshToken(),TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addHeader("Set-Cookie",CookieUtil.createAccessTokenCookie(token.getAccessToken(), TokenProvider.TOKEN_VALIDATION_SECOND).toString());
            res.addHeader("Set-Cookie",CookieUtil.createRefreshTokenCookie(token.getRefreshToken(), TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND).toString());
            return new ResponseEntity<>(userAccountService.getLoginResponse(request.getUsername()),HttpStatus.OK);
     }catch(BadCredentialsException e){
            return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
     }
    }

    @PostMapping("/users/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody KakaoLoginParams params, HttpServletResponse res) throws Exception {
            OAuthDto oauthDto = oAuthLoginService.login(params,saveFileService.getFileByFileName("icon_char_0.png"));
            UserAccount.UserAccountDto dto = oauthDto.getUserAccountDto();
            TokenDto token = oauthDto.getTokenDto();
            oAuthLoginService.authenticateUser(dto);
            redisService.set(TokenProvider.REFRESH_TOKEN_NAME+dto.userId(),token.getRefreshToken(),TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addHeader("Set-Cookie",CookieUtil.createAccessTokenCookie(token.getAccessToken(), TokenProvider.TOKEN_VALIDATION_SECOND).toString());
            res.addHeader("Set-Cookie",CookieUtil.createRefreshTokenCookie(token.getRefreshToken(), TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND).toString());
        return new ResponseEntity<>(userAccountService.getLoginResponse(dto.userId()),HttpStatus.OK);
    }


    @GetMapping("/users/logout")
    @Auth
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserAccount.PrincipalDto principal, HttpServletRequest request, HttpServletResponse response) {
        Cookie accessToken = CookieUtil.getCookie(TokenProvider.ACCESS_TOKEN_NAME, request);
        Cookie refreshToken = CookieUtil.getCookie(TokenProvider.REFRESH_TOKEN_NAME, request);
        if(accessToken!=null && refreshToken!=null){
            redisService.delete(TokenProvider.REFRESH_TOKEN_NAME+principal.getUsername());
            redisService.setBlackList(principal.getUsername(),accessToken.getValue(), TokenProvider.TOKEN_VALIDATION_SECOND);
            response.addHeader("Set-Cookie",CookieUtil.createExpiredCookie(TokenProvider.ACCESS_TOKEN_NAME).toString());
            response.addHeader("Set-Cookie",CookieUtil.createExpiredCookie(TokenProvider.REFRESH_TOKEN_NAME).toString());
            return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping ("/users/details")
    public ResponseEntity<?> getDetails(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        if(principal!=null){
            return new ResponseEntity<>(userAccountService.getLoginResponse(principal.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}



