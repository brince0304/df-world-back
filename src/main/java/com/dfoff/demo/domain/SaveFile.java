package com.dfoff.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
@Table(indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "fileName"),
})
@SQLDelete(sql = "UPDATE save_file SET deleted = true, deleted_at = now() WHERE id = ?")
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

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;


    private LocalDateTime deletedAt;







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaveFile saveFile)) return false;
        return id.equals(saveFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

        @Builder
        public record SaveFileDto(
                Long id, String fileName, String filePath, String fileType, Long fileSize,
                                  LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy
        ) {
            public static SaveFileDto from(SaveFile saveFile) {
                if(saveFile == null) return null;
                return SaveFileDto.builder()
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

            public SaveFile toEntity() {
                return SaveFile.builder()
                        .id(id)
                        .fileName(this.fileName())
                        .filePath(this.filePath())
                        .fileType(this.fileType())
                        .fileSize(this.fileSize())
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

        public static SaveFileResponse from(SaveFileDto dto ){
            return SaveFileResponse.builder()
                    .id(dto.id())
                    .fileName(dto.fileName())
                    .filePath(dto.filePath())
                    .fileType(dto.fileType())
                    .fileSize(dto.fileSize())
                    .createdAt(dto.createdAt())
                    .modifiedAt(dto.modifiedAt())
                    .createdBy(dto.createdBy())
                    .modifiedBy(dto.modifiedBy())
                    .build();
        }
    }
}
