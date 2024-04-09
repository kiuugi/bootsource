package com.example.book.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void insert() {

        categoryRepository.save(Category.builder().name("컴퓨터").build());
        categoryRepository.save(Category.builder().name("경제/경영").build());
        categoryRepository.save(Category.builder().name("인문").build());
        categoryRepository.save(Category.builder().name("소설").build());
        categoryRepository.save(Category.builder().name("자기계발").build());

        publisherRepository.save(Publisher.builder().name("로드북").build());
        publisherRepository.save(Publisher.builder().name("다산").build());
        publisherRepository.save(Publisher.builder().name("웅진지식하우스").build());
        publisherRepository.save(Publisher.builder().name("비룡소").build());
        publisherRepository.save(Publisher.builder().name("올유문화사").build());
    }

    @Test
    public void bookInsert() {
        Category category = Category.builder().id(1L).build(); // (id(i % 5) + 1) 를 하고 forEach를 20번을 돌리는방법도 있음
        Publisher publisher = Publisher.builder().id(1L).build();
        LongStream.range(1, 6).forEach(i -> {
            category.setId(i);
            publisher.setId(i);
            bookRepository.save(Book.builder()
                    .title("미술관의 경비원" + i)
                    .writer("이춘향이오")
                    .price(5000)
                    .salePrice(3500)
                    .category(category)
                    .publisher(publisher)
                    .build());
        });
        LongStream.range(1, 6).forEach(i -> {
            category.setId(i);
            publisher.setId(i);
            bookRepository.save(Book.builder()
                    .title("자바를 배우는데요" + i)
                    .writer("김진우")
                    .price(9000)
                    .salePrice(6500)
                    .category(category)
                    .publisher(publisher)
                    .build());
        });
        LongStream.range(1, 6).forEach(i -> {
            category.setId(i);
            publisher.setId(i);
            bookRepository.save(Book.builder()
                    .title("슈퍼지구의 이념")
                    .writer("이상현")
                    .price(15000)
                    .salePrice(9000)
                    .category(category)
                    .publisher(publisher)
                    .build());
        });
    }
}
