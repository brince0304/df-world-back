package com.dfoff.demo.aspect;

import com.dfoff.demo.domain.BoardComment;
import com.dfoff.demo.service.BoardCommentService;
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
public class BoardCommentAspect {

    private final BoardCommentService boardCommentService;

    public BoardCommentAspect(@Autowired BoardCommentService boardCommentService) {
        this.boardCommentService = boardCommentService;
    }

    @Pointcut("@annotation(com.dfoff.demo.annotation.CommentCheck)")
    public void boardComment() {
    }
    @Before(value = "boardComment()" )
    public void boardCheck(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        for(Object arg : args){
            if(arg instanceof BoardComment.BoardCommentRequest boardCommentRequest){
                String userId = boardCommentService.getCommentAuthor(boardCommentRequest.commentId());
                if (!Objects.equals(userId, name)) {
                    throw new IllegalAccessException("수정 권한이 없습니다.");
                }
            }
            if(arg instanceof Long id){
                String userId = boardCommentService.getCommentAuthor(id);
                if (!Objects.equals(userId, name)) {
                    throw new IllegalAccessException("삭제 권한이 없습니다.");
                }
            }
        }
    }
}
