package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.EnumType.BoardType;
import com.dfoff.demo.Repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public void createArticle(Board.BoardDto board) {
        boardRepository.save(board.toEntity());
    }
    public Board.BoardDto getArticle(Long id){
        Board board_ = boardRepository.findBoardById(id);
        if(board_==null){throw new EntityNotFoundException("게시글이 존재하지 않습니다.");}
        return Board.BoardDto.from(board_);
    }

    public Page<Board.BoardDto> getArticlesByKeyword(BoardType boardType, String keyword, String searchType, Pageable pageable) {
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
        return Page.empty();
    }

    public List<Board.BoardDto> getBestArticles(BoardType boardType){
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(end.getYear(),end.getMonth(),end.getDayOfMonth()-1,23,59,59);
        if(boardType==null){
            return boardRepository.findBoardByLikeCount(start,end).stream().map(Board.BoardDto::from).toList();
        }else{
            return boardRepository.findBoardByLikeCountAndBoardType(boardType,start,end).stream().map(Board.BoardDto::from).toList();
        }
    }

    public void updateArticle(Long id, Board.BoardDto board,String userId) {
       Board board_ =  boardRepository.findBoardById(id);
       if(board_==null){throw new EntityNotFoundException("게시글이 존재하지 않습니다.");}
       if(!board_.getUserAccount().getUserId().equals(userId)){
           throw new IllegalArgumentException("수정 권한이 없습니다.");
       }
       if(board != null){
           if(board.getBoardTitle()!=null){
               board_.setBoardTitle(board.getBoardTitle());
           }
              if(board.getBoardContent()!=null){
                board_.setBoardContent(board.getBoardContent());
              }
       }

    }

    public void deleteArticleById(Long id){
        boardRepository.findById(id).ifPresent(board -> {
            board.setIsDeleted("Y");
        });
    }
}
