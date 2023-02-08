package com.dfoff.demo.aspect;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Aspect
@Component
@Slf4j
public class BoardAspect {
    private final BoardService boardService;

    public BoardAspect(@Autowired BoardService boardService) {
        this.boardService = boardService;
    }

    @Pointcut("@annotation(com.dfoff.demo.annotation.BoardCheck)")
    public void board() {
    }

    //게시글 삭제 혹은 수정 전에 작성자와 요청자가 같은지 확인
    @Before(value = "board()" )
    public void boardCheck(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        for(Object arg : args){
            if(arg instanceof String request){
                if(request.equals("add")){
                    return;
                }
            }
            if(arg instanceof Board.BoardRequest board){
                String userId = boardService.getBoardAuthorById(board.id());
                if (!Objects.equals(userId, principal)) {
                    throw new IllegalAccessException("수정 권한이 없습니다.");
                }
            }
            if(arg instanceof Long id){
                String userId = boardService.getBoardAuthorById(id);
                if (!Objects.equals(userId,principal)) {
                    throw new IllegalAccessException("접근 권한이 없습니다.");
                }
            }
        }
    }

}
