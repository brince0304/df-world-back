package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public Board.BoardDto createBoard(Board.BoardDto board, Set<SaveFile.SaveFileDTO> saveFile) {
        log.info("createArticle() board: {}", board);
        log.info("createArticle() saveFile: {}", saveFile);
        Board board_ = boardRepository.save(board.toEntity());
        saveFile.stream().map(SaveFile.SaveFileDTO::toEntity).forEach(o-> board_.getBoardFiles().add(o));
        return Board.BoardDto.from(board_);
    }
    public Board.BoardDto getBoardDetail(Long id){
        Board board_ = boardRepository.findBoardById(id);
        if(board_==null){throw new EntityNotFoundException("게시글이 존재하지 않습니다.");}
        return Board.BoardDto.from(board_);
    }

    public Page<Board.BoardDto> getBoardsByKeyword(BoardType boardType, String keyword, String searchType, Pageable pageable) {
        if (boardType==null&&keyword == null) {
            return boardRepository.findAll(pageable).map(Board.BoardDto::from);
        } else if (boardType==null&&searchType.equals("title")) {
            return boardRepository.findAllByBoardTitleContainingIgnoreCase(keyword, pageable).map(Board.BoardDto::from);
        } else if (boardType==null&&searchType.equals("content")) {
            return boardRepository.findAllByBoardContentContainingIgnoreCase(keyword, pageable).map(Board.BoardDto::from);
        }else if(boardType==null&&searchType.equals("nickname")){
            return boardRepository.findAllByUserAccountContainingIgnoreCase(keyword, pageable).map(Board.BoardDto::from);
        }else if(boardType==null&&searchType.equals("title_content")) {
            return boardRepository.findAllByBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(keyword, pageable).map(Board.BoardDto::from);
        }else if(boardType!=null&&keyword==null){
            return boardRepository.findAllByBoardType(boardType, pageable).map(Board.BoardDto::from);
        }else if(boardType != null){
            switch (searchType) {
                case "title" -> {
                    return boardRepository.findAllByTypeAndBoardTitleContainingIgnoreCase(boardType, keyword, pageable).map(Board.BoardDto::from);
                }
                case "content" -> {
                    return boardRepository.findAllByTypeAndBoardContentContainingIgnoreCase(boardType, keyword, pageable).map(Board.BoardDto::from);
                }
                case "nickname" -> {
                    return boardRepository.findAllByTypeAndUserAccountContainingIgnoreCase(boardType, keyword, pageable).map(Board.BoardDto::from);
                }
                case "title_content" -> {
                    return boardRepository.findAllByTypeAndBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCase(boardType, keyword, pageable).map(Board.BoardDto::from);
                }
            }
        }
        return boardRepository.findAll(pageable).map(Board.BoardDto::from);
    }

    public List<Board.BoardDto> getBestBoard(BoardType boardType){
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(2);
        if(boardType==null){
            return boardRepository.findBoardByLikeCount(start,end).stream().map(Board.BoardDto::from).toList();
        }else{
            return boardRepository.findBoardByLikeCountAndBoardType(boardType,start,end).stream().map(Board.BoardDto::from).toList();
        }
    }

    public Board.BoardDto updateBoard(Long id, Board.BoardDto board , Set<SaveFile.SaveFileDTO> fileDtos) {
       Board board_ =  boardRepository.findBoardById(id);
       if(board_==null){throw new EntityNotFoundException("게시글이 존재하지 않습니다.");}
       if(board != null){
           if(board.getBoardTitle()!=null){
               board_.setBoardTitle(board.getBoardTitle());
           }
              if(board.getBoardContent()!=null){
                board_.setBoardContent(board.getBoardContent());
              }
       }
       fileDtos.stream().map(SaveFile.SaveFileDTO::toEntity).forEach(o-> board_.getBoardFiles().add(o));
       return Board.BoardDto.from(board_);

    }

    public void deleteBoardById(Long id){
        boardRepository.deleteBoardById(id);
    }
}
