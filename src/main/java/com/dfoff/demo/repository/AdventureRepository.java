package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Adventure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public interface AdventureRepository extends org.springframework.data.jpa.repository.JpaRepository<Adventure, String>, AdventureCustomRepository {
    boolean existsByRepresentCharacter_CharacterId(String characterId);

    boolean existsByUserAccount_UserIdAndDeletedIsFalse(String userId);



}
