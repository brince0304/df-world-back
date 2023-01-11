package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.CharacterEntityDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.JobDTO;
import com.dfoff.demo.Domain.ForCharacter.ServerDTO;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.UserAccountCharacterMapper;
import com.dfoff.demo.Util.OpenAPIUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.dfoff.demo.Util.OpenAPIUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharacterService {
    private final CharacterEntityRepository characterEntityRepository;
    private final UserAccountRepository userAccountRepository;

    private final UserAccountCharacterMapperRepository mapperRepository;

    private final Gson gson = OpenAPIUtil.getGson();


    public boolean addCharacter(UserAccount.UserAccountDTO account, CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.getUserId()) ) {
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
            UserAccount userAccount = userAccountRepository.findById(account.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, characterEntity);
            mapperRepository.save(mapper);
            return true;
        }
        return false;
    }

    public Page<CharacterEntityDto> getCharacterByAdventureName(String adventureName, Pageable pageable){
        return characterEntityRepository.findAllByAdventureNameContaining(adventureName, pageable).map(CharacterEntityDto::toDto);
    }


    public boolean deleteCharacter(UserAccount.UserAccountDTO dto, CharacterEntityDto character) {
        if (userAccountRepository.existsByUserId(dto.getUserId())) {
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
            UserAccount userAccount = userAccountRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = mapperRepository.findByUserAccountAndCharacter(userAccount, characterEntity);
            mapper.setCharacter(null);
            mapper.setUserAccount(null);
            return true;
        }
        return false;

    }

    public CharacterEntityDto getCharacter(String characterId) {
        CharacterEntity characterEntity = characterEntityRepository.findById(characterId).orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
        return CharacterEntityDto.toDto(characterEntity);
    }


    public void getServerStatus() {
        ServerDTO.ServerJSONDTO dfServerJSONDTO = parseUtil(OpenAPIUtil.SERVER_LIST_URL, ServerDTO.ServerJSONDTO.class);
        dfServerJSONDTO.toDTO();
    }

    public void getJobList() {
        JobDTO.JobJSONDTO jobJSONDTO = parseUtil(OpenAPIUtil.JOB_LIST_URL, JobDTO.JobJSONDTO.class);
        jobJSONDTO.toDTO();
    }

    public List<CharacterEntityDto> getCharacterDTOs(String serverId, String characterName) {
        if (characterName == null || characterName.equals("")) {
            return List.of();
        }
        List<CharacterDTO> characterDTOList = parseUtil(OpenAPIUtil.getCharacterSearchUrl(serverId, characterName), CharacterDTO.CharacterJSONDTO.class).toDTO();
        for(CharacterDTO characterDTO : characterDTOList) {
            characterEntityRepository.save(CharacterDTO.toEntity(characterDTO));
        }
        return characterDTOList.stream().map(CharacterEntityDto::from).collect(Collectors.toList());
    }



    @Async
    public CompletableFuture<CharacterEntityDto> getCharacterAbilityThenSaveAsync(CharacterEntityDto dto) {
        CharacterEntity entity = characterEntityRepository.save(CharacterEntityDto.toEntity(dto));
        CharacterAbilityDTO characterDto = parseUtil(OpenAPIUtil.getCharacterAbilityUrl(dto.getServerId(), dto.getCharacterId()), CharacterAbilityDTO.CharacterAbilityJSONDTO.class).toDTO();
        entity.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue());
        entity.setAdventureName(characterDto.getAdventureName());
        entity.setServerId(dto.getServerId());
        return CompletableFuture.completedFuture(CharacterEntityDto.toDto(entity));
    }




}
