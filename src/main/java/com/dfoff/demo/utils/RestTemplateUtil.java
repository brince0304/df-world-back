package com.dfoff.demo.utils;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RestTemplateUtil {
    public static String API_KEY = "qQpswERaNSg1ifEA7rbze6oNJrej4JJW";
    private static final Gson gson = getGson();

    public static final String LIMIT = "100";

    public static final String WORD_TYPE = "full";

    public static final String SERVER_LIST_URI = "https://api.neople.co.kr/df/servers?apikey=" + API_KEY;

    public static final String JOB_LIST_URI = "https://api.neople.co.kr/df/jobs?apikey=" + API_KEY;

    public static final String CHARACTER_IMG_URI = "https://img-api.neople.co.kr/df/servers/<serverId>/characters/<characterId>?zoom=<zoom>";

    public static final String CHARACTER_SEARCH_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters?characterName=<characterName>&jobId=<jobId>&jobGrowId=<jobGrowId>&limit="+LIMIT+"&wordType="+WORD_TYPE+"&apikey="+API_KEY;

    public static final String CHARACTER_DETAILS_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>?apikey="+API_KEY;

    public static final String CHARACTER_ABILITY_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/status?apikey="+API_KEY;

    public static final String CHARACTER_EQUIPMENT_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/equip/equipment?apikey="+API_KEY;

    public static final String EQUIPMENT_IMG_URI = "https://img-api.neople.co.kr/df/items/<itemId>?zoom=<zoom>";

    public static final String EQUIPMENT_DETAIL_URI = "https://api.neople.co.kr/df/items/<itemId>?apikey="+ API_KEY;

    public static final String CHARACTER_BUFF_EQUIPMENT_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/skill/buff/equip/equipment?apikey="+API_KEY;

    public static final String CHARACTER_BUFF_AVATAR_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/skill/buff/equip/avatar?apikey="+API_KEY;

    public static final String CHARACTER_BUFF_CREATURE_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/skill/buff/equip/creature?apikey="+API_KEY;

        public static final String CHARACTER_AVATAR_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/equip/avatar?apikey="+API_KEY;

        public static final String CHARACTER_TALISMAN_URI ="https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/equip/talisman?apikey="+API_KEY;

        public static final String CHARACTER_SKILL_URI = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/skill/style?apikey="+API_KEY;

        public static final String CHARACTER_SKILL_DETAIL_URI = "https://api.neople.co.kr/df/skills/<jobId>/<skillId>?apikey="+API_KEY;
    @Bean
    public static Gson getGson() {
        return new Gson();
    }


    public static String getCharacterSkillDetailUri(String jobId, String skillId) {
        return CHARACTER_SKILL_DETAIL_URI.replace("<jobId>", jobId).replace("<skillId>", skillId );
    }
    public static String getEquipmentDetailUri(String itemId) {
        return EQUIPMENT_DETAIL_URI.replace("<itemId>", itemId);
    }

    public static String getCharacterTalismanUri(String serverId, String characterId) {
        return CHARACTER_TALISMAN_URI.replace("<serverId>", serverId).replace("<characterId>", characterId);
    }

    public static String getCharacterSkillUri(String serverId, String characterId) {
        return CHARACTER_SKILL_URI.replace("<serverId>", serverId).replace("<characterId>", characterId);
    }


    public static String getCharacterSearchUri(String serverId, String characterName) {
        return CHARACTER_SEARCH_URI.replace("<serverId>", serverId)
                .replace("<characterName>", characterName).replace("<jobId>", "").replace("<jobGrowId>", "");
    }

    public static String getCharacterBuffAvatarUri(String serverId, String characterId) {
        return CHARACTER_BUFF_AVATAR_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterBuffCreatureUri(String serverId, String characterId) {
        return CHARACTER_BUFF_CREATURE_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterAbilityUri(String serverId, String characterId) {
        return CHARACTER_ABILITY_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterAvatarUri(String serverId, String characterId) {
        return CHARACTER_AVATAR_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterBuffEquipmentUri(String serverId, String characterId) {
        return CHARACTER_BUFF_EQUIPMENT_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterEquipmentUri(String serverId, String characterId) {
        return CHARACTER_EQUIPMENT_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getEquipmentImgUri(String itemId) {
        return EQUIPMENT_IMG_URI.replace("<itemId>", itemId);
    }

    public static String getCharacterDetailsUri(String serverId, String characterId) {
        return CHARACTER_DETAILS_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }
    public static String getCharacterImgUri(String serverId, String characterId, String zoom) {
        return CHARACTER_IMG_URI.replace("<serverId>", serverId)
                .replace("<characterId>", characterId)
                .replace("<zoom>", zoom);
    }

    public static <T> T parseJsonFromUri(String url, Class<T> clazz) throws InterruptedException {
        HashMap<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<?> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
        if(response.getStatusCode() == HttpStatus.BAD_REQUEST){
            TimeUnit.MILLISECONDS.sleep(500);
            return parseJsonFromUri(url, clazz);
        }if(response.getStatusCode().is5xxServerError()){
            throw new IllegalArgumentException("서버가 점검중이거나 장애가 발생하였습니다.");
        }if(response.getStatusCode().is4xxClientError()){
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
        return gson.fromJson(Objects.requireNonNull(response.getBody()).toString(), clazz);
    }


}
