package com.dfoff.demo.service;

import com.dfoff.demo.domain.*;
import com.dfoff.demo.jwt.TokenDto;
import com.dfoff.demo.jwt.TokenProvider;
import com.dfoff.demo.repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.repository.UserAccountRepository;
import com.dfoff.demo.repository.AdventureRepository;
import com.dfoff.demo.utils.CharactersUtil;
import com.dfoff.demo.utils.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final AdventureRepository adventureRepository;

    private final UserAccountCharacterMapperRepository mapperRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    private final TokenProvider tokenProvider;


    public void createAccount(UserAccount.UserAccountSignUpRequest account, SaveFile.SaveFileDto profileIcon) {
        if (userAccountRepository.existsByUserId(account.getUserId())) {
            throw new EntityExistsException("이미 존재하는 아이디입니다.");
        }
        if (userAccountRepository.existsByEmail(account.getEmail())) {
            throw new EntityExistsException("이미 존재하는 이메일입니다.");
        }
        if (userAccountRepository.existsByNickname(account.getNickname())) {
            throw new EntityExistsException("이미 존재하는 닉네임입니다.");
        }
        UserAccount account0 = userAccountRepository.save(account.toEntity());
        account0.setProfileIcon(profileIcon.toEntity());
    }

    @Transactional(readOnly = true)
    public String getUserAdventureNameByUserId(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if (userAccount.getAdventure() == null) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        return userAccount.getAdventure().getAdventureName();

    }


    @Transactional(readOnly = true)
    public boolean existsByUserId(String userId) {
        return userAccountRepository.existsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByNickname(String nickname) {
        return userAccountRepository.existsByNickname(nickname);
    }


    public void refreshUserAdventure(UserAccount.UserAccountDto dto) {
        if (!adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(dto.userId())) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        userAdventureStatusSetter(userAccount);
    }

    public Adventure.AdventureDto addAllCharactersToUserAdventure(UserAccount.UserAccountDto dto, List<CharacterEntity.CharacterEntityDto> characters) {
        UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if (userAccount.getAdventure() == null) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        userAccount.getAdventure().getCharacters().addAll(characters.stream().map(CharacterEntity.CharacterEntityDto::toEntity).toList());
        return Adventure.AdventureDto.from(userAccount.getAdventure());
    }

    private void userAdventureStatusSetter(UserAccount userAccount) {
        Adventure adventure = userAccount.getAdventure();
        userAdventureStatusAdventureFameSetter(adventure);
        userAdventureStatusBuffPowerAndDamageIncreaseSetter(adventure);
    }

    private void userAdventureStatusAdventureFameSetter(Adventure adventure) {
        for (CharacterEntity entity : adventure.getCharacters()) {
            if ( entity.getAdventureFame()!=null && entity.getAdventureFame() != 0  ) {
                adventure.setAdventureFame(adventure.getAdventureFame() + entity.getAdventureFame());
            }
        }
    }


    private void userAdventureStatusBuffPowerAndDamageIncreaseSetter(Adventure adventure) {
        for (CharacterEntity entity : adventure.getCharacters()) {
            if (entity.getDamageIncrease()!=null && entity.getDamageIncrease() != 0) {
                adventure.setAdventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower() + entity.getDamageIncrease());
            }
            if (entity.getBuffPower()!=null && entity.getBuffPower() != 0) {
                adventure.setAdventureDamageIncreaseAndBuffPower(adventure.getAdventureDamageIncreaseAndBuffPower() + entity.getBuffPower());
            }
        }
    }


    public boolean changeProfileIcon(UserAccount.UserAccountDto dto, SaveFile.SaveFileDto iconDto) {
        UserAccount account = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setProfileIcon(iconDto.toEntity());
        account.setProfileCharacterIcon(null);
        account.setProfileCharacterIconClassName(null);
        return true;
    }

    @Transactional(readOnly = true)
    public UserAccount.UserAdventureResponse getUserAdventureById(String userId) {
        UserAccount account_ = userAccountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if (account_.getAdventure() == null) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        return UserAccount.UserAdventureResponse.from(account_);
    }

    @Transactional(readOnly = true)
    public Boolean checkUserAdventureById(String userId) {
        return adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(userId);
    }


    @Transactional(readOnly = true)
    public UserAccount.UserAccountMyPageResponse getUserAccountById(String userId) {
        if (userAccountRepository.existsByUserId(userId)) {
            return UserAccount.UserAccountMyPageResponse.from(userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")));
        }
        return null;
    }

    public UserAccount.UserLoginResponse getLoginResponse(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        return UserAccount.UserLoginResponse.from(userAccount);
    }



    public void saveUserAdventure(Adventure.UserAdventureRequest request, UserAccount.UserAccountDto userAccount, CharacterEntity.CharacterEntityDto character, List<CharacterEntity.CharacterEntityDto> characters) {
        if (adventureRepository.checkAdventureHasUserAccount(character.getAdventureName())) {
            throw new EntityExistsException("이미 등록된 모험단 입니다.");
        }
        if (adventureRepository.existsByRepresentCharacter_CharacterId(request.getRepresentCharacterId())) {
            throw new EntityExistsException("이미 등록된 대표 캐릭터 입니다.");
        }
        UserAccount account = userAccountRepository.findById(userAccount.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        Adventure adventure = adventureRepository.findById(request.getAdventureName()).orElseGet(() -> adventureRepository.save(request.toEntity(userAccount, character)));
        adventure.getCharacters().addAll(characters.stream().map(CharacterEntity.CharacterEntityDto::toEntity).toList());
        account.setAdventure(adventure);
        userAdventureStatusSetter(account);
    }

    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsById(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardCommentsByUserId(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsByIdOrderByLikeCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardCommentsByUserIdOrderByLikeCount(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsById(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserId(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByIdOrderByLikeCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByLikeCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByIdOrderByViewCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByViewCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByIdOrderByCommentCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByCommentCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }


    public void deleteUserAccountById(String userId) {
        if (userAccountRepository.existsByUserId(userId)) {
            userAccountRepository.deleteById(userId);
            SecurityContextHolder.clearContext();
        } else {
            throw new EntityNotFoundException("존재하지 않는 아이디입니다.");
        }
    }

    public void addCharacterToUserAccount(UserAccount.UserAccountDto account, CharacterEntity.CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.userId())) {
            UserAccount userAccount = userAccountRepository.findById(account.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, CharacterEntity.CharacterEntityDto.toEntity(character));
            mapperRepository.save(mapper);
        }
    }


    public void deleteCharacterFromUserAccount(UserAccount.UserAccountDto dto, CharacterEntity.CharacterEntityDto character) {
        if (userAccountRepository.existsByUserId(dto.userId())) {
            UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = mapperRepository.findByUserAccountAndCharacter(userAccount, CharacterEntity.CharacterEntityDto.toEntity(character));
            if (mapper != null) {
                mapper.setCharacter(null);
                mapper.setUserAccount(null);
            }
        }
    }

    public void changePassword(UserAccount.UserAccountDto accountDto, String password) {
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자리로 입력해주세요.");
        }
        UserAccount account = userAccountRepository.findById(accountDto.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setPassword(password);
    }

    public void changeNickname(UserAccount.UserAccountDto accountDto, String nickname) {
        if (!nickname.matches("^[a-zA-Z0-9가-힣]{2,10}$")) {
            throw new IllegalArgumentException("닉네임은 2~10자리로 입력해주세요.");
        }
        if (userAccountRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        UserAccount account = userAccountRepository.findById(accountDto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        account.setNickname(nickname);
    }

    public void changeEmail(UserAccount.UserAccountDto accountDto, String email) {
        if (!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
        if (userAccountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        UserAccount account = userAccountRepository.findById(accountDto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        account.setEmail(email);
    }

    public void deleteUserAdventureFromUserAccount(String userId) {
        UserAccount account = userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (account.getAdventure() == null) {
            throw new IllegalArgumentException("모험단이 등록되지 않았습니다.");
        }
        account.getAdventure().setUserAccount(null);
        account.getAdventure().setRepresentCharacter(null);
        account.setAdventure(null);
        account.setProfileCharacterIcon(null);
        account.setProfileCharacterIconClassName(null);

    }

    public void changeProfileIconByAdventureCharacter(CharacterEntity.CharacterEntityDto dto, String userId) {
        UserAccount account_ = userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (account_.getAdventure() == null) {
            throw new IllegalArgumentException("모험단이 등록되지 않았습니다.");
        }
        if (!dto.getAdventureName().equals(account_.getAdventure().getAdventureName())) {
            throw new IllegalArgumentException("회원님의 모험단에 등록된 캐릭터가 아닙니다.");
        }
        account_.setProfileCharacterIcon(RestTemplateUtil.getCharacterImgUri(dto.getServerId(), dto.getCharacterId(), "1"));
        account_.setProfileCharacterIconClassName(CharactersUtil.getStyleClassName(dto.getJobName()));
    }

    public Page<Board.BoardListMyPageResponse> getUserBoardLogs(UserAccount.UserAccountDto dto, Pageable pageable, String sortBy) {
        return switch (sortBy) {
            case "like" -> getBoardsByIdOrderByLikeCount(dto.userId(), pageable);
            case "commentCount" -> getBoardsByIdOrderByCommentCount(dto.userId(), pageable);
            case "view" -> getBoardsByIdOrderByViewCount(dto.userId(), pageable);
            default -> getBoardsById(dto.userId(), pageable);
        };
    }

    @Transactional (readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getUserCommentLogs(UserAccount.UserAccountDto dto, Pageable pageable, String sortBy) {
        if (sortBy.equals("like")) {
            return getCommentsByIdOrderByLikeCount(dto.userId(), pageable);
        }
        return getCommentsById(dto.userId(), pageable);
    }

    @Transactional (readOnly = true)
    public TokenDto getToken(String userId, String password) throws Exception {
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAccount.PrincipalDto principal = (UserAccount.PrincipalDto) authentication.getPrincipal();
        log.info("principal : {}", principal);
        return TokenDto.from(principal, tokenProvider.generateToken(principal),tokenProvider.generateRefreshToken(principal));
    }

}
