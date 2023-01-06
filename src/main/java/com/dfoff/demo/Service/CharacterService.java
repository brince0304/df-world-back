package com.dfoff.demo.Service;


import com.dfoff.demo.Domain.CharacterDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterAbilityDTO;
import com.dfoff.demo.Domain.ForCharacter.CharacterDetailsDTO;
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
    public Map<Integer,List<CharacterDTO>> getCharacterList(String serverId, String characterName,int page) {
        List<CharacterDTO> list = parseUtil(OpenAPIUtil.getCharacterSearchUrl(serverId,characterName), CharacterDTO.CharacterJSONDTO.class).toDTO();
        for(CharacterDTO characterDTO : list){
            CharacterDetailsDTO characterDetailsDTO = parseUtil(OpenAPIUtil.getCharacterDetailsUrl(characterDTO.getServerId(),characterDTO.getCharacterId()), CharacterDetailsDTO.CharacterDetailsJSONDTO.class).toDTO();
            characterDTO.setCharacterDetailsDTO(characterDetailsDTO);
            CharacterAbilityDTO characterAbilityDTO = parseUtil(OpenAPIUtil.getCharacterAbilityUrl(characterDTO.getServerId(),characterDTO.getCharacterId()), CharacterAbilityDTO.CharacterAbilityJSONDTO.class).toDTO();
            characterDTO.setCharacterAbilityDTO(characterAbilityDTO);
        }
        Map<Integer,List<CharacterDTO>> map = new HashMap<>();
        map.put(list.size()<10?1:list.size()/10+1,getPage(list,page,10));
        return map;
    }

    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if(pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() <= fromIndex){
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }




}
