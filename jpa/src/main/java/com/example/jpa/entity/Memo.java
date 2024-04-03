package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memotbl") // 테이블이름 설정 / 기본은 클래스 이름을 따라감
@Entity // 데이터베이스에서 데이터로 관리하는 대상
public class Memo {
    // AUTO : create sequence memotbl_seq start with 1 increment by 50
    // IDENTITY(기본키 생성은 데이터베이스에 위임) : mno number(19,0) generated as identity
    // SEQUENCE : create sequence memotbl_seq start with 1 increment by 50
    @SequenceGenerator(name = "memo_seq_gen", sequenceName = "memo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_gen")
    @Id // == primary key
    private Long mno;
    // 자바 변수명은 _ 사용 X => 대문자 / 데이터베이스 컬럼명 memo_text
    @Column(nullable = false, length = 300)
    private String memoText;
}
