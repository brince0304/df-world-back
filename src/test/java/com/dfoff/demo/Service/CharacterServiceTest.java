package com.dfoff.demo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)

class CharacterServiceTest {
    @InjectMocks
    CharacterService sut;


    @BeforeEach
    void setUp() {
        sut.getJobList();
        sut.getServerStatus();
    }


    @Test
    void getServerStatusAndSaveTest() throws JsonProcessingException {
        //given

        //when
        sut.getServerStatus();
        //then
    }

    @Test
    void getJobListAndSaveTest() throws JsonProcessingException {
        //given
        //when
        sut.getJobList();
        //then
    }

    @Test
    void getCharacterListTest() {
        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        //then
    }
}
