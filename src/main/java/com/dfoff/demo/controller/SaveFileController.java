package com.dfoff.demo.controller;

import com.dfoff.demo.annotation.Auth;
import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.service.SaveFileService;
import com.dfoff.demo.utils.FileUtil;
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


    @Auth
    @PostMapping("/files")
    public ResponseEntity<?> uploadFile(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestBody MultipartFile file) throws IOException {
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
