package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.JsonDtos.CharacterBuffEquipmentJsonDto;
import com.dfoff.demo.Domain.JsonDtos.CharacterEquipmentJsonDto;
import com.dfoff.demo.Domain.JsonDtos.CharacterSkillStyleJsonDto;
import com.dfoff.demo.Domain.JsonDtos.EquipmentDetailJsonDto;
import com.dfoff.demo.Repository.CharacterEntityRepository;
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
                                            @PageableDefault(size = 12) Pageable pageable) throws InterruptedException {
        ModelAndView mav = new ModelAndView("/search/searchPage");
        if (serverId == null || characterName == null) {
            mav.addObject("characters", Page.empty());
            return mav;
        }
        mav.addObject("serverId", serverId);
        mav.addObject("characterName", characterName);
        if (serverId.equals("adventure")) {
            mav.addObject("characters", characterService.getCharacterByAdventureName(characterName, pageable));
            return mav;
        }
        List<CharacterEntity.CharacterEntityDto> characters = characterService.getCharacterDtos(serverId, characterName).join();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), characters.size());
        List<CompletableFuture<CharacterEntity.CharacterEntityDto>> list = new ArrayList<>();
        Page<CharacterEntity.CharacterEntityDto> characterPage = new PageImpl<>(characters.subList(Math.min(start, end), end), pageable, characters.size());
                characterPage.forEach(o-> {
                    if (o.getLevel() >= 100) {
                        try {
                            list.add(characterService.getCharacterAbilityAsync(o));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else list.add(CompletableFuture.completedFuture(o));
                });
        mav.addObject("characters", new PageImpl<>(list.stream().map(CompletableFuture::join).collect(Collectors.toList()), pageable, characters.size()));
        return mav;
    }

    @GetMapping("/characters/detail")
    public ModelAndView getCharacterDetails(@RequestParam String serverId,
                                            @RequestParam String characterId) throws InterruptedException {
        ModelAndView mav = new ModelAndView("/search/characters");
        CharacterBuffEquipmentJsonDto characterBuffEquipment = characterService.getCharacterBuffEquipment(serverId, characterId).join();
        Long characterRank = characterService.getRankByCharacterId(characterId);
        Long characterRankByJobName = characterService.getRankByCharacterIdAndJobName(characterId, characterBuffEquipment.getJobName());
        Long characterCountByJobName = characterService.getCharacterCountByJobName(characterBuffEquipment.getJobName());
        Long characterCount = characterService.getCharacterCount();
        String characterRankPercent = String.format("%.2f", (double) characterRank / characterCount * 100);
        String characterRankPercentByJobName = String.format("%.2f", (double) characterRankByJobName / characterCountByJobName * 100);
        CharacterEquipmentJsonDto characterEquipmentJsonDto= characterService.getCharacterEquipment(serverId, characterId).join();
        CharacterSkillStyleJsonDto.Style characterSkillStyle = characterService.getCharacterSkillStyle(serverId, characterId).join();
        List<EquipmentDetailJsonDto> equipmentDetailJsonDtos = new ArrayList<>();
        for (CharacterEquipmentJsonDto.Equipment o : characterEquipmentJsonDto.getEquipment()) {
            equipmentDetailJsonDtos.add(characterService.getEquipmentDetail(o).join());
        }
        mav.addObject("serverId", serverId);
        mav.addObject("characterId", characterId);
        mav.addObject("buffEquipment",characterBuffEquipment);
        mav.addObject("characterEquipment",characterEquipmentJsonDto );
        mav.addObject("characterAbility", CharacterEntity.CharacterEntityDto.CharacterEntityResponse.from(characterService.getCharacterAbility(serverId, characterId).join(),serverId));
        mav.addObject("characterEquipmentDetails",equipmentDetailJsonDtos);
        mav.addObject("buffStatus", CharactersUtil.getBuffPercent(characterBuffEquipment,equipmentDetailJsonDtos));
        mav.addObject("buffAvatar",characterService.getCharacterBuffAvatar(serverId, characterId).join());
        mav.addObject("buffCreature",characterService.getCharacterBuffCreature(serverId, characterId).join());
        mav.addObject("characterAvatar",characterService.getCharacterAvatar(serverId, characterId).join());
        mav.addObject("characterTalisman",characterService.getCharacterTalisman(serverId, characterId).join());
        mav.addObject("boardCount",characterService.getBoardCountByCharacterId( characterId));
        mav.addObject("characterSkillStyle",characterSkillStyle);
        mav.addObject("lastUpdated",characterService.getLastUpdatedByCharacterId(characterId));
        mav.addObject("characterRank", characterRank);
        mav.addObject("characterRankByJobName", characterRankByJobName);
        mav.addObject("characterCountByJobName", characterCountByJobName);
        mav.addObject("characterCount", characterCount);
        mav.addObject("characterPercent", characterRankPercent);
        mav.addObject("characterPercentByJobName", characterRankPercentByJobName);
        return mav;
    }





}
