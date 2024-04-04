package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Test {

    // create table test (
    // id number(19,0) not null,
    // id2 number(19,0) not null,
    // id3 number(10,0) not null,
    // id4 number(10,0),
    // primary key (id)
    // )
    // 기본타입 : 소문자 시작 / int, long, boolean, char, float, double, ... / null 대입 불가
    // 객체타입 : 대문자 시작 / Integer, Long, Boolean, ... / null 대입 가능
    // 기본타입은 no null 객체타입은 null 가능으로 설정됨
    @Id
    private Long id;

    @Column(columnDefinition = "number(8)") // 컬럼 속성 정의
    private long id2;

    private int id3;

    private Integer id4;

}
