package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Adventure;
import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Repository.AdventureRepository;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdventureService {

    private final AdventureRepository adventureRepository;
    private final CharacterEntityRepository characterEntityRepository;

    public void saveAdventure(CharacterEntity.CharacterEntityDto dto){
        if(dto.getAdventureName()==null){return;}
        Adventure adventure = adventureRepository.findById(dto.getAdventureName()).orElseGet(
                ()-> adventureRepository.save(Adventure.builder()
                        .adventureName(dto.getAdventureName())
                        .serverId(dto.getServerId()).build())
        );
        adventure.getCharacters().add(CharacterEntity.CharacterEntityDto.toEntity(dto));
        adventureStatusSetter(adventure);
    }

    public void saveAdventureByCharacterId(String characterId){
        CharacterEntity character = characterEntityRepository.findById(characterId).get();
        if(character.getAdventureName()==null){return;}
        Adventure adventure = adventureRepository.findById(character.getAdventureName()).orElseGet(
                ()-> adventureRepository.save(Adventure.builder()
                        .adventureName(character.getAdventureName())
                        .serverId(character.getServerId()).build())
        );
        adventure.getCharacters().add(character);
        adventureStatusSetter(adventure);
    }


    private void adventureStatusSetter(Adventure adventure){
        Integer adventureFame = adventure.getCharacters().stream().mapToInt(CharacterEntity::getAdventureFame).sum();
        Integer adventureDamageIncreaseAndBuffPower = adventure.getCharacters().stream().mapToInt(CharacterEntity::getDamageIncrease).sum()+
                adventure.getCharacters().stream().mapToInt(CharacterEntity::getBuffPower).sum();
        adventure.setAdventureFame(adventureFame);
        adventure.setAdventureDamageIncreaseAndBuffPower(adventureDamageIncreaseAndBuffPower);
    }

    public List<Adventure.UserAdventureMainPageResponse> getUserAdventureRankingBest5OrderByAdventureFame(){
        return adventureRepository.getAdventureRankingBest5OrderByAdventureFame();
    }

    public List<Adventure.UserAdventureMainPageResponse> getUserAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower(){
        return adventureRepository.getAdventureRankingBest5OrderByAdventureDamageIncreaseAndBuffPower();
    }



}
