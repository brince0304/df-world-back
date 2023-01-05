package com.dfoff.demo.Repository.Character;


import com.dfoff.demo.Domain.ForDFCharacter.DFServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DFServerRepository extends JpaRepository<DFServer, String> {
}

