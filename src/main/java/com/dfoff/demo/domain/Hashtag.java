package com.dfoff.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Hashtag extends AuditingFields {

    @Id
    @NotNull
    private String name;

    @OneToMany(mappedBy = "hashtag")
    @ToString.Exclude
    @Builder.Default
    Set<BoardHashtagMapper> boards = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hashtag hashtag)) return false;
        return name.equals(hashtag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Builder
    @Getter
    @NoArgsConstructor
    public static class HashtagDto {
        private String name;


        public HashtagDto(String name) {
            this.name = name;
        }

        public static HashtagDto of(String name) {
            return new HashtagDto(name);
        }


        public String getName() {
            return name;
        }

        public Hashtag toEntity() {
            return Hashtag.builder()
                    .name(name)
                    .build();
        }

        public static HashtagDto from(Hashtag hashtag) {
            if(hashtag!=null) {
                return HashtagDto.builder()
                        .name(hashtag.getName())
                        .build();
            }
            return null;
        }
    }
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HashtagResponse{
        private String name;



        public String getName() {
            return name;
        }


        public static HashtagResponse from(HashtagDto hashtag) {
            return HashtagResponse.builder()
                    .name(hashtag.getName())
                    .build();
        }



    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HashtagRequest{
        private String value;
        public String getValue() {
            return value;
        }




    }


}



