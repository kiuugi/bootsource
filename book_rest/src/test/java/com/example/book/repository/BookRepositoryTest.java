package com.example.book.repository;

import static org.mockito.ArgumentMatchers.endsWith;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.example.book.dto.PageRequestDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    private PageRequestDto pageRequestDto;

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

    @Test
    @Transactional
    public void testBookList() {
        List<Book> books = bookRepository.findAll();

        books.forEach(book -> {
            System.out.println(book);
            System.out.println("출판사 이름 : " + book.getPublisher().getName());
            System.out.println("카테고리 : " + book.getCategory().getName());
        });
    }

    @Test
    public void searchTest() {
        List<Book> books = bookRepository.findByCategory(Category.builder().id(1L).build());

        books.forEach(book -> {
            System.out.println(book);
            System.out.println("출판사 이름 : " + book.getPublisher().getName());
            System.out.println("카테고리 : " + book.getCategory().getName());
        });
    }

    @Test
    public void categoryTest() {
        List<Category> list = categoryRepository.findAll();

        list.forEach(cate -> {
            System.out.println(cate);
            System.out.println(cate.getName());
        });
        // List<String> cateList = new ArrayList<>();
        // list.forEach(cate -> cateList.add(cate.getName()));
        List<String> cateList = list.stream().map(entity -> entity.getName()).collect(Collectors.toList());

        cateList.forEach(System.out::println);
    }

    @Test
    public void testList() {
        // Spring Data JPA 페이징 처리 객체 (페이지나누기를 편하게 해줌)
        // page 번호 : 0 부터 시작
        // Pageable pageable = PageRequest.of(0, 10);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "id"); // "DESC 할
        // 컬럼 명"
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        // Page : 페이지 나누기에 필요한 메소드 제공
        // ==> PageDto와 같은 역할
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("t", "미술"), pageable);

        System.out.println("전체 행 수" + result.getTotalElements());
        System.out.println("필요한 페이지 수 " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }

}
