package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.CharacterEntityRepository;
import com.dfoff.demo.Repository.UserAccountCharacterMapperRepository;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Domain.UserAccountCharacterMapper;
import com.dfoff.demo.Util.OpenAPIUtil;
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
    UserAccountRepository userAccountRepository;
    @Mock
    UserAccountCharacterMapperRepository mapperRepository;

    @Mock
    OpenAPIUtil openAPIUtil;

    @BeforeEach
    void setUp() {

    }






    @Test
    void getCharacterDTOsTest() {
        //given
        //when
        sut.getCharacterDTOs("all","test");
        //then
        characterEntityRepository.saveAll(List.of());
    }

    @Test
    void getCharacterTest(){
        //given
        given(characterEntityRepository.findById(any())).willReturn(Optional.ofNullable(CharacterEntity.builder().characterId("test").build()));
        //when
        sut.getCharacter("cain","77dae44a87261743386852bb3979c03a");
        //then
        characterEntityRepository.findById("77dae44a87261743386852bb3979c03a");
    }
    @Test
    void getCharacterByAdventureNameTest(){
        //given
        given(characterEntityRepository.findAllByAdventureNameContaining("test",Pageable.ofSize(10))).willReturn(Page.empty());

        //when
        sut.getCharacterByAdventureName("test",Pageable.ofSize(10));

        //then
        then(characterEntityRepository).should().findAllByAdventureNameContaining("test",Pageable.ofSize(10));
    }
    @Test
    void getCharacterAbilityThenSaveAsyncTest(){
        //given
        given(characterEntityRepository.save(any())).willReturn(CharacterEntity.builder().characterId("3bf7c8c99a0389acc0e66f4ff230d0acs").serverId("casillas").build());
        //when
        sut.getCharacterAbilityThenSaveAsync(CharacterEntity.CharacterEntityDto.builder().characterId("3bf7c8c99a0389acc0e66f4ff230d0ac").serverId("casillas").build());
        //then
        then(characterEntityRepository).should().save(any());
    }
    @Test
    void addCharacterTest(){
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        given(characterEntityRepository.findById(any())).willReturn(Optional.ofNullable(CharacterEntity.builder().characterId("test").build()));
        given(userAccountRepository.findById(any())).willReturn(Optional.ofNullable(UserAccount.builder().userId("test").build()));

        //when
        sut.addCharacter(UserAccount.UserAccountDto.builder().userId("test").build(),CharacterEntity.CharacterEntityDto.builder().characterId("test").build());

        //then
        then(mapperRepository).should().save(any());
    }

    @Test
    void deleteCharacterTest(){
        //given
        given(userAccountRepository.existsByUserId(any())).willReturn(true);
        given(characterEntityRepository.findById(any())).willReturn(Optional.ofNullable(CharacterEntity.builder().characterId("test").build()));
        given(userAccountRepository.findById(any())).willReturn(Optional.ofNullable(UserAccount.builder().userId("test").build()));
        given(mapperRepository.findByUserAccountAndCharacter(any(),any())).willReturn(UserAccountCharacterMapper.of(UserAccount.builder().userId("test").build(),CharacterEntity.builder().characterId("test").build()));
        //when
        sut.deleteCharacter(UserAccount.UserAccountDto.builder().userId("test").build(),CharacterEntity.CharacterEntityDto.builder().characterId("test").build());

        //then
        then(mapperRepository).should().findByUserAccountAndCharacter(any(),any());
    }

}
