package com.example.guestbook.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestbookRepositoryTest {
    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void testInsert() {
        // 300개 테스트 데이터 삽입
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("GuestTitle" + i)
                    .writer("감블러" + (i % 10))
                    .content("Guest Content ... " + i)
                    .build();
            guestBookRepository.save(guestBook);
        });
    }

    @Test
    public void testList() {
        // 전체 리스트
        List<GuestBook> list = guestBookRepository.findAll();
        for (GuestBook guestBook : list) {
            System.out.println(guestBook);
        }
    }

    @Test
    public void testRow() {
        // 특정 Row 조회
        GuestBook guestBook = guestBookRepository.findById(1L).get();
        System.out.println(guestBook);
    }

    @Test
    public void testUpdate() {
        // 특정 Row 수정 (title, content)
        GuestBook guestBook = guestBookRepository.findById(2L).get();
        guestBook.setTitle("수정");
        guestBook.setContent("수정 Content...");
        System.out.println(guestBookRepository.save(guestBook));
    }

    @Test
    public void testDelet() {
        // 특정 Row 삭제
        guestBookRepository.deleteById(3L);
    }
}
