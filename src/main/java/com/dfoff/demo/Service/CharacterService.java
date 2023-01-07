package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.AccountCharacterConnector;
import com.dfoff.demo.Domain.CharacterDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.JobDTO;
import com.dfoff.demo.Domain.ForCharacter.ServerDTO;
import com.dfoff.demo.Util.OpenAPIUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.dfoff.demo.Util.OpenAPIUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharacterService {


    public List<ServerDTO> getServerStatus() {
        ServerDTO.ServerJSONDTO dfServerJSONDTO = parseUtil(OpenAPIUtil.SERVER_LIST_URL, ServerDTO.ServerJSONDTO.class);
        return dfServerJSONDTO.toDTO();
    }

    public List<JobDTO> getJobList() {
        JobDTO.JobJSONDTO jobJSONDTO = parseUtil(OpenAPIUtil.JOB_LIST_URL, JobDTO.JobJSONDTO.class);
        return jobJSONDTO.toDTO();
    }

    public List<CharacterAbilityDTO> getCharacterAbilityList(List<AccountCharacterConnector.AccountCharacterConnectorDTO> list) {
        List<CharacterAbilityDTO> characterAbilityDTOList = new ArrayList<>();
        if(list.size()>0) {
            for (AccountCharacterConnector.AccountCharacterConnectorDTO dto : list) {
                CharacterAbilityDTO characterAbilityDTO = parseUtil(getCharacterAbilityUrl(dto.getServerId(),dto.getCharacterId()), CharacterAbilityDTO.CharacterAbilityJSONDTO.class).toDTO();
                characterAbilityDTOList.add(characterAbilityDTO);
            }
            return characterAbilityDTOList;
        }
        return characterAbilityDTOList;
    }

    public Map<Integer, List<CharacterDTO>> getCharacterAbilityList(String serverId, String characterName, String page) {
        int idx = Integer.parseInt(page)==1?0:Integer.parseInt(page)*10-10;
        List<CharacterDTO> list = parseUtil(OpenAPIUtil.getCharacterSearchUrl(serverId, characterName), CharacterDTO.CharacterJSONDTO.class).toDTO();
        for (int i=idx ; i<idx+10;i++) {
            if(i>=list.size() || i<0){break;}
                CharacterDTO characterDTO = list.get(i);
                CharacterAbilityDTO characterAbilityDTO = parseUtil(OpenAPIUtil.getCharacterAbilityUrl(characterDTO.getServerId(), characterDTO.getCharacterId()), CharacterAbilityDTO.CharacterAbilityJSONDTO.class).toDTO();
                characterDTO.setCharacterAbilityDTO(characterAbilityDTO);
                if(characterAbilityDTO.getStatus().size()!=0){
                    String adventureFame=characterAbilityDTO.getStatus()
                            .stream().filter(s->s.getName().equals("모험가 명성"))
                            .findFirst().isEmpty()?"0":characterAbilityDTO.getStatus()
                            .stream().filter(s->s.getName().equals("모험가 명성")).findFirst().get().getValue();
                    characterAbilityDTO.setAdventureFame(adventureFame);
                }
                log.info(String.valueOf(characterDTO));
        }
        Map<Integer, List<CharacterDTO>> map = new HashMap<>();
        map.put(list.size() < 10 ? 1 : list.size() / 10 + 1, getPage(list, page, 10));
        return map;
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
