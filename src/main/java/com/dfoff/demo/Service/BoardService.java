package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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
