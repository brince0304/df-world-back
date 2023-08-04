package com.dfoff.demo.controller;


import com.dfoff.demo.service.AdventureService;
import com.dfoff.demo.service.BoardService;
import com.dfoff.demo.service.CharacterService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
public class MainController {
    @GetMapping("/")
    public String index() {
        return "hello";
    }

}
