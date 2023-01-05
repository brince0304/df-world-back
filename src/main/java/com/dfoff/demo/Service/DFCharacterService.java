package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.ForDFCharacter.DFJob;
import com.dfoff.demo.Domain.ForDFCharacter.DFJobGrow;
import com.dfoff.demo.Domain.ForDFCharacter.DFServer;
import com.dfoff.demo.Repository.Character.DFCharacterRepository;
import com.dfoff.demo.Repository.Character.DFJobGrowRepository;
import com.dfoff.demo.Repository.Character.DFJobRepository;
import com.dfoff.demo.Repository.Character.DFServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DFCharacterService {
    private final DFJobRepository dfJobRepository;
    private final DFJobGrowRepository dfJobGrowRepository;
    private final DFServerRepository dfServerRepository;
    private final DFCharacterRepository DFCharacterRepository;

    public void getServerStatus() {
        ResponseEntity<?> resultMap = parseUtil("https://api.neople.co.kr/df/servers?apikey=qQpswERaNSg1ifEA7rbze6oNJrej4JJW");
        int count = 0;
        String str1 = resultMap.getBody().toString();
        str1 = str1.substring(7, str1.length() - 2).replaceAll("\\{", "").replaceAll("=", "").replaceAll("\\}", "")
                .replaceAll(" ", "").replaceAll("serverId", "").replaceAll("serverName", "");
        log.info(str1);
        String[] str2 = str1.split(",");
        List<DFServer> list = new ArrayList<>();
        for (int i = 0; i < str2.length; i++) {
            list.add(DFServer.builder().serverId(str2[i]).serverName(str2[i + 1]).build());
            i++;
        }
        dfServerRepository.saveAll(list);
    }

    public void getJobList() {
        ResponseEntity<?> resultMap = parseUtil("https://api.neople.co.kr/df/jobs?apikey=qQpswERaNSg1ifEA7rbze6oNJrej4JJW");
        String str1 = resultMap.getBody().toString().replaceAll("rows=", "").replaceAll("next=", "").replaceAll("\\{", "")
                .replaceAll("\\}", "").replaceAll(" ", "").replaceAll("\\]", "").replaceAll("\\[", "");
        ArrayDeque<String> deque = new ArrayDeque<>(Arrays.asList(str1.split(",")));
        while (!deque.isEmpty()) {
            String jobId = deque.pollFirst();
            String jobName = deque.pollFirst();
            DFJob job = DFJob.builder().jobId(jobId.split("=")[1]).jobName(jobName.split("=")[1]).build();
            dfJobRepository.save(job);
            log.info(job.toString());
            List<DFJobGrow> list = new ArrayList<>();
            while (!deque.isEmpty() && !deque.peekFirst().contains("jobId")) {
                String jobGrowId = deque.pollFirst();
                String jobGrowName = deque.pollFirst();
                DFJobGrow jobGrow = DFJobGrow.builder().jobGrowId(jobGrowId.split("=")[1]).jobGrowName(jobGrowName.split("=")[1]).build();
                jobGrow.setJobName(job);
                list.add(jobGrow);
                log.info(jobGrow.toString());
            }
            dfJobGrowRepository.saveAll(list);
        }

    }

    public ResponseEntity<?> parseUtil(String url) {
        HashMap<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        return restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Object.class);
    }


}
