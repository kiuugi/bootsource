package com.example.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.guestbook.entity.GuestBook;
import java.util.List;

// QuerydslPredicateExecutor<GuestBook> - 동적 쿼리 사용을 위함

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
    List<GuestBook> findByTitle(String title);

    List<GuestBook> findByWriter(String writer);
}
