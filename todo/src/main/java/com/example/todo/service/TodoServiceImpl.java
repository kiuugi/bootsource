package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor // @Autowired 와 같은 역할
@Service // 프레임 워크가 관리하는 객체라고 알려줌 -> 프레임 워크가 객체를 생성하고 가지고있게됨 -> 필요한 시점에 알아서 생성한 객체를 사용해줌
@Log4j2
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoDto> getList() {
        // 미완료 목록 보여주기
        log.info("ser`vice getList 요청");
        List<Todo> list = todoRepository.findByCompletedOrderByIdDesc(false);
        // Todo => TodoDto 변환
        // List<TodoDto> todoList = new ArrayList<>();
        // list.forEach(todo -> todoList.add(entityToDto(todo)));
        List<TodoDto> compList = list.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());
        return compList;
    }

    @Override
    public List<TodoDto> getDoneList() {
        log.info("service getDoneList 요청");
        List<Todo> completedList = todoRepository.findByCompletedOrderByIdDesc(true);
        // Todo => TodoDto 변환
        List<TodoDto> todoList = new ArrayList<>();
        completedList.forEach(todo -> todoList.add(entityToDto(todo)));
        return todoList;
    }

    @Override
    public TodoDto getTodo(Long id) {
        log.info("service getTodo 요청");
        Todo todo = todoRepository.findById(id).get();
        TodoDto todoDto = entityToDto(todo);
        return todoDto;
    }

    @Override
    public TodoDto create(TodoDto dto) {
        // TodoDto => Todo 변환
        Todo entity = todoRepository.save(dtoToEntity(dto));
        return entityToDto(entity);
    }

    @Override
    public Long updateTodo(Long id) {
        // 업데이트 완료 후 id 만 리턴
        Todo entity = todoRepository.findById(id).get();
        entity.setCompleted(true);
        Todo todo = todoRepository.save(entity);

        return todo.getId();
    }

    @Override
    public void deleteTodo(@RequestParam("id") Long id) {

        todoRepository.deleteById(id);
    }

}
