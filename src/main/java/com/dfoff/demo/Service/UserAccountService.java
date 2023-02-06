package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Repository.AdventureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public String getUserAdventureNameByUserId(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        if(userAccount.getAdventure() == null) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        return userAccount.getAdventure().getAdventureName();

    }



    public boolean existsByUserId(String userId) {
        return userAccountRepository.existsByUserId(userId);
    }

    public boolean existsByEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userAccountRepository.existsByNickname(nickname);
    }


    public void updateAccountDetails(UserAccount.UserAccountDto request) {
        UserAccount account = userAccountRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByEmail(request.email())) {
            return;
        }
        if (userAccountRepository.existsByNickname(request.nickname())) {
            return;
        }
        if (request.email() != null) {
            account.setEmail(request.email());
        }
        if (request.nickname() != null) {
            account.setNickname(request.nickname());
        }
    }

    public void refreshUserAdventure(UserAccount.UserAccountDto dto) {
        if (!adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(dto.userId())) {
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        userAdventureStatusSetter(userAccount);
    }

    public Adventure.UserAdventureDto addAllCharactersToUserAdventure(UserAccount.UserAccountDto dto, List<CharacterEntity.CharacterEntityDto> characters){
        UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if(userAccount.getAdventure()== null){
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        userAccount.getAdventure().getCharacters().addAll(characters.stream().map(CharacterEntity.CharacterEntityDto::toEntity).toList());
        return Adventure.UserAdventureDto.from(userAccount.getAdventure());
    }

    private void userAdventureStatusSetter(UserAccount userAccount) {
        Integer userAdventureFame = userAccount.getAdventure().getCharacters().stream().mapToInt(CharacterEntity::getAdventureFame).sum();
        Integer userDamageIncrease = userAccount.getAdventure().getCharacters().stream().mapToInt(CharacterEntity::getDamageIncrease).sum();
        Integer userBuffPower = userAccount.getAdventure().getCharacters().stream().mapToInt(CharacterEntity::getBuffPower).sum();
        userAccount.getAdventure().setAdventureFame(userAdventureFame);
        userAccount.getAdventure().setAdventureDamageIncreaseAndBuffPower(userDamageIncrease + userBuffPower);
    }


    public boolean changeProfileIcon(UserAccount.UserAccountDto dto, SaveFile.SaveFileDto iconDto) {
        UserAccount account = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setProfileIcon(iconDto.toEntity());
        return true;
    }

    public Adventure.UserAdventureResponse getUserAdventureByUserId(String userId) {
        UserAccount account_= userAccountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if(account_.getAdventure()==null){
            throw new EntityNotFoundException("모험단이 등록되지 않았습니다.");
        }
        return Adventure.UserAdventureResponse.from(account_.getAdventure());
    }

    public Boolean checkUserAdventure(String userId) {

        return  adventureRepository.existsByUserAccount_UserIdAndDeletedIsFalse(userId);
    }


    @Transactional(readOnly = true)
    public UserAccount.UserAccountMyPageResponse getUserAccountById(String userId) {
        if (userAccountRepository.existsByUserId(userId)) {
            return UserAccount.UserAccountMyPageResponse.from(userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")));
        }
        return null;
    }


    public void saveUserAdventure(Adventure.UserAdventureRequest request, UserAccount.UserAccountDto userAccount, CharacterEntity.CharacterEntityDto character, List<CharacterEntity.CharacterEntityDto> characters) {
        if (adventureRepository.checkAdventureHasUserAccount(character.getAdventureName())) {
            throw new EntityExistsException("이미 등록된 모험단 입니다.");
        }
        if (adventureRepository.existsByRepresentCharacter_CharacterId(request.getRepresentCharacterId())) {
            throw new EntityExistsException("이미 등록된 대표 캐릭터 입니다.");
        }
        UserAccount account = userAccountRepository.findById(userAccount.userId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        Adventure adventure = adventureRepository.save(request.toEntity(userAccount, character));
        adventure.getCharacters().addAll(characters.stream().map(CharacterEntity.CharacterEntityDto::toEntity).toList());
        account.setAdventure(adventure);
        userAdventureStatusSetter(account);
    }

    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsByUserId(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardCommentsByUserId(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<BoardComment.BoardCommentMyPageResponse> getCommentsByUserIdOrderByLikeCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardCommentsByUserIdOrderByLikeCount(userId, pageable).map(BoardComment.BoardCommentMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserId(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserId(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByLikeCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByLikeCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByViewCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByViewCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public Page<Board.BoardListMyPageResponse> getBoardsByUserIdOrderByCommentCount(String userId, Pageable pageable) {
        if (userAccountRepository.existsByUserId(userId)) {
            return userAccountRepository.findBoardsByUserIdOrderByCommentCount(userId, pageable).map(Board.BoardListMyPageResponse::from);
        }
        return Page.empty();
    }


    public void deleteUserAccountById(String userId) {
        if (userAccountRepository.existsByUserId(userId)) {
            userAccountRepository.deleteById(userId);
            SecurityContextHolder.clearContext();
        }else{
            throw new EntityNotFoundException("존재하지 않는 아이디입니다.");
        }
    }

    public void addCharacter(UserAccount.UserAccountDto account, CharacterEntity.CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.userId())) {
            UserAccount userAccount = userAccountRepository.findById(account.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, CharacterEntity.CharacterEntityDto.toEntity(character));
            mapperRepository.save(mapper);
        }
    }
    
    

    public void deleteCharacter(UserAccount.UserAccountDto dto, CharacterEntity.CharacterEntityDto character) {
        if (userAccountRepository.existsByUserId(dto.userId())) {
            UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = mapperRepository.findByUserAccountAndCharacter(userAccount, CharacterEntity.CharacterEntityDto.toEntity(character));
            if(mapper!=null) {
                mapper.setCharacter(null);
                mapper.setUserAccount(null);
            }
        }
    }

    public boolean changePassword(UserAccount.UserAccountDto accountDTO, String password) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setPassword(password);
        return true;
    }

    public boolean changeNickname(UserAccount.UserAccountDto accountDTO, String nickname) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByNickname(nickname)) {
            return false;
        }
        account.setNickname(nickname);
        return true;
    }

    public boolean changeEmail(UserAccount.UserAccountDto accountDTO, String email) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByEmail(email)) {
            return false;
        }
        account.setEmail(email);
        return true;
    }

    public void deleteUserAdventure(String userId) {
        UserAccount account = userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if(account.getAdventure()==null){
            throw new IllegalArgumentException("모험단이 등록되지 않았습니다.");
        }
        account.getAdventure().setUserAccount(null);
        account.getAdventure().setRepresentCharacter(null);
        account.setAdventure(null);

    }
}
