package com.dfoff.demo.service;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.repository.SaveFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SaveFileService {
    private final SaveFileRepository saveFileRepository;

    @Transactional(readOnly = true)
    public SaveFile.SaveFileDto getFile(Long fileId) {
        return saveFileRepository.findById(fileId).map(SaveFile.SaveFileDto::from).orElseThrow(()-> new EntityNotFoundException("파일이 없습니다 - fileId: " + fileId));
    }

    @Transactional(readOnly = true)
    public SaveFile.SaveFileDto getFileByFileName(String fileName) {
        if(Objects.isNull(fileName)) {
            throw new IllegalArgumentException("파일 이름이 없습니다");
        }

        return SaveFile.SaveFileDto.from(saveFileRepository.findByFileName(fileName).orElseThrow(()-> new IllegalArgumentException("파일이 없습니다 - fileName: " + fileName)));
    }


    @Transactional
    public void deleteFile(Long fileId) {
        if (!saveFileRepository.existsById(fileId)) {
            throw new EntityNotFoundException("파일이 없습니다 - fileId: " + fileId);
        }
        File file = new File(saveFileRepository.getReferenceById(fileId).getFilePath());
        if (file.exists()) {
            if(file.delete()) {
                log.info("파일 삭제 성공");
                saveFileRepository.deleteById(fileId);
            }
        }
    }

    public boolean isExistById(Long fileId) {
        return saveFileRepository.existsById(fileId);
    }

    @Transactional
    public void deleteFileByFileName (String fileName) {
       SaveFile file = saveFileRepository.findByFileName(fileName).orElseThrow(()-> new IllegalArgumentException("파일이 없습니다 - fileName: " + fileName));
          File file1 = new File(file.getFilePath());
          if (file1.exists()) {
              if(file1.delete()){
                    log.info("파일 삭제 성공");
                  saveFileRepository.delete(file);
              }
          }
       saveFileRepository.deleteByFileName(fileName);
    }


    @Transactional
    public SaveFile.SaveFileDto saveFile(SaveFile.SaveFileDto saveFile) {
        if(saveFile == null){throw new IllegalArgumentException("파일이 없습니다");}
        return SaveFile.SaveFileDto.from(saveFileRepository.save(saveFile.toEntity()));
    }


    @Transactional
    public void deleteNotUploadedFilesFromBoardContent(String content, String fileIds){
        if(Objects.isNull(content) || Objects.isNull(fileIds) || fileIds.equals("")){
            return;
        }
        for(String fileId : fileIds.split(",")){
            log.info("fileId: {}", fileId);
            String fileName = saveFileRepository.getReferenceById(Long.parseLong(fileId)).getFileName();
            if(!content.contains(fileName)){
                log.info("파일 삭제");
                deleteFile(Long.parseLong(fileId));
            }
        }
    }

    @Transactional
    public Set<SaveFile.SaveFileDto> getFileDtosFromRequestFileIds(Board.BoardRequest dto) {
        deleteNotUploadedFilesFromBoardContent(dto.boardContent(),dto.boardFiles());
        String[] fileIdArr = Objects.requireNonNull(dto.boardFiles()).split(",");
        Set<SaveFile.SaveFileDto> saveFileDtos = new HashSet<>();
        for (String fileId : fileIdArr) {
            if (fileId.equals("")) {
                break;
            }
            saveFileRepository.findById(Long.parseLong(fileId)).ifPresent(saveFile -> saveFileDtos.add(SaveFile.SaveFileDto.from(saveFile)));
        }
        return saveFileDtos;
    }




}
