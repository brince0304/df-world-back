package com.dfoff.demo.Domain;

import com.dfoff.demo.JpaAuditing.AuditingFields;
import io.micrometer.core.lang.Nullable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
public class SaveFile extends AuditingFields {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String fileName;
    @Setter
    private String filePath;


    @Setter
    private String fileType;
    @Setter
    private Long fileSize;
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Nullable
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }


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

        public SaveFile toEntity(){
            return SaveFile.builder()
                    .id(this.getId())
                    .fileName(this.getFileName())
                    .filePath(this.getFilePath())
                    .fileType(this.getFileType())
                    .fileSize(this.getFileSize())
                    .build();
        }



    }

    /**
     * A DTO for the {@link SaveFile} entity
     */
    @Data
    @Builder
    public static class SaveFileResponse implements Serializable {
        private final LocalDateTime createdAt;
        private final String createdBy;
        private final LocalDateTime modifiedAt;
        private final String modifiedBy;
        private final Long id;
        private final String fileName;
        private final String filePath;
        private final String fileType;
        private final Long fileSize;

        public static SaveFileResponse from(SaveFileDTO dto ){
            return SaveFileResponse.builder()
                    .id(dto.getId())
                    .fileName(dto.getFileName())
                    .filePath(dto.getFilePath())
                    .fileType(dto.getFileType())
                    .fileSize(dto.getFileSize())
                    .createdAt(dto.getCreatedAt())
                    .modifiedAt(dto.getModifiedAt())
                    .createdBy(dto.getCreatedBy())
                    .modifiedBy(dto.getModifiedBy())
                    .build();
        }
    }
}
