package com.example.todo.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {
    // DAO == TodoRepository
    // service 에서 호출하는 메소드 테스트
    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입
    @Test
    public void insertTodo() {
        // @다이나믹이 없을 때
        // insert into todotbl
        // (completed, created_date, important, last_modidied_date, title, todo_id)
        // values (?, ?, ?, ?, ?, ?)
        // IntStream.range(1, 10).forEach(i -> {

        // Todo todo = Todo.builder().title("오늘 할 일 Todo" +
        // i).build();
        // todoRepository.save(todo);
        // });
        IntStream.range(1, 10).forEach(i -> {
            // insert into todotbl (created_date, last_modidied_date, title, todo_id) values
            // (?, ?, ?, ?)
            Todo todo = Todo.builder().title("오늘 할 일 Todo " + i).build();
            todoRepository.save(todo);
        });

    }

    @Test
    public void getTodoList() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // todo 완료 목록 조회
    @Test
    public void getCompletedTodo() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void getImportantTodo() {
        todoRepository.findByImportant(true).forEach(todo -> System.out.println(todo));
    }

    // todo 수정
    @Test
    public void updateTodo() {
        Todo entity = todoRepository.findById(1L).get();
        entity.setCompleted(true);
        todoRepository.save(entity);

        entity = todoRepository.findById(1L).get();
        entity.setTitle("6시 기상");
        entity.setImportant(true);
        todoRepository.save(entity);

    }

    // todo 삭제
    @Test
    public void deleteTodo() {
        todoRepository.deleteById(9L);
    }

}
