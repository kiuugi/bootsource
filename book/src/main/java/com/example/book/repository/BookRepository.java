package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByWriter(String writer);

    List<Book> findByTitle(String title);

    List<Book> findByCategory(Category category);

    List<Book> findByPublisher(Publisher publisher);

}
