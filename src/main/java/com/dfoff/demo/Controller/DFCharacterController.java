package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.DFCharacter;
import com.dfoff.demo.Service.DFCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DFCharacterController {
    private final DFCharacterService DFCharacterService;

    @GetMapping("/api/character/search.df")
    public Page searchCharacterInfo(@RequestParam(required = false) String serverId,
                                    @RequestParam(required = false) String characterName,
                                    @PageableDefault(size = 10, direction = Sort.Direction.ASC) Pageable pageable
            , ModelMap map) {
        log.info("searchCharacterInfo: {}", map);
        Page<DFCharacter.DFCharacterDTO> page = DFCharacterService.getCharacterList(serverId, characterName, pageable);
        log.info(page.toString());
        return page;
    }

    @GetMapping("/api/df/update.df")
    public ModelAndView getServerAndJobListForUpdate() {
        try {
            DFCharacterService.getJobListAndSave();
            DFCharacterService.getServerStatusAndSave();
            return new ModelAndView("redirect:/main.df");
        } catch (Exception e) {
            log.error("error", e);
            return new ModelAndView("redirect:/main.df");
        }
    }
}
