package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.JsonDtos.*;
import com.dfoff.demo.Repository.*;
import com.dfoff.demo.Util.CharactersUtil;
import com.dfoff.demo.Util.RestTemplateUtil;
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

import static com.dfoff.demo.Util.RestTemplateUtil.*;
import static com.dfoff.demo.Util.CharactersUtil.timesAgo;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharacterService {
    private final CharacterSkillDetailRepository characterSkillDetailRepository;
    private final CharacterEntityRepository characterEntityRepository;
    private final UserAccountRepository userAccountRepository;

    private final UserAccountCharacterMapperRepository mapperRepository;


    private final Gson gson = RestTemplateUtil.getGson();

    @Async
    public CompletableFuture<List<CharacterEntity.CharacterEntityDto>> getCharacterDtos(String serverId, String characterName) throws InterruptedException {
        if (characterName == null || characterName.equals("")) {
            return CompletableFuture.completedFuture(List.of());
        }
        List<CharacterDto> characterDtoList = parseJsonFromUri(RestTemplateUtil.getCharacterSearchUri(serverId, characterName), CharacterDto.CharacterJSONDto.class).toDto();
        List<CharacterEntity> characterEntityList = characterDtoList.stream().map(CharacterEntity.CharacterEntityDto::toEntity).filter(o->o.getLevel()>=75).collect(Collectors.toList());
        characterEntityRepository.saveAll(characterEntityList);
        return CompletableFuture.completedFuture(characterEntityList.stream().map(CharacterEntity.CharacterEntityDto::from).collect(Collectors.toList()));
    }

    public void saveSkillDetails(CharacterSkillStyleJsonDto style, CharacterSkillDetailJsonDto detail){
        characterSkillDetailRepository.saveAll(CharacterSkillDetail.CharacterSkillDetailDto.toEntity(style,detail));
    }






    public String getRandomJobName(){
        if(characterEntityRepository.count() == 0){
            return "귀검사(남)";
        }
        long number = characterEntityRepository.count();
        Random random = new Random();
        int randomInt = random.nextInt((int) number);
        return characterEntityRepository.findAll().get(randomInt).getJobName();
    }

    public String getLastUpdatedByCharacterId(String characterId) {
        return timesAgo(characterEntityRepository.getReferenceById(characterId).getModifiedAt());
    }

    @Async
    public CompletableFuture<CharacterEntity.CharacterEntityDto> getCharacter(String serverId, String characterId) {
        if(characterId==null || characterId.equals("")){
            return CompletableFuture.completedFuture(null);
        }
        CharacterEntity characterEntity = characterEntityRepository.findById(characterId).orElseGet(()-> {
            try {
                return characterEntityRepository.save(CharacterAbilityDto.toEntity(parseJsonFromUri(RestTemplateUtil.getCharacterAbilityUri(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto(),serverId));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return CompletableFuture.completedFuture(CharacterEntity.CharacterEntityDto.from(characterEntity));
    }

    @Async
    public CompletableFuture<CharacterEquipmentJsonDto> getCharacterEquipment(String serverId, String characterId) throws InterruptedException {
        if(characterId==null || characterId.equals("")){
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getCharacterEquipmentUri(serverId, characterId), CharacterEquipmentJsonDto.class));
    }

    @Async
    public CompletableFuture<EquipmentDetailJsonDto> getEquipmentDetail(CharacterEquipmentJsonDto.Equipment eq) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getEquipmentDetailUri(eq.getItemId()),EquipmentDetailJsonDto.class));
    }

    @Async
    public CompletableFuture<CharacterBuffEquipmentJsonDto> getCharacterBuffEquipment(String serverId, String characterId) throws InterruptedException {
        if(characterId==null || characterId.equals("")){
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getCharacterBuffEquipmentUri(serverId, characterId), CharacterBuffEquipmentJsonDto.class));
    }

    public Page<CharacterEntity.CharacterEntityDto> getCharacterByAdventureName(String adventureName, Pageable pageable) {
        return characterEntityRepository.findAllByAdventureNameContaining(adventureName, pageable).map(CharacterEntity.CharacterEntityDto::from);
    }

    @Async
    public CompletableFuture<CharacterEntity.CharacterEntityDto> getCharacterAbilityAsync(CharacterEntity.CharacterEntityDto dto) throws InterruptedException {
        CharacterEntity entity = characterEntityRepository.save(CharacterEntity.CharacterEntityDto.toEntity(dto));
        CharacterAbilityDto characterDto = parseJsonFromUri(RestTemplateUtil.getCharacterAbilityUri(dto.getServerId(), dto.getCharacterId()), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        entity.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue());
        entity.setDamageIncrease(characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().get().getValue());
        entity.setBuffPower(characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().get().getValue());
        entity.setAdventureName(characterDto.getAdventureName());
        entity.setServerId(dto.getServerId());
        return CompletableFuture.completedFuture(CharacterEntity.CharacterEntityDto.from(entity));
    }

    @Async
    public CompletableFuture<CharacterAbilityDto> getCharacterAbility(String serverId, String characterId) throws InterruptedException {
        CharacterAbilityDto characterDto = parseJsonFromUri(RestTemplateUtil.getCharacterAbilityUri(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        CharacterEntity character = characterEntityRepository.save(CharacterAbilityDto.toEntity(characterDto,serverId));
        character.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue());
        character.setDamageIncrease(characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().get().getValue());
        character.setBuffPower(characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().get().getValue());
        characterDto.setAdventureName(character.getAdventureName());
        characterDto.setAdventureFame(character.getAdventureFame());
        return CompletableFuture.completedFuture(characterDto);
    }

    public void addCharacter(UserAccount.UserAccountDto account, CharacterEntity.CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.userId())) {
            UserAccount userAccount = userAccountRepository.findById(account.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseGet(() -> CharacterEntity.CharacterEntityDto.toEntity(character));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, characterEntity);
            mapperRepository.save(mapper);
        }
    }

    @Async
    public CompletableFuture<List<CharacterTalismanJsonDto.Talisman>> getCharacterTalisman(String serverId, String characterId) throws InterruptedException {
        CharacterTalismanJsonDto dto = parseJsonFromUri(RestTemplateUtil.getCharacterTalismanUri(serverId, characterId), CharacterTalismanJsonDto.class);
        return CompletableFuture.completedFuture(dto.getTalismans());
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

    @Async
    public CompletableFuture<CharacterSkillStyleJsonDto.Style> getCharacterSkillStyle(String serverId, String characterId) throws InterruptedException {
        CharacterSkillStyleJsonDto dto = parseJsonFromUri(RestTemplateUtil.getCharacterSkillUri(serverId, characterId), CharacterSkillStyleJsonDto.class);
        return CompletableFuture.completedFuture(dto.getSkill().getStyle());
    }

    public List<String> getSkillDetails(CharacterSkillStyleJsonDto.Style dto, String jobId) {
        List<String> list = new ArrayList<>();

        dto.getActive().forEach(o-> {
            try {
                if(o.getRequiredLevel()>=15) {
                    list.add(CharactersUtil.getSkillFinalPercent(parseJsonFromUri(getCharacterSkillDetailUri(jobId, o.getSkillId()), CharacterSkillDetailJsonDto.class), o.getLevel()));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("skill details : {}",list);
        return list;
    }

    public Long getCharacterCount(){
        return characterEntityRepository.count();
    }

    public Long getCharacterCountByJobName(String jobName){
        return characterEntityRepository.getCharacterCountByJobName(jobName);
    }

    public Long getRankByCharacterId(String characterId){
        return characterEntityRepository.getRankByCharacterId(characterId);
    }



    public Long getRankByCharacterIdAndJobName(String characterId, String jobName){
        return characterEntityRepository.getRankByCharacterId(characterId,jobName);
    }

    public Double getRankPercent(Long rank, Long count){
        return (double)rank/count*100;
    }



    public Long getBoardCountByCharacterId(String characterId){
        return characterEntityRepository.getBoardCountByCharacterId(characterId);
    }


    @Async
    public CompletableFuture<CharacterBuffAvatarJsonDto> getCharacterBuffAvatar(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getCharacterBuffAvatarUri(serverId, characterId), CharacterBuffAvatarJsonDto.class));
    }

    @Async
    public CompletableFuture<CharacterBuffCreatureJsonDto> getCharacterBuffCreature(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getCharacterBuffCreatureUri(serverId, characterId), CharacterBuffCreatureJsonDto.class));
    }

    public Page<CharacterEntity.CharacterEntityDto> getCharactersOrderByAdventureFame(Pageable pageable){
        return characterEntityRepository.findAll(pageable).map(CharacterEntity.CharacterEntityDto::from);
    }

    @Async
    public CompletableFuture<CharacterAvatarJsonDto> getCharacterAvatar(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(RestTemplateUtil.getCharacterAvatarUri(serverId, characterId), CharacterAvatarJsonDto.class));
    }



    public void getServerStatus() throws InterruptedException {
        ServerDto.ServerJSONDto dfServerJSONDto = parseJsonFromUri(RestTemplateUtil.SERVER_LIST_URI, ServerDto.ServerJSONDto.class);
        dfServerJSONDto.toDTO();
    }

    public void getJobList() throws InterruptedException {
        JobDto.JobJSONDto jobJSONDTO = parseJsonFromUri(RestTemplateUtil.JOB_LIST_URI, JobDto.JobJSONDto.class);
        jobJSONDTO.toDTO();
    }


    public String getRandomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * 3);
            switch (index) {
                case 0 -> randomString.append((char) ((int) (Math.random() * 26) + 97));
                case 1 -> randomString.append((char) ((int) (Math.random() * 26) + 65));
                case 2 -> randomString.append((int) (Math.random() * 10));
            }
        }
        return randomString.toString();
    }

    public boolean checkCharacterAdventure(UserAdventure.UserAdventureRequest request) throws InterruptedException {
        List<CharacterDto> dto = parseJsonFromUri(RestTemplateUtil.getCharacterSearchUri(request.getServerId(),request.getRandomString()), CharacterDto.CharacterJSONDto.class).toDto();
        if(dto.size()==0){
            return false;
        }
CharacterDto characterDto = dto.get(0);
        CharacterAbilityDto dto_ = RestTemplateUtil.parseJsonFromUri(RestTemplateUtil.getCharacterAbilityUri(request.getServerId(), characterDto.getCharacterId()), CharacterAbilityDto.class);
        return dto_.getJobName().equals(request.getRandomJobName()) && dto_.getAdventureName().equals(request.getAdventureName());
    }
}
