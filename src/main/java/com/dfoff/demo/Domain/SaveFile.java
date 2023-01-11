package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SaveFile extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String fileName;
    @Setter
    private String filePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaveFile)) return false;
        SaveFile saveFile = (SaveFile) o;
        return id.equals(saveFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Setter
    private String fileType;
    @Setter
    private Long fileSize;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SaveFileDTO {
        private final Long id;
        private final String fileName;
        private final String filePath;
        private final String fileType;
        private final Long fileSize;

        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final String createdBy;
        private final String modifiedBy;

        public static SaveFileDTO from(SaveFile saveFile){
            if(saveFile == null) return SaveFileDTO.builder().build();
            return SaveFileDTO.builder()
                    .id(saveFile.getId())
                    .fileName(saveFile.getFileName())
                    .filePath(saveFile.getFilePath())
                    .fileType(saveFile.getFileType())
                    .fileSize(saveFile.getFileSize())
                    .createdAt(saveFile.getCreatedAt())
                    .modifiedAt(saveFile.getModifiedAt())
                    .createdBy(saveFile.getCreatedBy())
                    .modifiedBy(saveFile.getModifiedBy())
                    .build();
        }

        public static SaveFile toEntity(SaveFileDTO saveFileDto){
            if(saveFileDto == null) return null;
            return SaveFile.builder()
                    .id(saveFileDto.getId())
                    .fileName(saveFileDto.getFileName())
                    .filePath(saveFileDto.getFilePath())
                    .fileType(saveFileDto.getFileType())
                    .fileSize(saveFileDto.getFileSize())
                    .build();
        }



    }

}
