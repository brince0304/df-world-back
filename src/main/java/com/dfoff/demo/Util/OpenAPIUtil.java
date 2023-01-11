package com.dfoff.demo.Util;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Slf4j
public class OpenAPIUtil {
    public static final String API_KEY = "qQpswERaNSg1ifEA7rbze6oNJrej4JJW";
    private static final Gson gson = getGson();

    public static final String LIMIT = "100";

    public static final String WORD_TYPE = "full";

    public static final String SERVER_LIST_URL = "https://api.neople.co.kr/df/servers?apikey=" + API_KEY;

    public static final String JOB_LIST_URL = "https://api.neople.co.kr/df/jobs?apikey=" + API_KEY;

    public static final String CHARACTER_IMG_URL = "https://img-api.neople.co.kr/df/servers/<serverId>/characters/<characterId>?zoom=<zoom>";

    public static final String CHARACTER_SEARCH_URL = "https://api.neople.co.kr/df/servers/<serverId>/characters?characterName=<characterName>&jobId=<jobId>&jobGrowId=<jobGrowId>&limit="+LIMIT+"&wordType="+WORD_TYPE+"&apikey="+API_KEY;

    public static final String CHARACTER_DETAILS_URL = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>?apikey="+API_KEY;

    public static final String CHARACTER_ABILITY_URL = "https://api.neople.co.kr/df/servers/<serverId>/characters/<characterId>/status?apikey="+API_KEY;



    @Bean
    public static Gson getGson() {
        return new Gson();
    }


    public static String getCharacterSearchUrl(String serverId, String characterName) {
        return CHARACTER_SEARCH_URL.replace("<serverId>", serverId)
                .replace("<characterName>", characterName).replace("<jobId>", "").replace("<jobGrowId>", "");
    }

    public static String getCharacterAbilityUrl(String serverId, String characterId) {
        return CHARACTER_ABILITY_URL.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }

    public static String getCharacterDetailsUrl(String serverId, String characterId) {
        return CHARACTER_DETAILS_URL.replace("<serverId>", serverId)
                .replace("<characterId>", characterId);
    }
    public static String getCharacterImgUrl(String serverId, String characterId, String zoom) {
        return CHARACTER_IMG_URL.replace("<serverId>", serverId)
                .replace("<characterId>", characterId)
                .replace("<zoom>", zoom);
    }

    public static <T> T parseUtil(String url, Class<T> clazz) {
        HashMap<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<?> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
        log.info("response: {}", response);
        log.info("url: {}", url);
        return gson.fromJson(Objects.requireNonNull(response.getBody()).toString(), clazz);
    }

    public static <T> List<T> getPage(List<T> sourceList, String page, int pageSize) {
        int pageNum = Integer.parseInt(page);
        if (pageSize <= 0 || pageNum <= 0) {
            return Collections.emptyList();
        }

        int fromIndex = (pageNum - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

}
