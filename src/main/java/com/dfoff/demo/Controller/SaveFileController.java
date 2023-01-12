package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Util.FileUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SaveFileController {
    private final SaveFileService saveFileService;

    @PostMapping("/api/upload.df")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        try{
            SaveFile.SaveFileDTO fileDto = saveFileService.saveFile(FileUtil.getFileDtoFromMultiPartFile(file));
            return new ResponseEntity<>(fileDto, HttpStatus.OK);
        }catch (Exception e){
            log.error("uploadFile() error: {}", e.getMessage());
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/getFile.df")
    public ResponseEntity<?> getFileImg(@RequestParam String name) throws IOException {
        try{
            File file = FileUtil.getFileFromSaveFile(saveFileService.getFileByFileName(name));
            byte[] imgArray = IOUtils.toByteArray(new FileInputStream(file));
            return new ResponseEntity<>(imgArray,HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>("error",HttpStatus.NOT_FOUND);
        }
    }

}
