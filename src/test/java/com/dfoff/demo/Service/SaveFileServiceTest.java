package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Repository.SaveFileRepository;
import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@ExtendWith(MockitoExtension.class)
public class SaveFileServiceTest {

    @InjectMocks
    SaveFileService sut;

    @Mock
    SaveFileRepository saveFileRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("파일 아이디로 가져오기 테스트 성공")
    void getFileTest() {
        given(saveFileRepository.findById(anyLong())).willReturn(Optional.of(createSaveFile()));
        sut.getFile(1L);
        then(saveFileRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("파일 가져오기 테스트 예외 - 파일이 없습니다")
    void getFileExceptionTest() {
        given(saveFileRepository.findById(anyLong())).willThrow(new EntityNotFoundException("파일이 없습니다 - fileId: " + 1L));
        Throwable throwable = AssertionsForClassTypes.catchThrowable(() -> sut.getFile(1L));
        then(saveFileRepository).should().findById(anyLong());
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("파일명으로 파일 가져오기 성공")
    void getFileByFileNameTest() {
        given(saveFileRepository.findByFileName(any())).willReturn(Optional.of(createSaveFile()));
        sut.getFileByFileName("test");
        then(saveFileRepository).should().findByFileName(any());
    }

    @Test
    @DisplayName("파일명으로 파일 가져오기 실패 - 파일이 없습니다")
    void getFileByFileNameExceptionTest() {
        given(saveFileRepository.findByFileName(any())).willThrow(new EntityNotFoundException("파일이 없습니다 - fileName: " + "test"));
        Throwable throwable = AssertionsForClassTypes.catchThrowable(() -> sut.getFileByFileName("test"));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
        then(saveFileRepository).should().findByFileName(any());
    }

    @Test
    @DisplayName("파일 삭제하기 성공")
    void deleteFileTest() {
        given(saveFileRepository.existsById(anyLong())).willReturn(true);
        given(saveFileRepository.getReferenceById(anyLong())).willReturn(createSaveFile());
        Throwable throwable = AssertionsForClassTypes.catchThrowable(() -> sut.deleteFile(1L));
        assertThat(throwable).isNull();
        then(saveFileRepository).should().existsById(anyLong());
        then(saveFileRepository).should().deleteById(anyLong());
    }

    @Test
    @DisplayName("파일 삭제하기 실패 - 파일이 없습니다")
    void deleteFileExceptionTest() {
        given(saveFileRepository.existsById(anyLong())).willReturn(false);
        Throwable throwable = AssertionsForClassTypes.catchThrowable(() -> sut.deleteFile(1L));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
        then(saveFileRepository).should().existsById(anyLong());
    }

    @Test
    @DisplayName("파일 저장하기 성공")
    void saveFileTest() {
        given(saveFileRepository.save(any())).willReturn(createSaveFile());
        sut.saveFile(SaveFile.SaveFileDto.from(createSaveFile()));
        then(saveFileRepository).should().save(any());
    }

    @Test
    @DisplayName("파일 저장하기 실패")
    void saveFileExceptionTest() {
        Throwable throwable = AssertionsForClassTypes.catchThrowable(() -> sut.saveFile(null));
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("게시글에 업로드 되지 않은 잔여 파일 삭제하기 성공")
    void deleteUnuploadedFilesFromBoardContentTest() {
        given(saveFileRepository.getReferenceById(anyLong())).willReturn(createSaveFile());
       sut.deleteNotUploadedFilesFromBoardContent("test","1");
        then(saveFileRepository).should().getReferenceById(anyLong());
    }




    private SaveFile createSaveFile(){
        return SaveFile.builder()
                .id(1L)
                .fileName("test")
                .filePath("test")
                .fileSize(100L)
                .fileType("jpg")
                .build();
    }
}
