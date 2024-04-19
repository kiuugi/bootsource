package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Publisher;
import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);
}
