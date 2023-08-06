package com.dfoff.demo.service;


import com.dfoff.demo.annotation.SaveAdventure;
import com.dfoff.demo.domain.*;
import com.dfoff.demo.domain.jsondtos.*;
import com.dfoff.demo.repository.*;
import com.dfoff.demo.utils.CharactersUtil;
import com.dfoff.demo.utils.NeopleApiUtil;
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

import static com.dfoff.demo.utils.NeopleApiUtil.*;
import static com.dfoff.demo.utils.CharactersUtil.timesAgo;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharacterService {
    private final CharacterSkillDetailRepository characterSkillDetailRepository;
    private final CharacterEntityRepository characterEntityRepository;
    private final Gson gson = NeopleApiUtil.getGson();

    @Async
    public CompletableFuture<List<CharacterEntity.CharacterEntityDto>> getCharacterDtos(String serverId, String characterName) throws InterruptedException {
        if (characterName == null || characterName.equals("")) {
            return CompletableFuture.completedFuture(List.of());
        }
        List<CharacterDto> characterDtoList = parseJsonFromUri(NeopleApiUtil.getCharacterSearchUri(serverId, characterName), CharacterDto.CharacterJSONDto.class).toDto();
        List<CharacterEntity> characterEntityList = characterDtoList.stream().map(CharacterEntity.CharacterEntityDto::toEntity).filter(o -> o.getLevel() >= 75).collect(Collectors.toList());
        characterEntityRepository.saveAll(characterEntityList);
        return CompletableFuture.completedFuture(characterEntityList.stream().map(CharacterEntity.CharacterEntityDto::from).collect(Collectors.toList()));
    }

    public void saveSkillDetails(CharacterSkillStyleJsonDto style, CharacterSkillDetailJsonDto detail) {
        characterSkillDetailRepository.saveAll(CharacterSkillDetail.CharacterSkillDetailDto.toEntity(style, detail));
    }


    public List<CharacterEntity.CharacterEntityDto> getCharactersByAdventureName(String adventureName) {
        return characterEntityRepository.findAllByAdventureName(adventureName).stream().map(CharacterEntity.CharacterEntityDto::from).collect(Collectors.toList());
    }


    public String getRandomJobName() {
        if (characterEntityRepository.count() == 0) {
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

    public CharacterEntity.CharacterEntityDto getCharacter(String serverId, String characterId) throws InterruptedException {
        if (characterId == null || characterId.equals("")) {
            throw new IllegalArgumentException("캐릭터 아이디가 없습니다.");
        }
        CharacterEntity characterEntity = characterEntityRepository.findById(characterId).orElseGet(() -> {
            try {
                return characterEntityRepository.save(CharacterAbilityDto.toEntity(parseJsonFromUri(NeopleApiUtil.getCharacterAbilityUri(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto(), serverId));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return CharacterEntity.CharacterEntityDto.from(characterEntity);
    }

    @Async
    public CompletableFuture<CharacterEquipmentJsonDto> getCharacterEquipment(String serverId, String characterId) throws InterruptedException {
        if (characterId == null || characterId.equals("")) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getCharacterEquipmentUri(serverId, characterId), CharacterEquipmentJsonDto.class));
    }

    @Async
    public CompletableFuture<EquipmentDetailJsonDto> getEquipmentDetail(CharacterEquipmentJsonDto.Equipment eq) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getEquipmentDetailUri(eq.getItemId()), EquipmentDetailJsonDto.class));
    }

    @Async
    public CompletableFuture<CharacterBuffEquipmentJsonDto> getCharacterBuffEquipment(String serverId, String characterId) throws InterruptedException {
        if (characterId == null || characterId.equals("")) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getCharacterBuffEquipmentUri(serverId, characterId), CharacterBuffEquipmentJsonDto.class));
    }

    public Page<CharacterEntity.CharacterEntityResponse> getCharacterByAdventureName(String adventureName, Pageable pageable) {
        return characterEntityRepository.findAllByAdventureNameContaining(adventureName, pageable);
    }

    @SaveAdventure
    public CharacterEntity.CharacterEntityDto getCharacterAbility(CharacterEntity.CharacterEntityDto dto) throws InterruptedException {
        CharacterEntity entity = characterEntityRepository.save(CharacterEntity.CharacterEntityDto.toEntity(dto));
        CharacterAbilityDto characterDto = parseJsonFromUri(NeopleApiUtil.getCharacterAbilityUri(dto.getServerId(), dto.getCharacterId()), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        characterStatusSetter(entity, characterDto);
        entity.setServerId(dto.getServerId());
        return CharacterEntity.CharacterEntityDto.from(entity);
    }


    @Async
    @SaveAdventure
    public CompletableFuture<CharacterAbilityDto> getCharacterAbilityAsync(String serverId, String characterId) throws InterruptedException {
        CharacterAbilityDto characterDto = parseJsonFromUri(NeopleApiUtil.getCharacterAbilityUri(serverId, characterId), CharacterAbilityDto.CharacterAbilityJSONDto.class).toDto();
        CharacterEntity character = characterEntityRepository.save(CharacterAbilityDto.toEntity(characterDto, serverId));
        characterStatusSetter(character, characterDto);
        characterDto.setAdventureName(character.getAdventureName());
        characterDto.setAdventureFame(character.getAdventureFame());
        return CompletableFuture.completedFuture(characterDto);
    }

    @Async
    public CompletableFuture<List<CharacterTalismanJsonDto.Talisman>> getCharacterTalisman(String serverId, String characterId) throws InterruptedException {
        CharacterTalismanJsonDto dto = parseJsonFromUri(NeopleApiUtil.getCharacterTalismanUri(serverId, characterId), CharacterTalismanJsonDto.class);
        return CompletableFuture.completedFuture(dto.getTalismans());
    }


    @Async
    public CompletableFuture<CharacterSkillStyleJsonDto.Style> getCharacterSkillStyle(String serverId, String characterId) throws InterruptedException {
        CharacterSkillStyleJsonDto dto = parseJsonFromUri(NeopleApiUtil.getCharacterSkillUri(serverId, characterId), CharacterSkillStyleJsonDto.class);
        return CompletableFuture.completedFuture(dto.getSkill().getStyle());
    }

    public List<String> getSkillDetails(CharacterSkillStyleJsonDto.Style dto, String jobId) {
        List<String> list = new ArrayList<>();

        dto.getActive().forEach(o -> {
            try {
                if (o.getRequiredLevel() >= 15) {
                    list.add(CharactersUtil.getSkillFinalPercent(parseJsonFromUri(getCharacterSkillDetailUri(jobId, o.getSkillId()), CharacterSkillDetailJsonDto.class), o.getLevel()));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return list;
    }

    public Long getCharacterCount() {
        return characterEntityRepository.count();
    }

    public Long getCharacterCountByJobName(String jobName) {
        return characterEntityRepository.getCharacterCountByJobName(jobName);
    }

    public Long getRankCountByCharacterIdOrderByAdventureFame(String characterId) {
        return characterEntityRepository.getRankCountByCharacterIdOrderByAdventureFame(characterId);
    }


    public Long getRankCountByCharacterIdAndJobNameOrderByAdventureFame(String characterId, String jobName) {
        return characterEntityRepository.getRankCountByCharacterIdAndJobNameOrderByAdventureFame(characterId, jobName);
    }

    public Double getRankPercent(Long rank, Long count) {
        return (double) rank / count * 100;
    }


    public Long getBoardCountByCharacterId(String characterId) {
        return characterEntityRepository.getBoardCountByCharacterId(characterId);
    }


    @Async
    public CompletableFuture<CharacterBuffAvatarJsonDto> getCharacterBuffAvatar(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getCharacterBuffAvatarUri(serverId, characterId), CharacterBuffAvatarJsonDto.class));
    }

    @Async
    public CompletableFuture<CharacterBuffCreatureJsonDto> getCharacterBuffCreature(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getCharacterBuffCreatureUri(serverId, characterId), CharacterBuffCreatureJsonDto.class));
    }

    public Page<CharacterEntity.CharacterEntityDto> getCharactersOrderByAdventureFame(Pageable pageable) {
        return characterEntityRepository.findAll(pageable).map(CharacterEntity.CharacterEntityDto::from);
    }

    @Async
    public CompletableFuture<CharacterAvatarJsonDto> getCharacterAvatar(String serverId, String characterId) throws InterruptedException {
        return CompletableFuture.completedFuture(parseJsonFromUri(NeopleApiUtil.getCharacterAvatarUri(serverId, characterId), CharacterAvatarJsonDto.class));
    }


    public void getServerStatus() throws InterruptedException {
        ServerDto.ServerJSONDto dfServerJSONDto = parseJsonFromUri(NeopleApiUtil.SERVER_LIST_URI, ServerDto.ServerJSONDto.class);
        dfServerJSONDto.toDTO();
    }

    public void getJobList() throws InterruptedException {
        JobDto.JobJSONDto jobJSONDTO = parseJsonFromUri(NeopleApiUtil.JOB_LIST_URI, JobDto.JobJSONDto.class);
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

    public boolean checkCharacterAdventure(Adventure.UserAdventureRequest request) throws InterruptedException {
        if (request.getAdventureName().equals("")) {
            return false;
        }
        List<CharacterDto> dto = parseJsonFromUri(NeopleApiUtil.getCharacterSearchUri(request.getServerId(), request.getRandomString()), CharacterDto.CharacterJSONDto.class).toDto();
        if (dto.size() == 0) {
            return false;
        }
        CharacterDto characterDto = dto.get(0);
        CharacterAbilityDto dto_ = NeopleApiUtil.parseJsonFromUri(NeopleApiUtil.getCharacterAbilityUri(request.getServerId(), characterDto.getCharacterId()), CharacterAbilityDto.class);
        return dto_.getJobName().equals(request.getRandomJobName()) && dto_.getAdventureName().equals(request.getAdventureName());
    }


    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByAdventureFame() {
        return characterEntityRepository.getCharacterRankingBest5OrderByAdventureFame();
    }

    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByDamageIncrease() {
        return characterEntityRepository.getCharacterRankingBest5OrderByDamageIncrease();
    }

    public List<CharacterEntity.CharacterEntityMainPageResponse> getCharacterRankingBest5OrderByBuffPower() {
        return characterEntityRepository.getCharacterRankingBest5OrderByBuffPower();
    }

    private void characterStatusSetter(CharacterEntity entity, CharacterAbilityDto characterDto) {
        entity.setAdventureFame(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().isEmpty() ? 0 : Integer.parseInt(characterDto.getStatus().stream().filter(o -> o.getName().equals("모험가 명성")).findFirst().get().getValue()));
        entity.setDamageIncrease(characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().isEmpty() ? 0 : Integer.parseInt(characterDto.getStatus().stream().filter(o -> o.getName().equals("피해 증가")).findFirst().get().getValue()));
        entity.setBuffPower(characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().isEmpty() ? 0 : Integer.parseInt(characterDto.getStatus().stream().filter(o -> o.getName().equals("버프력")).findFirst().get().getValue()));
        entity.setAdventureName(characterDto.getAdventureName());
    }


    public Page<CharacterEntity.CharacterEntityRankingResponse> getCharacterRanking(String searchType, String characterName, Pageable pageable) {
        return switch (searchType) {
            case "damageIncrease" ->
                    characterEntityRepository.getCharacterRankingOrderByDamageIncrease(characterName, pageable);
            case "buffPower" -> characterEntityRepository.getCharacterRankingOrderByBuffPower(characterName, pageable);
            default -> characterEntityRepository.getCharacterRankingOrderByAdventureFame(characterName, pageable);
        };
    }

    public List<CharacterEntity.AutoCompleteResponse> getAutocompeleteCharacters(String serverId, String name) {
        if (serverId.equals("all")) {
            return characterEntityRepository.getCharacterNameAutoCompleteServerAll(name);
        }
        if (serverId.equals("adventure")) {
            return characterEntityRepository.getCharacterNameAutoCompleteServerAdventure(name);
        }
        return characterEntityRepository.getCharacterNameAutoComplete(name, serverId);


    }
}
