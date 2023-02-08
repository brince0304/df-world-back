package com.dfoff.demo.repository;

import com.dfoff.demo.domain.Hashtag;

import java.util.List;

public interface HashtagRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.domain.Hashtag, String> {

    List<Hashtag> findAllByNameContainingIgnoreCase(String name);



}


