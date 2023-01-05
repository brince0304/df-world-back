package com.dfoff.demo.Util;

import com.dfoff.demo.Domain.ForDFCharacter.DFJob;
import com.dfoff.demo.Domain.ForDFCharacter.DFJobGrow;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class OpenAPIUtil {
    public static final String API_KEY = "qQpswERaNSg1ifEA7rbze6oNJrej4JJW";
    private static final Gson gson = getGson();


    @Bean
    public static Gson getGson() {
        return new Gson();
    }

    public static <T> T parseUtil(String url, Class<T> clazz) {
        HashMap<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url.replace("<apikey>", API_KEY)).build();
        ResponseEntity<?> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
        return gson.fromJson(response.getBody().toString(), clazz);
    }

    public static HashMap<List<DFJob>, List<DFJobGrow>> parseDFJobDTO(DFJob.DFJobDTO dfJobDTO) {
        HashMap<List<DFJob>, List<DFJobGrow>> result = new HashMap<>();
        List<DFJob> dfJobList = new ArrayList<>();
        List<DFJobGrow> dfJobGrowList = new ArrayList<>();
        for (DFJob.DFJobDTO.Row row : dfJobDTO.getRows()) {
            DFJob dfJob = DFJob.builder().jobId(row.getJobId()).jobName(row.getJobName()).build();
            dfJobList.add(dfJob);
            for (DFJob.DFJobDTO.Row__1 row__1 : row.getRows()) {
                dfJobGrowList.add(DFJobGrow.builder().jobName(dfJob).jobGrowId(row__1.getJobGrowId()).jobGrowName(row__1.getJobGrowName()).build());
                if (row__1.getNext() != null) {
                    DFJob.DFJobDTO.Next next = row__1.getNext();
                    dfJobGrowList.add(DFJobGrow.builder().jobName(dfJob).jobGrowId(next.getJobGrowId()).jobGrowName(next.getJobGrowName()).build());
                    if (next.getNext() != null) {
                        DFJob.DFJobDTO.Next__1 next1 = next.getNext();
                        dfJobGrowList.add(DFJobGrow.builder().jobName(dfJob).jobGrowId(next1.getJobGrowId()).jobGrowName(next1.getJobGrowName()).build());
                        if (next1.getNext() != null) {
                            DFJob.DFJobDTO.Next__2 next2 = next1.getNext();
                            dfJobGrowList.add(DFJobGrow.builder().jobName(dfJob).jobGrowId(next2.getJobGrowId()).jobGrowName(next2.getJobGrowName()).build());
                        }
                    }
                }
            }
        }
        result.put(dfJobList, dfJobGrowList);
        return result;
    }
}
