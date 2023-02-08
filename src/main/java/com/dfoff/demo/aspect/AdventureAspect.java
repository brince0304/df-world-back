package com.dfoff.demo.aspect;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.service.AdventureService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
public class AdventureAspect {

    private final AdventureService adventureService;

    public AdventureAspect(AdventureService adventureService) {
        this.adventureService = adventureService;
    }

    @Pointcut("@annotation(com.dfoff.demo.annotation.SaveAdventure)")
    public void saveAdventure() {
    }

    @After(value = "saveAdventure()&&args(serverId,characterId)", argNames = "joinPoint,serverId,characterId")
    public void saveAdventure(JoinPoint joinPoint, String serverId, String characterId) {
        Arrays.stream(joinPoint.getArgs()).forEach(arg -> log.info("Method arguments : " + arg));
        CompletableFuture.runAsync(() -> adventureService.saveAdventureByCharacterId(characterId));
    }

    @AfterReturning(value = "saveAdventure()", returning = "result")
    public void saveAdventure( Object result) {
        if (result instanceof CharacterEntity.CharacterEntityDto) {
            adventureService.saveAdventure((CharacterEntity.CharacterEntityDto) result);
        }
    }




}
