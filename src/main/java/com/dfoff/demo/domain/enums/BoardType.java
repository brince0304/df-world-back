package com.dfoff.demo.domain.enums;

public enum BoardType {
    NOTICE ("공지")
    ,FREE ("자유")
    ,MARKET ("거래")
    ,QUESTION ("질답")
    ,RECRUITMENT ("모집")
    ,REPORT("사건/사고"),
    ALL("전체");


    BoardType(String value) {
    }

}
