package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.ForCharacter.CharacterDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDto;
import com.dfoff.demo.Domain.ForCharacter.JobDto;
import com.dfoff.demo.Domain.ForCharacter.ServerDto;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Domain.UserAccountCharacterMapper;
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

    public List<CharacterEntity.CharacterEntityDto> getCharacterDTOs(String serverId, String characterName) {
        if (characterName == null || characterName.equals("")) {
            return List.of();
        }
        List<CharacterDto> characterDtoList = parseUtil(OpenAPIUtil.getCharacterSearchUrl(serverId, characterName), CharacterDto.CharacterJSONDto.class).toDto();
        List<CharacterEntity> characterEntityList = characterDtoList.stream().map(CharacterEntity.CharacterEntityDto::toEntity).collect(Collectors.toList());
        characterEntityRepository.saveAll(characterEntityList);
        return characterEntityList.stream().map(CharacterEntity.CharacterEntityDto::from).collect(Collectors.toList());
    }

    public CharacterEntity.CharacterEntityDto getCharacter(String serverId,String characterId) {
        if(characterId==null || characterId.equals("")){
            return null;
        }
        CharacterEntity characterEntity = characterEntityRepository.findById(characterId).orElseGet(()-> characterEntityRepository.save(CharacterAbilityDto.toEntity(parseUtil(OpenAPIUtil.getCharacterAbilityUrl(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto(),serverId)));
        return CharacterEntity.CharacterEntityDto.from(characterEntity);
    }

    public Page<CharacterEntity.CharacterEntityDto> getCharacterByAdventureName(String adventureName, Pageable pageable) {
        return characterEntityRepository.findAllByAdventureNameContaining(adventureName, pageable).map(CharacterEntity.CharacterEntityDto::from);
    }

    @Async
    public CompletableFuture<CharacterEntity.CharacterEntityDto> getCharacterAbilityThenSaveAsync(CharacterEntity.CharacterEntityDto dto) {
        CharacterEntity entity = characterEntityRepository.save(CharacterEntity.CharacterEntityDto.toEntity(dto));
        CharacterAbilityDto characterDto = parseUtil(OpenAPIUtil.getCharacterAbilityUrl(dto.getServerId(), dto.getCharacterId()), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        entity.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue());
        entity.setAdventureName(characterDto.getAdventureName());
        entity.setServerId(dto.getServerId());
        return CompletableFuture.completedFuture(CharacterEntity.CharacterEntityDto.from(entity));
    }

    public void addCharacter(UserAccount.UserAccountDto account, CharacterEntity.CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.userId())) {
            UserAccount userAccount = userAccountRepository.findById(account.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseGet(() -> CharacterEntity.CharacterEntityDto.toEntity(character));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, characterEntity);
            mapperRepository.save(mapper);
        }
    }


    public void deleteCharacter(UserAccount.UserAccountDto dto, CharacterEntity.CharacterEntityDto character) {
        if (userAccountRepository.existsByUserId(dto.userId())) {
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseGet(() -> CharacterEntity.CharacterEntityDto.toEntity(character));
            UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            UserAccountCharacterMapper mapper = mapperRepository.findByUserAccountAndCharacter(userAccount, characterEntity);
            if(mapper!=null) {
                mapper.setCharacter(null);
                mapper.setUserAccount(null);
            }
        }

    }


    public void getServerStatus() {
        ServerDto.ServerJSONDto dfServerJSONDto = parseUtil(OpenAPIUtil.SERVER_LIST_URL, ServerDto.ServerJSONDto.class);
        dfServerJSONDto.toDTO();
    }

    public void getJobList() {
        JobDto.JobJSONDto jobJSONDTO = parseUtil(OpenAPIUtil.JOB_LIST_URL, JobDto.JobJSONDto.class);
        jobJSONDTO.toDTO();
    }


}
