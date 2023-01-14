package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.BoardService;
import com.dfoff.demo.Service.SaveFileService;
import io.github.furstenheim.CopyDown;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final SaveFileService saveFileService;
    @GetMapping("/board.df")
    public ModelAndView getBoardList(@RequestParam (required = false) BoardType boardType,
                                     @RequestParam (required = false) String keyword,
                                     @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam (required = false) String searchType) {
        ModelAndView mav = new ModelAndView("/board/boardList");
        mav.addObject("articles",boardService.getBoardsByKeyword(boardType,keyword,searchType,pageable).map(Board.BoardResponse::from));
        mav.addObject("bestArticles",boardService.getBestBoard(boardType).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
        if(boardType!=null) {
            mav.addObject("boardType", boardType.toString());
        }
        mav.addObject("keyword",keyword);
        mav.addObject("searchType",searchType);
        return mav;
    }

    @GetMapping("/board/{boardId}.df")
    public ModelAndView getBoardDetails(@PathVariable Long boardId) {
        try {
            ModelAndView mav = new ModelAndView("/board/boardDetails");
            Board.BoardResponse boardResponse = Board.BoardResponse.from(boardService.getBoardDetail(boardId));
            mav.addObject("article", boardResponse);
            mav.addObject("boardType", boardResponse.getBoardType().toString());
            mav.addObject("bestArticles", boardService.getBestBoard(null).stream().map(Board.BoardResponse::from).collect(Collectors.toList()));
            return mav;
        }
        catch (Exception e){
            return new ModelAndView("redirect:/board.df");
        }
    }


    @GetMapping("/board/insert.df")
    public ModelAndView getBoardInsert(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                       @RequestParam (required = true) String request,
                                       @RequestParam (required = false) String id,
                                       Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ModelAndView("redirect:/board.df");
        }else if(request==null){
            return new ModelAndView("redirect:/board.df");
        }
        ModelAndView mav = new ModelAndView("/board/boardInsert");
        if(request.equals("add")){
            mav.addObject("boardRequest", boardRequest);
            mav.addObject("requestType",request);
        }else if(request.equals("update")){
            Board.BoardResponse boardResponse = Board.BoardResponse.from(boardService.getBoardDetail(Long.parseLong(id)));
            mav.addObject("boardResponse", boardResponse);
            mav.addObject("requestType",request);
        }
        return mav;
    }

    @DeleteMapping("/api/board.df")
public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                      @RequestParam (required = true) Long id) {
        try {
            Board.BoardDto boardDto = boardService.getBoardDetail(id);
            if (principalDto == null || !boardDto.getUserAccount().userId().equals(principalDto.getUsername())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            boardDto.getBoardFiles().forEach(saveFile -> {
                log.info("saveFile : {}", saveFile);
                saveFileService.deleteFile(saveFile.id());
            });
            boardService.deleteBoardById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/board.df")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody Board.BoardRequest boardRequest) {
        if(principalDto==null){
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(boardRequest);
        return new ResponseEntity<>(boardService.createBoard(Board.BoardDto.from(boardRequest, UserAccount.UserAccountDto.from(principalDto)),set).getId(),HttpStatus.OK);
    }

    @PutMapping("/api/board.df")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody Board.BoardRequest updateRequest) {
        try{
        Board.BoardDto dto_ = boardService.getBoardDetail(updateRequest.getId());
        if(principalDto==null || !dto_.getUserAccount().userId().equals(principalDto.getUsername())){
            return new ResponseEntity<>("로그인이 필요하거나 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }
        Set<SaveFile.SaveFileDTO> set = saveFileService.getFileDtosFromRequestsFileIds(updateRequest);
        return new ResponseEntity<>(boardService.updateBoard(updateRequest.getId(),Board.BoardDto.from(updateRequest, UserAccount.UserAccountDto.from(principalDto)),set).getId(),HttpStatus.OK);
    }catch (Exception e){
            log.error("error : {}",e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
}
