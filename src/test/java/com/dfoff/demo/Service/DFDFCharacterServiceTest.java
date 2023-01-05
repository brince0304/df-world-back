package com.dfoff.demo.Service;

import com.dfoff.demo.Repository.Character.DFCharacterRepository;
import com.dfoff.demo.Repository.Character.DFJobRepository;
import com.dfoff.demo.Repository.Character.DFServerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)

class DFDFCharacterServiceTest {
    @InjectMocks
    DFCharacterService sut;

    @Mock
    DFCharacterRepository DFCharacterRepository;

    @Mock
    DFServerRepository dfServerRepository;

    @Mock
    DFJobRepository dfJobRepository;


    @Test
    void test() throws JsonProcessingException {
        //given
        long count = dfServerRepository.count();
        //when
        sut.getServerStatus();
        //then
        then(dfServerRepository).should().saveAll(any());
    }

    @Test
    void test1() throws JsonProcessingException {
        //given
        //when
        sut.getJobList();
        //then
        then(dfJobRepository).should().saveAll(any());

    }
}
