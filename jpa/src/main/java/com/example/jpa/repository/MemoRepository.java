package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // interface MemoRepository extends JpaRepository<여기서 쓸 Entity 이름, Entity 에서
    // 기본키(PK) 타입>
    // DAO 역할

}
