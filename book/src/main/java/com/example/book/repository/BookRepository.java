package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.entity.QBook;
import com.fasterxml.jackson.core.StreamReadConstraints.Builder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
    List<Book> findByWriter(String writer);

    List<Book> findByTitle(String title);

    List<Book> findByCategory(Category category);

    List<Book> findByPublisher(Publisher publisher);

    public default Predicate makePredicate() {
        BooleanBuilder builder = new BooleanBuilder();

        QBook book = QBook.book;
        // id > 0
        builder.and(book.id.gt(0L));

        return builder;
    }

}
