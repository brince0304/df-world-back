package com.dfoff.demo.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    private final String REDIS_KEY_LIKE_LOG = ": LIKE_LOG :";

    private final String REDIS_KEY_VIEW_LOG = ": VIEW_LOG :";

    private final String REDIS_KEY_BOARD="BOARD :";

    private final String REDIS_KEY_BOARD_COMMENT="BOARD_COMMENT :";

    private final Long REDIS_KEY_EXPIRE_TIME = 60*60*24L*7L;

    public void saveLikeLog(String ipAddress,Long boardId){
        redisTemplate.opsForValue().set( REDIS_KEY_BOARD+boardId+REDIS_KEY_LIKE_LOG+ipAddress,ipAddress,REDIS_KEY_EXPIRE_TIME,TimeUnit.SECONDS);
    }

    public void deleteLikeLog(String ipAddress,Long boardId){
        redisTemplate.delete(REDIS_KEY_BOARD+boardId+REDIS_KEY_LIKE_LOG+ipAddress);
    }

    public void saveViewLog(String ipAddress,Long boardId){
        redisTemplate.opsForValue().set( REDIS_KEY_BOARD+boardId+REDIS_KEY_VIEW_LOG+ipAddress,ipAddress,REDIS_KEY_EXPIRE_TIME,TimeUnit.SECONDS);
    }

    public boolean checkLikeLog(String ipAddress,Long boardId){
        return Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_KEY_BOARD + boardId + REDIS_KEY_LIKE_LOG + ipAddress));
    }

    public boolean checkViewLog(String ipAddress,Long boardId){
        return Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_KEY_BOARD + boardId + REDIS_KEY_VIEW_LOG + ipAddress));
    }

    public void set(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, String value, Long expireTime){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }


    public void setExpired(String key, String value, long expired){
        redisTemplate.opsForValue().set(key,value,expired);
        redisTemplate.expire(key,expired, TimeUnit.SECONDS);
    }



}
