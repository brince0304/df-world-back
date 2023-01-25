package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.JsonDtos.CharacterBuffEquipmentJsonDto;
import com.dfoff.demo.Domain.JsonDtos.CharacterEquipmentJsonDto;
import com.dfoff.demo.Domain.JsonDtos.EquipmentDetailJsonDto;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Util.CharactersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/characters/")
    public ModelAndView searchCharacterInfo(@RequestParam(required = false) String serverId,
                                            @RequestParam(required = false) String characterName,
                                            @PageableDefault(size = 12) Pageable pageable) {
        ModelAndView mav = new ModelAndView("/search/searchPage");
        if (serverId == null || characterName == null) {
            mav.addObject("characters", Page.empty());
            return mav;
        }
        mav.addObject("serverId", serverId);
        mav.addObject("characterName", characterName);
        if (serverId.equals("adventure")) {
            mav.addObject("characters", characterService.getCharacterByAdventureName(characterName, pageable).map(CharacterEntity.CharacterEntityDto.CharacterEntityResponse::from));
            return mav;
        }
        List<CharacterEntity.CharacterEntityDto> characters = characterService.getCharacterDTOs(serverId, characterName);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), characters.size());
        Page<CharacterEntity.CharacterEntityDto> characterPage = new PageImpl<>(characters.subList(Math.min(start, end), end), pageable, characters.size());
        List<CompletableFuture<CharacterEntity.CharacterEntityDto>> list = new ArrayList<>();
        for (CharacterEntity.CharacterEntityDto character : characterPage) {
            if (character.getLevel() >= 100) {
                list.add(characterService.getCharacterAbilityAsync(character));
            } else {
                list.add(CompletableFuture.completedFuture(character));
            }
        }
        mav.addObject("characters", new PageImpl<>(list.stream().map(CompletableFuture::join).collect(Collectors.toList()), pageable, characters.size()));
        return mav;
    }

    @GetMapping("/characters/detail")
    public ModelAndView getCharacterDetails(@RequestParam String serverId,
                                            @RequestParam String characterId) {
        ModelAndView mav = new ModelAndView("/search/characters");
        mav.addObject("serverId", serverId);
        mav.addObject("characterId", characterId);
        CharacterBuffEquipmentJsonDto characterBuffEquipment = characterService.getCharacterBuffEquipment(serverId, characterId);
        CharacterEquipmentJsonDto characterEquipmentJsonDto= characterService.getCharacterEquipment(serverId, characterId);
        List<EquipmentDetailJsonDto> equipmentDetailJsonDtos = characterService.getEquipmentDetail(characterEquipmentJsonDto);
        mav.addObject("buffEquipment",characterBuffEquipment);
        mav.addObject("characterEquipment",characterEquipmentJsonDto );
        mav.addObject("characterAbility", CharacterEntity.CharacterEntityDto.CharacterEntityResponse.from(characterService.getCharacterAbility(serverId, characterId),serverId));
        mav.addObject("characterEquipmentDetails",characterService.getEquipmentDetail(characterEquipmentJsonDto));
        log.info("characterEquipmentJsonDto : {}",characterEquipmentJsonDto.getEquipment().size());
        mav.addObject("buffStatus", CharactersUtil.getBuffPercent(characterBuffEquipment,equipmentDetailJsonDtos));
        mav.addObject("buffAvatar",characterService.getCharacterBuffAvatar(serverId, characterId));
        mav.addObject("buffCreature",characterService.getCharacterBuffCreature(serverId, characterId));
        mav.addObject("characterAvatar",characterService.getCharacterAvatar(serverId, characterId));
        mav.addObject("characterTalisman",characterService.getCharacterTalisman(serverId, characterId));
        mav.addObject("boardCount",characterService.getBoardCountByCharacterId( characterId));
        mav.addObject("characterSkillStyle",characterService.getCharacterSkillStyle(serverId, characterId));
        mav.addObject("lastUpdated",characterService.getLastUpdatedByCharacterId(characterId));
        Long characterRank = characterService.getRankByCharacterId(characterId);
        Long characterRankByJobName = characterService.getRankByCharacterIdAndJobName(characterId, characterBuffEquipment.getJobName());
        Long characterCountByJobName = characterService.getCharacterCountByJobName(characterBuffEquipment.getJobName());
        Long characterCount = characterService.getCharacterCount();
        String characterPercent = String.format("%.2f", (double) characterRank / characterCount * 100);
        String characterPercentByJobName = String.format("%.2f", (double) characterRankByJobName / characterCountByJobName * 100);
        mav.addObject("characterRank", characterRank);
        mav.addObject("characterRankByJobName", characterRankByJobName);
        mav.addObject("characterCountByJobName", characterCountByJobName);
        mav.addObject("characterCount", characterCount);
        mav.addObject("characterPercent", characterPercent);
        mav.addObject("characterPercentByJobName", characterPercentByJobName);
        return mav;
    }





}
