package com.dfoff.demo.utils;

import com.dfoff.demo.domain.SaveFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtil {
    private static String uploadPath;
    public static String getFilePath = "https://api.df-world.kr:8080/files/?name=";
    @Value("${spring.servlet.multipart.location}")
    public void setUploadPath(String value) {
        FileUtil.uploadPath = value;
    }

    public  static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public static String getFileNameWithUUID(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public static File getMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(uploadPath,getFileNameWithUUID(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        multipartFile.transferTo(file);
        return file;
    }

    public static File getFileFromSaveFile(SaveFile.SaveFileDto saveFileDto) {
        return new File(saveFileDto.filePath());
    }

    public static String getProfileIconPath(String profileIcon) {
        return getFilePath + profileIcon;
    }


    public static void deleteFile(SaveFile.SaveFileDto profileImg) {
        File file = getFileFromSaveFile(profileImg);
        if (file.exists()) {
            file.delete();
        }
    }
    public static SaveFile.SaveFileDto getFileDtoFromMultiPartFile(MultipartFile multipartFile) throws IOException {
        String fileName = getMultipartFileToFile(multipartFile).getName();
        String fileType = getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Long fileSize = multipartFile.getSize();
        return SaveFile.SaveFileDto.builder()
                .fileName(fileName)
                .filePath(uploadPath+fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .build();
    }
}
