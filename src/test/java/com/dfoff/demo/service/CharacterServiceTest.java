package com.dfoff.demo.service;

import com.dfoff.demo.domain.CharacterEntity;
import com.dfoff.demo.repository.CharacterEntityRepository;
import com.dfoff.demo.utils.RestTemplateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)

class CharacterServiceTest {
    @InjectMocks
    CharacterService sut;

    @Mock
    CharacterEntityRepository characterEntityRepository;


    @Mock
    RestTemplateUtil restTemplateUtil;

    @BeforeEach
    void setUp() {

    }






    @Test
    void getCharacterDTOs() throws InterruptedException {
        //given
        //when
        sut.getCharacterDtos("all","test");
        //then
        characterEntityRepository.saveAll(List.of());
    }

    @Test
    void getCharacter() throws InterruptedException {
        //given
        given(characterEntityRepository.findById(any())).willReturn(Optional.ofNullable(CharacterEntity.builder().characterId("test").build()));
        //when
        sut.getCharacter("cain","77dae44a87261743386852bb3979c03a");
        //then
        characterEntityRepository.findById("77dae44a87261743386852bb3979c03a");
    }
    @Test
    void getCharacterByAdventureName(){
        //given
        given(characterEntityRepository.findAllByAdventureNameContaining("test",Pageable.ofSize(10))).willReturn(Page.empty());

        //when
        sut.getCharacterByAdventureName("test",Pageable.ofSize(10));

        //then
        then(characterEntityRepository).should().findAllByAdventureNameContaining("test",Pageable.ofSize(10));
    }
    @Test
    void getCharacterAbilityThenSaveAsync() throws InterruptedException {
        //given
        given(characterEntityRepository.save(any())).willReturn(CharacterEntity.builder().characterId("3bf7c8c99a0389acc0e66f4ff230d0acs").serverId("casillas").build());
        //when
        sut.getCharacterAbility(CharacterEntity.CharacterEntityDto.builder().characterId("3bf7c8c99a0389acc0e66f4ff230d0ac").serverId("casillas").build());
        //then
        then(characterEntityRepository).should().save(any());
    }

}
