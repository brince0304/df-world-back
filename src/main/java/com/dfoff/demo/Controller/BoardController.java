package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.*;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.dfoff.demo.Controller.UserAccountController.getCharacterResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    private final SaveFileService saveFileService;

    private final CharacterService characterService;

    private final NotificationService notificationService;

    private final RedisService redisService;


    @GetMapping("/boards/")
    public ModelAndView getBoardList(@RequestParam(required = false) BoardType boardType,
                                     @RequestParam(required = false) String keyword,
                                     @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam(required = false) String searchType) {
        ModelAndView mav = new ModelAndView("/board/boardList");
        mav.addObject("articles", boardService.getBoardsByKeyword(boardType, keyword, searchType, pageable));
        mav.addObject("bestArticles", boardService.getBestBoardByBoardType(boardType));
        if (boardType != null) {
            mav.addObject("boardType", boardType.toString());
        }
        mav.addObject("keyword", keyword);
        mav.addObject("searchType", searchType);
        return mav;
    }

    @PostMapping("/boards/like-board")
    public ResponseEntity<?> likeBoardById(@RequestParam Long boardId, HttpServletRequest req, @AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        if (redisService.checkBoardLikeLog(req.getRemoteAddr(), boardId)) {
            redisService.deleteBoardLikeLog(req.getRemoteAddr(), boardId);

            return new ResponseEntity<>(boardService.decreaseBoardLikeCount(boardId).getBoardLikeCount(), HttpStatus.OK);

        } else {
            redisService.saveBoardLikeLog(req.getRemoteAddr(), boardId);
            Board.BoardDto dto = boardService.increaseBoardLikeCount(boardId);
            if (principal != null && !principal.getUsername().equals(dto.getUserAccount().userId())) {
                notificationService.saveBoardNotification(dto.getUserAccount(), dto, "", NotificationType.BOARD_LIKE);
            }
            return new ResponseEntity<>(dto.getBoardLikeCount(), HttpStatus.OK);
        }
    }


    @GetMapping("/boards/{boardId}")
    public ModelAndView getBoardDetail(@PathVariable Long boardId,
                                       HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("/board/boardDetails");
        if (!redisService.checkBoardViewLog(req.getRemoteAddr(), boardId)) {
            redisService.saveBoardViewLog(req.getRemoteAddr(), boardId);
            boardService.increaseBoardViewCount(boardId);
        }
        Board.BoardDetailResponse boardResponse = boardService.getBoardDetailById(boardId);
        mav.addObject("article", boardResponse);
        mav.addObject("boardType", boardResponse.getBoardType().toString());
        mav.addObject("bestArticles", boardService.getBestBoardByBoardType(null));
        mav.addObject("likeLog", redisService.checkBoardLikeLog(req.getRemoteAddr(), boardId));
        return mav;
    }


    @GetMapping("/boards/insert")
    public ModelAndView getBoardInsert(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                       @RequestParam(required = true) String request,
                                       @RequestParam(required = false) String id,
                                       Board.BoardRequest boardRequest) {
        if (principalDto == null) {
            throw new EntityNotFoundException("로그인이 필요합니다.");
        } else if (request == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        ModelAndView mav = new ModelAndView("/board/boardInsert");
        if (request.equals("add")) {
            mav.addObject("boardRequest", boardRequest);
            mav.addObject("requestType", request);
        } else if (request.equals("update")) {
            Board.BoardDetailResponse boardResponse = boardService.getBoardDetailById(Long.parseLong(id));
            mav.addObject("boardResponse", boardResponse);
            mav.addObject("requestType", request);
            StringBuilder sb = new StringBuilder();
            for (String hashtag : boardResponse.getHashtags()) {
                sb.append(",").append(hashtag);
            }
            mav.addObject("hashtag", sb);
            mav.addObject("characterExist", boardResponse.getCharacter() != null);
            if (boardResponse.getCharacter() != null) {
                mav.addObject("characterName", boardResponse.getCharacter().getCharacterName());
                mav.addObject("characterId", boardResponse.getCharacter().getCharacterId());
                mav.addObject("serverId", boardResponse.getCharacter().getServerId());
            }
        }
        return mav;
    }

    @GetMapping("/hashtags/")
    public List<String> getHashtags(@RequestParam String query) {
        return boardService.findHashtags(query);
    }

    @DeleteMapping("/boards")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                         @RequestParam Long id) {

        String writer = boardService.getBoardAuthorById(id);
        if (principalDto == null || !writer.equals(principalDto.getUsername())) {
            throw new SecurityException("권한이 없습니다.");
        }
        boardService.getBoardSaveFile(id).forEach(saveFile -> {
            log.info("saveFile : {}", saveFile);
            saveFileService.deleteFile(saveFile.id());
        });
        boardService.getBoardCommentsByBoardId(id).stream().filter(o -> !o.getUserId().equals(principalDto.getUsername())).forEach(o -> {
            notificationService.saveBoardCommentNotification(o.getUserAccountDto(), o, principalDto.getNickname(), NotificationType.DELETE_COMMENT);
        });
        boardService.deleteBoardById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/boards/characters/")
    public ResponseEntity<?> searchCharacterForBoard(@RequestParam(required = false) String serverId,
                                                     @RequestParam(required = false) String characterName,
                                                     @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable,
                                                     @AuthenticationPrincipal UserAccount.PrincipalDto principal) throws InterruptedException {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        if (serverId == null || characterName == null) {
            throw new IllegalArgumentException("서버 아이디, 캐릭터 아이디는 필수입니다.");
        }
        if (serverId.equals("adventure")) {
            return new ResponseEntity<>(characterService.getCharacterByAdventureName(characterName, pageable).map(CharacterEntity.CharacterEntityDto.CharacterEntityResponse::from).toList(), HttpStatus.OK);
        }
        List<CompletableFuture<CharacterEntity.CharacterEntityDto>> dtos = new ArrayList<>();
        List<CharacterEntity.CharacterEntityDto> dtos1 = characterService.getCharacterDtos(serverId, characterName).join();
        return getCharacterResponse(dtos, dtos1, characterService);
    }


    @PostMapping("/boards")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody @Valid Board.BoardRequest boardRequest, BindingResult bindingResult) {

        if (principalDto == null) {
            throw new SecurityException("권한이 없습니다.");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Set<SaveFile.SaveFileDto> set = saveFileService.getFileDtosFromRequestsFileIds(boardRequest);
        if (boardRequest.getServerId().equals("")) {
            return new ResponseEntity<>(boardService.createBoard(boardRequest, set, UserAccount.UserAccountDto.from(principalDto), null), HttpStatus.OK);
        }
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(boardRequest.getServerId(), boardRequest.getCharacterId()).join();
        return new ResponseEntity<>(boardService.createBoard(boardRequest, set, UserAccount.UserAccountDto.from(principalDto), character), HttpStatus.OK);
    }


    @PutMapping("/boards")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto, @RequestBody @Valid Board.BoardRequest updateRequest, BindingResult bindingResult) {

        String writer = boardService.getBoardAuthorById(updateRequest.getId());
        if (principalDto == null || !writer.equals(principalDto.getUsername())) {
            throw new SecurityException("권한이 없습니다.");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Set<SaveFile.SaveFileDto> set = saveFileService.getFileDtosFromRequestsFileIds(updateRequest);
        CharacterEntity.CharacterEntityDto character = characterService.getCharacter(updateRequest.getServerId(), updateRequest.getCharacterId()).join();
        return new ResponseEntity<>(boardService.updateBoard(updateRequest.getId(), updateRequest, set, character), HttpStatus.OK);
    }
}
