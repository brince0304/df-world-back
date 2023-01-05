package com.dfoff.demo.Repository.Character;

import com.dfoff.demo.Domain.ForDFCharacter.DFJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DFJobRepository extends JpaRepository<DFJob, String> {
}

