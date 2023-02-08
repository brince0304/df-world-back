package com.dfoff.demo.repository;

import com.dfoff.demo.domain.SaveFile;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SaveFileRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.domain.SaveFile, Long> {
    Optional<SaveFile> findByFileName(@Param("file_name")String fileName);

}
