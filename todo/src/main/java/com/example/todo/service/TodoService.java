package com.example.todo.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;

public interface TodoService {

    List<TodoDto> getList();

    List<TodoDto> getDoneList();

    TodoDto getTodo(Long id);

    TodoDto create(TodoDto dto);

    Long updateTodo(Long id);

    void deleteTodo(Long id);

    // DB에 연결된 entity로 온 애들을 DTO에 담음
    public default TodoDto entityToDto(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .title(entity.getTitle())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    public default Todo dtoToEntity(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .title(dto.getTitle())
                // .createdDate(dto.getCreatedDate())
                // .lastModifiedDate(dto.getLastModifiedDate())
                .build();
    }

}