package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.ForDFCharacter.*;
import com.dfoff.demo.Repository.Character.DFCharacterRepository;
import com.dfoff.demo.Repository.Character.DFJobGrowRepository;
import com.dfoff.demo.Repository.Character.DFJobRepository;
import com.dfoff.demo.Repository.Character.DFServerRepository;
import com.dfoff.demo.Util.OpenAPIUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
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

import static com.dfoff.demo.Util.OpenAPIUtil.parseDFJobDTO;
import static com.dfoff.demo.Util.OpenAPIUtil.parseUtil;

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
        DFServer.DFServerDTO dfServerDTO = parseUtil("https://api.neople.co.kr/df/servers?apikey=<apikey>", DFServer.DFServerDTO.class);
        List<DFServer.DFServerDTO.Row> rows = dfServerDTO.getRows();
        List<DFServer> list = new ArrayList<>();
        for (DFServer.DFServerDTO.Row row : rows) {
            DFServer dfServer = DFServer.builder()
                    .serverId(row.getServerId())
                    .serverName(row.getServerName())
                    .build();
            list.add(dfServer);
        }
        dfServerRepository.saveAll(list);
    }

    public void getJobList() {
        DFJob.DFJobDTO dfJobDto = parseUtil("https://api.neople.co.kr/df/jobs?apikey=<apikey>", DFJob.DFJobDTO.class);
        Map<List<DFJob>, List<DFJobGrow>> map = parseDFJobDTO(dfJobDto);
        if (map.keySet().isEmpty()) {
            return;
        }
        dfJobRepository.saveAll(map.keySet().iterator().next());
        dfJobGrowRepository.saveAll(map.values().iterator().next());

    }


}
