package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.dto.TodoDto;

@SpringBootTest
public class TodoServiceTest {

    // Service <==> Repository 동작 확인
    // Service 가 잘 동작하는지 확인

    @Autowired // final 코드를 못써서 @Autowired 사용
    private TodoService service;

    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

    @Test
    public void serviceCreate() {
        TodoDto dto = new TodoDto();
        dto.setTitle("testCreate");
        dto.setImportant(true);
        System.out.println(service.create(dto));
    }

    @Test
    public void serviceRead() {
        System.out.println(service.getTodo(3L));
    }
}
