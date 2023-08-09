package com.dfoff.demo.controller;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.domain.jsondtos.CharacterBuffEquipmentJsonDto;
import com.dfoff.demo.domain.jsondtos.CharacterEquipmentJsonDto;
import com.dfoff.demo.domain.jsondtos.EquipmentDetailJsonDto;
import com.dfoff.demo.service.AdventureService;
import com.dfoff.demo.service.CharacterService;
import com.dfoff.demo.utils.CharactersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class CharacterController {
    private final CharacterService characterService;
    private final AdventureService adventureService;
    private final CharactersUtil charactersUtil;

    @GetMapping("/characters/autoComplete")
    public ResponseEntity<?> autoCompleteSearch(@RequestParam String name, @RequestParam String serverId) {
        List<CharacterEntity.AutoCompleteResponse> characters = characterService.getAutocompeleteCharacters(serverId,name);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters/")
    public ResponseEntity<?> searchCharacterInfo(@RequestParam(required = false) String serverId,
                                            @RequestParam(required = false) String characterName,
                                            @PageableDefault(size = 12) Pageable pageable) throws InterruptedException {
        if (serverId == null || characterName == null) {
            return ResponseEntity.ok(Page.empty());
        }
        if (serverId.equals("adventure")) {
            return ResponseEntity.ok(characterService.getCharacterByAdventureName(characterName, pageable));
        }
        List<CharacterEntity.CharacterEntityDto> characters = characterService.getCharacterDtos(serverId, characterName).join();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), characters.size());
        List<CharacterEntity.CharacterEntityDto> list = new ArrayList<>();
        Page<CharacterEntity.CharacterEntityDto> characterPage = new PageImpl<>(characters.subList(Math.min(start, end), end), pageable, characters.size());
                characterPage.forEach(o-> {
                    if (o.getLevel() >= 110) {
                        try {
                            list.add(characterService.getCharacterAbility(o));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else list.add(o);
                });
        return ResponseEntity.ok(new PageImpl<>(list, pageable, characters.size()));
    }
    @GetMapping("/characters/rank/")
    public ModelAndView getCharacterRanking(@RequestParam (required = false) String searchType,@RequestParam (required = false) String characterName, @PageableDefault(size = 10) Pageable pageable) {
        ModelAndView mav = new ModelAndView("/search/ranking");
        if(searchType==null || searchType.equals("")){searchType="fame";}
        if(characterName==null){characterName="";}
        mav.addObject("searchType",searchType);
        mav.addObject("characterName",characterName);
        if(searchType.contains("adventure")){
            mav.addObject("response",adventureService.getAdventureRank(searchType,pageable,characterName));
                    return mav;
        }
        mav.addObject("response",characterService.getCharacterRanking(searchType, characterName, pageable));
        return mav;
    }

    @GetMapping("/characters/detail")
    public ResponseEntity<?> getCharacterDetails(@RequestParam String serverId,
                                            @RequestParam String characterId) throws InterruptedException {
        Map<String,Object> model = new HashMap<>();
        CharacterBuffEquipmentJsonDto characterBuffEquipment = characterService.getCharacterBuffEquipment(serverId, characterId).join();
        Long characterRank = characterService.getRankCountByCharacterIdOrderByAdventureFame(characterId);
        Long characterRankByJobName = characterService.getRankCountByCharacterIdAndJobNameOrderByAdventureFame(characterId, characterBuffEquipment.getJobName());
        Long characterCountByJobName = characterService.getCharacterCountByJobName(characterBuffEquipment.getJobName());
        Long characterCount = characterService.getCharacterCount();
        String characterRankPercent = String.format("%.2f", (double) characterRank / characterCount * 100);
        String characterRankPercentByJobName = String.format("%.2f", (double) characterRankByJobName / characterCountByJobName * 100);
        CharacterEquipmentJsonDto characterEquipmentJsonDto= characterService.getCharacterEquipment(serverId, characterId).join();
        List<EquipmentDetailJsonDto> equipmentDetailJsonDtos = new ArrayList<>();
        for (CharacterEquipmentJsonDto.Equipment o : characterEquipmentJsonDto.getEquipment()) {
            equipmentDetailJsonDtos.add(characterService.getEquipmentDetail(o).join());
        }
        model.put("characterEntityDto", characterService.getCharacter(serverId, characterId));
        model.put("characterEquipment",characterEquipmentJsonDto );
        model.put("characterAbility", CharacterEntity.CharacterEntityResponse.from(characterService.getCharacterAbilityAsync(serverId, characterId).join(),serverId));
        model.put("characterEquipmentDetails",equipmentDetailJsonDtos);
        model.put("buffStatus", charactersUtil.getBuffPercent(characterBuffEquipment,equipmentDetailJsonDtos));
        model.put("boardCount",characterService.getBoardCountByCharacterId( characterId));
        model.put("lastUpdated",characterService.getLastUpdatedByCharacterId(characterId));
        model.put("characterRank", characterRank);
        model.put("characterRankByJobName", characterRankByJobName);
        model.put("characterCountByJobName", characterCountByJobName);
        model.put("characterCount", characterCount);
        model.put("characterPercent", characterRankPercent);
        model.put("characterPercentByJobName", characterRankPercentByJobName);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/characters/mainRank")
    public ResponseEntity<?> getCharacterMainRanking(@RequestParam String searchType, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(characterService.getCharacterRanking(searchType, "", pageable));
    }





}
