package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.CharacterEntityDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterDTO;
import com.dfoff.demo.Service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/character/search.df")
    public ModelAndView searchCharacterInfo(@RequestParam(required = false) String serverId,
                                            @RequestParam(required = false) String characterName,
                                            @PageableDefault(size = 10) Pageable pageable) throws ExecutionException, InterruptedException {
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

        List<CharacterEntityDto> characters = characterService.getCharacterDTOs(serverId, characterName);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), characters.size());
        Page<CharacterEntityDto> characterPage = new PageImpl<>(characters.subList(start, end), pageable, characters.size());
        List<CompletableFuture<CharacterEntityDto>> list = new ArrayList<>();
        for (CharacterEntityDto character : characterPage) {
            if (character.getLevel() >= 100) {
                list.add(characterService.getCharacterAbilityThenSaveAsync(character));
            } else {
                list.add(CompletableFuture.completedFuture(character));
            }
        }
        mav.addObject("characters", new PageImpl<>(list.stream().map(CompletableFuture::join).collect(Collectors.toList()), pageable, characters.size()));
        return mav;
    }


}
