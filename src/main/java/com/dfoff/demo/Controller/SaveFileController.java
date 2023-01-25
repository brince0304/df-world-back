package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Service.BoardService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BoardService boardService;

    @PostMapping("/files")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
            SaveFile.SaveFileDto fileDto = saveFileService.saveFile(FileUtil.getFileDtoFromMultiPartFile(file));
            return new ResponseEntity<>(fileDto, HttpStatus.OK);
        }

    @GetMapping("/files/")
    public ResponseEntity<?> getFileImg(@RequestParam String name) throws IOException {
        File file = FileUtil.getFileFromSaveFile(saveFileService.getFileByFileName(name));
        byte[] imgArray = IOUtils.toByteArray(new FileInputStream(file));
        return new ResponseEntity<>(imgArray, HttpStatus.OK);
    }

}
