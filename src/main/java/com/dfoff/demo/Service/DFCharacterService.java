package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.DFCharacter;
import com.dfoff.demo.Domain.ForDFCharacter.*;
import com.dfoff.demo.Repository.Character.DFCharacterRepository;
import com.dfoff.demo.Repository.Character.DFJobGrowRepository;
import com.dfoff.demo.Repository.Character.DFJobRepository;
import com.dfoff.demo.Repository.Character.DFServerRepository;
import com.dfoff.demo.Util.OpenAPIUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.dfoff.demo.Util.OpenAPIUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DFCharacterService {
    private final DFJobRepository dfJobRepository;
    private final DFJobGrowRepository dfJobGrowRepository;
    private final DFServerRepository dfServerRepository;
    private final DFCharacterRepository DFCharacterRepository;

    public void getServerStatusAndSave() {
        DFServer.DFServerJSONDTO dfServerJSONDTO = parseUtil(OpenAPIUtil.SERVER_LIST_URL, DFServer.DFServerJSONDTO.class);
        List<DFServer.DFServerJSONDTO.Row> rows = dfServerJSONDTO.getRows();
        List<DFServer> list = new ArrayList<>();
        for (DFServer.DFServerJSONDTO.Row row : rows) {
            DFServer dfServer = DFServer.builder()
                    .serverId(row.getServerId())
                    .serverName(row.getServerName())
                    .build();
            list.add(dfServer);
        }
        dfServerRepository.saveAll(list);
    }

    public void getJobListAndSave() {
        DFJob.DFJobJSONDTO dfJobJSONDTO = parseUtil(OpenAPIUtil.JOB_LIST_URL, DFJob.DFJobJSONDTO.class);
        Map<List<DFJob>, List<DFJobGrow>> map = parseDFJobDTO(dfJobJSONDTO);
        if (map.keySet().isEmpty()) {
            return;
        }
        dfJobRepository.saveAll(map.keySet().iterator().next());
        dfJobGrowRepository.saveAll(map.values().iterator().next());

    }

    public Page<DFCharacter.DFCharacterDTO> getCharacterList(String serverId, String characterName, Pageable pageable) {
        DFCharacter.DFCharacterJSONDTO dfCharacterJSONDTO = parseUtil(getCharacterSearchUrl(serverId, characterName), DFCharacter.DFCharacterJSONDTO.class);
        List<DFCharacter.DFCharacterJSONDTO.Row> rows = dfCharacterJSONDTO.getRows();
        for (DFCharacter.DFCharacterJSONDTO.Row row : rows) {
            DFCharacter dfCharacter = DFCharacter.builder()
                    .characterId(row.getCharacterId())
                    .characterName(row.getCharacterName())
                    .dfJob(dfJobRepository.findById(row.getJobName()).orElseThrow(EntityNotFoundException::new))
                    .dfJobGrow(dfJobGrowRepository.findById(row.getJobGrowName()).orElseThrow(EntityNotFoundException::new))
                    .characterLevel(row.getLevel())
                    .dfServer(dfServerRepository.getReferenceById((row.getServerId())))
                    .build();
            log.info("dfCharacter : {}", dfCharacter);
            updateOrSaveDFCharacter(dfCharacter);
        }
        Page<DFCharacter.DFCharacterDTO> page = DFCharacterRepository.findByCharacterNameContainingIgnoreCase(characterName,pageable).map(DFCharacter.DFCharacterDTO::from);
        log.info("page : {}", page);
        return page;
    }

    public DFCharacter updateOrSaveDFCharacter(DFCharacter dfCharacter){
        DFCharacter character = DFCharacterRepository.findById(dfCharacter.getCharacterId()).orElse(DFCharacterRepository.save(dfCharacter));
        log.info("dfCharacter : {}", character);
        if(!Objects.equals(dfCharacter.getCharacterLevel(), character.getCharacterLevel())){
            character.setCharacterLevel(dfCharacter.getCharacterLevel());
        }
        if(!Objects.equals(dfCharacter.getCharacterName(), character.getCharacterName())){
            character.setCharacterName(dfCharacter.getCharacterName());
        }
        if(!Objects.equals(dfCharacter.getDfJob(), character.getDfJob())){
            character.setDfJob(dfCharacter.getDfJob());
        }
        if(!Objects.equals(dfCharacter.getDfJobGrow(), character.getDfJobGrow())){
            character.setDfJobGrow(dfCharacter.getDfJobGrow());
        }
        if(!Objects.equals(dfCharacter.getDfServer(), character.getDfServer())){
            character.setDfServer(dfCharacter.getDfServer());
        }
        return character;
    }


}
