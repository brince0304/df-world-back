package com.dfoff.demo.Util;

import com.dfoff.demo.Domain.SaveFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {
    private FileUtil() {
    }
    public static String uploadPath ="/Users/brinc/Desktop/brincestudy/JAVA/df-toy-project/src/main/resources/static/images/imgSaveFolder/";


    public  static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public static String getFileNameWithUUID(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public static File getMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(uploadPath,getFileNameWithUUID(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }

    public static File getFileFromSaveFile(SaveFile.SaveFileDTO saveFileDto) {
        return new File(saveFileDto.filePath());
    }


    public static void deleteFile(SaveFile.SaveFileDTO profileImg) {
        File file = getFileFromSaveFile(profileImg);
        if (file.exists()) {
            file.delete();
        }
    }
    public static SaveFile.SaveFileDTO getFileDtoFromMultiPartFile(MultipartFile multipartFile) throws IOException {
        String fileName = getMultipartFileToFile(multipartFile).getName();
        String fileType = getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Long fileSize = multipartFile.getSize();
        return SaveFile.SaveFileDTO.builder()
                .fileName(fileName)
                .filePath(uploadPath+fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .build();// TODO: 경로가 자꾸 null 로 입력되기 때문에 해결 방안을 찾아야함.
    }
}
