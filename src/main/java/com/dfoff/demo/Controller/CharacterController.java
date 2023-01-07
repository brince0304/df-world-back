package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterDTO;
import com.dfoff.demo.Service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService CharacterService;

    @GetMapping("/character/search.df")
    public ModelAndView searchCharacterInfo(@RequestParam(required = false) String serverId,
                                      @RequestParam(required = false) String characterName,
                                      @RequestParam(required = false) String page) {
        ModelAndView mav = new ModelAndView();
        if(page==null){
            page="1";
        }
        mav.addObject("maxPage",5);
        mav.addObject("thisPage",page);
        mav.addObject("sliceNumber",(Integer.parseInt(page)/5)+1);
        if(serverId==null || serverId.equals("") || characterName==null || characterName.equals("")){
            mav.addObject("resultSize",1);
            mav.setViewName("/search/searchPage");
            return mav;
        }
        Map<Integer,List<CharacterDTO>> map = CharacterService.getCharacterAbilityList(serverId,characterName,page);
        mav.addObject("serverId",serverId);
        mav.addObject("characterName",characterName);
        mav.addObject("characterResults",map.values().stream().findFirst().get());
        mav.addObject("resultSize",map.keySet().stream().findFirst().get());
        mav.setViewName("/search/searchPage");
        return mav;
    }





}
