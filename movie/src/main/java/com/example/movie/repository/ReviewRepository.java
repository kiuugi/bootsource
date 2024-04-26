package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Review;

import jakarta.transaction.Transactional;

import com.example.movie.entity.Movie;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // movie의 id값으로 review 지우기

    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);
}
