package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        // LongStream.range(1, 101); 1~100
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void readTest() {
        System.out.println(boardRepository.findById(5L));
        // Optional[Board(id=5, title=Title...5, content=Content...5, writer=user5)]
    }

    @Test
    public void getListTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(26L);
        // result.get();
        result.ifPresent(board -> {
            board.setTitle("updateTitle...");
            board.setContent("updateContent...");
            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void deleteTest() {
        // entity 찾기
        Optional<Board> result = boardRepository.findById(7L);
        // 삭제
        boardRepository.delete(result.get());
        System.out.println("삭제된 번호라면 empty " + boardRepository.findById(7L));
    }

}
