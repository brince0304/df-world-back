package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterDTO;
import com.dfoff.demo.Service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService CharacterService;

    @GetMapping("/api/character/search.df")
    public Map<Integer,List<CharacterDTO>> searchCharacterInfo(@RequestParam(required = false) String serverId,
                                      @RequestParam(required = false) String characterName,
                                      @RequestParam(required = true) int page
            , ModelMap map) {
        log.info("searchCharacterInfo: {}", map);
        return CharacterService.getCharacterList(serverId, characterName, page);
    }

    @GetMapping("/api/df/update.df")
    public ModelAndView getServerAndJobListForUpdate() {
        try {
            CharacterService.getJobList();
            CharacterService.getServerStatus();
            return new ModelAndView("redirect:/main.df");
        } catch (Exception e) {
            log.error("error", e);
            return new ModelAndView("redirect:/main.df");
        }
    }
}
