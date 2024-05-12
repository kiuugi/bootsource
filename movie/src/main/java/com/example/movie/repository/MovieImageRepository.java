package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.MovieImage;
import com.example.movie.repository.total.MovieImageReviewRepository;

import com.example.movie.entity.Movie;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>, MovieImageReviewRepository {
    // movie의 id로 movieImage 지우기

    @Modifying
    @Query("delete from MovieImage mi where mi.movie = :movie")
    void deleteByMovie(Movie movie);
}
