package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Review;

import jakarta.transaction.Transactional;

import com.example.movie.entity.Movie;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // movie의 id값으로 review 지우기

    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 번호를 이용해 리뷰 가져오기
    // 이 메소드 실행 시 join 구문으로 처리해줘
    // .getMember.get* 하고 들어갈때 select 구문이 2번 실행되는걸 방지함
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovieOrderByReviewNoDesc(Movie movie);
}
