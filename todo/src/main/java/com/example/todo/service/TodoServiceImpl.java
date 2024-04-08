package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor // @Autowired 와 같은 역할
@Service
@Log4j2
public class TodoServiceImpl {

    private final TodoRepository todoRepository;

    public List<TodoDto> getList() {
        log.info("service getList 요청");
        List<Todo> list = todoRepository.findAll();
        // Todo => TodoDto 변환
        List<TodoDto> todoList = new ArrayList<>();
        list.forEach(todo -> todoList.add(entityToDto(todo)));
        return todoList;
    }

    public TodoDto getRow(Long id) {
        log.info("service getRow 요청");
        Todo todo = todoRepository.findById(id).get();
        TodoDto todoDto = entityToDto(todo);
        return todoDto;
    }

    // DB에 연결된 entity로 온 애들을 DTO에 담음
    private TodoDto entityToDto(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .title(entity.getTitle())
                .createDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModidiedDate())
                .build();
    }

    private Todo dtoToEntity(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .title(dto.getTitle())
                .build();
    }
}
