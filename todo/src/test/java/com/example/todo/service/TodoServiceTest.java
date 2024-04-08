package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoServiceTest {

    // Service <==> Repository 동작 확인
    // Service 가 잘 동작하는지 확인

    @Autowired // final 코드를 못써서 @Autowired 사용
    private TodoServiceImpl service;

    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }
}
