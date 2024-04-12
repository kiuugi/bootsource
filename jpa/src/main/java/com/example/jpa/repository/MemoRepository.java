package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // interface MemoRepository extends JpaRepository<여기서 쓸 Entity 이름, Entity 에서
    // 기본키(PK) 타입>
    // DAO 역할

    // mno 가 5보다 작은 메모 조회
    List<Memo> findByMnoLessThan(Long mno);

    // mno 가 10보다 작은 메모 조회 (mno 내림차순)
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // mno >=50 and mno <= 70 메모조회
    List<Memo> findByMnoBetween(Long start, Long end);
}
