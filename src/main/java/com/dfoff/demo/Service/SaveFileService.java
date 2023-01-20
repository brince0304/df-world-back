package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Repository.SaveFileRepository;
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
    public SaveFile.SaveFileDTO getFile(Long fileId) {
        log.info("getFile() fileId: {}", fileId);
        return saveFileRepository.findById(fileId).map(SaveFile.SaveFileDTO::from).orElseThrow(()-> new EntityNotFoundException("파일이 없습니다 - fileId: " + fileId));
    }

    @Transactional(readOnly = true)
    public SaveFile.SaveFileDTO getFileByFileName(String fileName) {
        log.info("getFileByFileName() fileId: {}", fileName);
        if(Objects.isNull(fileName)) {
            throw new IllegalArgumentException("파일 이름이 없습니다");
        }

        return SaveFile.SaveFileDTO.from(saveFileRepository.findByFileName(fileName).orElseThrow(()-> new IllegalArgumentException("파일이 없습니다 - fileName: " + fileName)));
    }


    public void deleteFile(Long fileId) {
        if (!saveFileRepository.existsById(fileId)) {
            throw new EntityNotFoundException("파일이 없습니다 - fileId: " + fileId);
        }
        log.info("deleteFile() fileId: {}", fileId);
        File file = new File(saveFileRepository.getReferenceById(fileId).getFilePath());
        if (file.exists()) {
            saveFileRepository.deleteById(fileId);
            if (file.delete()) {
                log.info("파일삭제 성공");
            }
        }
    }

    public SaveFile.SaveFileDTO saveFile(SaveFile.SaveFileDTO saveFile) {
        if(saveFile == null){throw new IllegalArgumentException("파일이 없습니다");}
        log.info("saveFile() saveFile: {}", saveFile);
        return SaveFile.SaveFileDTO.from(saveFileRepository.save(saveFile.toEntity()));
    }


    public void deleteUnuploadedFilesFromBoardContent(String content,String fileIds){
        if(Objects.isNull(content) || Objects.isNull(fileIds) || fileIds.equals("")){
            return;
        }
        for(String fileId : fileIds.split(",")){
            if(!content.contains(saveFileRepository.getReferenceById(Long.parseLong(fileId)).getFileName())){
                deleteFile(Long.parseLong(fileId));
            }
        }
    }

    public Set<SaveFile.SaveFileDTO> getFileDtosFromRequestsFileIds(Board.BoardRequest dto) {
        deleteUnuploadedFilesFromBoardContent(dto.getBoardContent(),dto.getBoardFiles());
        String[] fileIdArr = Objects.requireNonNull(dto.getBoardFiles()).split(",");
        Set<SaveFile.SaveFileDTO> saveFileDtos = new HashSet<>();
        for (String fileId : fileIdArr) {
            log.info("fileId: {}", fileId);
            if (fileId.equals("")) {
                break;
            }
            saveFileRepository.findById(Long.parseLong(fileId)).ifPresent(saveFile -> saveFileDtos.add(SaveFile.SaveFileDTO.from(saveFile)));
        }
        return saveFileDtos;
    }




}
