package com.dfoff.demo.Controller;


import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Service.BoardService;
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
    private final BoardService boardService;

    @GetMapping("/main.df")
    public ModelAndView main(@PageableDefault(size=5,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) throws ParseException {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("articles",boardService.getArticlesByKeyword(null,null,null,pageable).map(Board.BoardResponse::from));
        return mav;
    }

    @GetMapping("/")
    public ModelAndView index() throws ParseException {
        return new ModelAndView("redirect:/main.df");
    }

}
