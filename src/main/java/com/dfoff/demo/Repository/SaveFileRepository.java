package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.SaveFile;
import org.springframework.data.repository.query.Param;

public interface SaveFileRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.Domain.SaveFile, Long> {
    SaveFile findByFileName(@Param("file_name")String fileName);
}
