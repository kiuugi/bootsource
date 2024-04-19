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

    public default Predicate makePredicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        QBook book = QBook.book;
        // id > 0
        builder.and(book.id.gt(0L));

        // 검색
        // 검색 타입이 없는 경우
        if (type == null)
            return builder;
        // 검색 타입이 있는 경우
        if (type.equals("c")) {
            builder.and(book.category.name.contains(keyword));
        } else if (type.equals("t")) {
            builder.and(book.title.contains(keyword));
        } else if (type.equals("w")) {
            builder.and(book.writer.contains(keyword));
        }

        return builder;
    }

}
