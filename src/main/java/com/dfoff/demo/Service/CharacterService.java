package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.CharacterSkillDetail;
import com.dfoff.demo.Domain.ForCharacter.*;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.CharacterSkillDetailRepository;
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
import static com.dfoff.demo.Util.SearchPageUtil.timesAgo;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharacterService {
    private final CharacterSkillDetailRepository characterSkillDetailRepository;
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

    public void saveSkillDetails(CharacterSkillStyleJsonDto style, CharacterSkillDetailJsonDto detail){
        characterSkillDetailRepository.saveAll(CharacterSkillDetail.CharacterSkillDetailDto.toEntity(style,detail));
    }




    public List<CharacterSkillDetail.CharacterSkillDetailDto> getCharacterSkillDetail(String serverId, String characterId) {
        return getSkillDetails(parseUtil(OpenAPIUtil.getCharacterSkillUrl(serverId, characterId), CharacterSkillStyleJsonDto.class));
    }

    public String getLastUpdatedByCharacterId(String characterId) {
        return timesAgo(characterEntityRepository.getReferenceById(characterId).getModifiedAt());
    }

    public CharacterEntity.CharacterEntityDto getCharacter(String serverId,String characterId) {
        if(characterId==null || characterId.equals("")){
            return null;
        }
        CharacterEntity characterEntity = characterEntityRepository.findById(characterId).orElseGet(()-> characterEntityRepository.save(CharacterAbilityDto.toEntity(parseUtil(OpenAPIUtil.getCharacterAbilityUrl(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto(),serverId)));
        return CharacterEntity.CharacterEntityDto.from(characterEntity);
    }

    public CharacterEquipmentJsonDto getCharacterEquipment(String serverId,String characterId) {
        if(characterId==null || characterId.equals("")){
            return null;
        }
        return parseUtil(OpenAPIUtil.getCharacterEquipmentUrl(serverId, characterId), CharacterEquipmentJsonDto.class);
    }

    public List<EquipmentDetailJsonDto> getEquipmentDetail(CharacterEquipmentJsonDto dto) {
        List <EquipmentDetailJsonDto> list = new ArrayList<>();
        for(CharacterEquipmentJsonDto.Equipment eq : dto.getEquipment()){
            list.add(parseUtil(OpenAPIUtil.getEquipmentDetailUrl(eq.getItemId()),EquipmentDetailJsonDto.class));
        }
        return list;
    }

    public CharacterBuffEquipmentJsonDto getCharacterBuffEquipment(String serverId,String characterId) {
        if(characterId==null || characterId.equals("")){
            return null;
        }
        return parseUtil(OpenAPIUtil.getCharacterBuffEquipmentUrl(serverId, characterId), CharacterBuffEquipmentJsonDto.class);
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

    public CharacterAbilityDto getCharacterAbility(String serverId, String characterId) {
        CharacterAbilityDto characterDto = parseUtil(OpenAPIUtil.getCharacterAbilityUrl(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        CharacterEntity character = characterEntityRepository.save(CharacterAbilityDto.toEntity(characterDto,serverId));
        character.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? "0" : characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue());
        characterDto.setAdventureFame(character.getAdventureFame());
        return characterDto;
    }

    public void addCharacter(UserAccount.UserAccountDto account, CharacterEntity.CharacterEntityDto character) { //캐릭터가 존재하지 않을 이유가 없음
        if (userAccountRepository.existsByUserId(account.userId())) {
            UserAccount userAccount = userAccountRepository.findById(account.userId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
            CharacterEntity characterEntity = characterEntityRepository.findById(character.getCharacterId()).orElseGet(() -> CharacterEntity.CharacterEntityDto.toEntity(character));
            UserAccountCharacterMapper mapper = UserAccountCharacterMapper.of(userAccount, characterEntity);
            mapperRepository.save(mapper);
        }
    }

    public List<CharacterTalismanJsonDto.Talisman> getCharacterTalisman(String serverId, String characterId) {
        CharacterTalismanJsonDto dto = parseUtil(OpenAPIUtil.getCharacterTalismanUrl(serverId, characterId), CharacterTalismanJsonDto.class);
        return dto.getTalismans();
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

    public CharacterSkillStyleJsonDto.Style getCharacterSkillStyle(String serverId, String characterId) {
        CharacterSkillStyleJsonDto dto = parseUtil(OpenAPIUtil.getCharacterSkillUrl(serverId, characterId), CharacterSkillStyleJsonDto.class);
        return dto.getSkill().getStyle();
    }

    public List<CharacterSkillDetail.CharacterSkillDetailDto> getSkillDetails(CharacterSkillStyleJsonDto dto) {
        List<CharacterSkillDetail> list = new ArrayList<>();
        for(CharacterSkillStyleJsonDto.Active active : dto.getSkill().getStyle().getActive()){
            log.info("jobID:{}, skillId:{}",dto.getJobId(),active.getSkillId());
            if(active.getRequiredLevel()>=20){
            characterSkillDetailRepository.getCharacterSkillDetailBySkillIdAndSkillLevel(active.getSkillId(),String.valueOf(active.getLevel())).ifPresentOrElse(list::add,()->{
                CharacterSkillDetailJsonDto detail = parseUtil(OpenAPIUtil.getCharacterSkillDetailUrl(dto.getJobId(),active.getSkillId()), CharacterSkillDetailJsonDto.class);
                characterSkillDetailRepository.saveAll(CharacterSkillDetail.CharacterSkillDetailDto.toEntity(dto,detail));
            }
            );
            }
        }
        return list.stream().map(CharacterSkillDetail.CharacterSkillDetailDto::from).collect(Collectors.toList());
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



    public CharacterBuffAvatarJsonDto getCharacterBuffAvatar(String serverId, String characterId) {
        CharacterBuffAvatarJsonDto dto = parseUtil(OpenAPIUtil.getCharacterBuffAvatarUrl(serverId, characterId), CharacterBuffAvatarJsonDto.class);
       return dto;
    }

    public CharacterBuffCreatureJsonDto getCharacterBuffCreature(String serverId, String characterId) {
        CharacterBuffCreatureJsonDto dto = parseUtil(OpenAPIUtil.getCharacterBuffCreatureUrl(serverId, characterId), CharacterBuffCreatureJsonDto.class);
        return dto;
    }

    public CharacterAvatarJsonDto getCharacterAvatar(String serverId, String characterId) {
        CharacterAvatarJsonDto dto = parseUtil(OpenAPIUtil.getCharacterAvatarUrl(serverId, characterId), CharacterAvatarJsonDto.class);
        return dto;
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
