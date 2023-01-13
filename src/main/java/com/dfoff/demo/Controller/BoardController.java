package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Repository.BoardRepository;
import com.dfoff.demo.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/board.df")
    public ModelAndView getBoardList(@RequestParam (required = false) BoardType boardType,
                                     @RequestParam (required = false) String keyword,
                                     @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam (required = false) String searchType) {
        ModelAndView mav = new ModelAndView("/board/boardList");
        mav.addObject("articles",boardService.getArticlesByKeyword(boardType,keyword,searchType,pageable).map(Board.BoardResponse::from));
        mav.addObject("bestArticles",boardService.getBestArticles(boardType).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
        if(boardType!=null) {
            mav.addObject("boardType", boardType.toString());
        }
        mav.addObject("keyword",keyword);
        mav.addObject("searchType",searchType);
        return mav;
    }
}
