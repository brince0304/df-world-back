package com.dfoff.demo.Repository;

import com.dfoff.demo.Domain.Hashtag;

import java.util.List;

public interface HashtagRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.Domain.Hashtag, String> {

    List<Hashtag> findAllByNameContainingIgnoreCase(String name);



}


