package com.dfoff.demo.Service;

import com.dfoff.demo.Repository.Character.DFCharacterRepository;
import com.dfoff.demo.Repository.Character.DFJobGrowRepository;
import com.dfoff.demo.Repository.Character.DFJobRepository;
import com.dfoff.demo.Repository.Character.DFServerRepository;
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

class DFCharacterServiceTest {
    @InjectMocks
    DFCharacterService sut;

    @Mock
    DFCharacterRepository DFCharacterRepository;

    @Mock
    DFServerRepository dfServerRepository;

    @Mock
    DFJobRepository dfJobRepository;

    @Mock
    DFJobGrowRepository dfJobGrowRepository;

    @BeforeEach
    void setUp() {
        sut.getJobListAndSave();
        sut.getServerStatusAndSave();
    }


    @Test
    void getServerStatusAndSaveTest() throws JsonProcessingException {
        //given
        long count = dfServerRepository.count();
        //when
        sut.getServerStatusAndSave();
        //then
        then(dfServerRepository).should().saveAll(any());
    }

    @Test
    void getJobListAndSaveTest() throws JsonProcessingException {
        //given
        //when
        sut.getJobListAndSave();
        //then
        then(dfJobRepository).should().saveAll(any());
    }

    @Test
    void getCharacterListTest() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        given(dfJobRepository.findById(any())).willReturn(java.util.Optional.ofNullable(null));
        given(dfJobGrowRepository.findById(any())).willReturn(java.util.Optional.ofNullable(null));
        given(dfServerRepository.getReferenceById(any())).willReturn(null);

        //when
        sut.getCharacterList("bakal", "소라_",pageable);
        //then
        then(sut).should().getCharacterList(any(), any(), any());
    }
}