package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 특정 컬럼 값에 따라 조회
    List<Todo> findByCompleted(Boolean completed);

    // where completed=? order by id desc
    List<Todo> findByCompletedOrderByIdDesc(Boolean completed);

    List<Todo> findByImportant(Boolean important);

    List<Todo> findByTitle(String title);
}
