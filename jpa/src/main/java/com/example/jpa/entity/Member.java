package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membertbl", uniqueConstraints = { // 제약조건 유니크함(name, age) 중복불가
        @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = { "name", "age" }) })
public class Member {
    // id, name, age, roleType(ADMIN, USER), created_date, last_modified_date,
    // description
    @Id
    private String id;

    @Column(name = "name")
    private String userName;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING) // check (role_type in ('ADMIN','USER')),
    private RoleType roleType;

    @CreatedDate // insert 시 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 마지막으로 변경된 시간 자동 저장
    private LocalDateTime lastModifiedDate;

    @Lob // 대용량 데이터 담기
    private String description;

}
