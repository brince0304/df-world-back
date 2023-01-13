package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.BoardRepository;
import com.dfoff.demo.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@RestController
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

    @GetMapping("/board/insert.df")
    public ModelAndView getBoardInsert(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ModelAndView("redirect:/main.df");
        }
        ModelAndView mav = new ModelAndView("/board/boardInsert");
        mav.addObject("boardRequest",boardRequest);
        return mav;
    }

    @PostMapping("/api/board.df")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(boardService.createArticle(Board.BoardDto.from(boardRequest,UserAccount.UserAccountDto.from(principalDto))).getId(),HttpStatus.OK);

    }
}
