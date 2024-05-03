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
import com.example.movie.entity.Member;

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

    // @Query("delete from review r where r.member = :member", nativeQery = true)
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member);
    // review_no 를 기준으로 동작함(리뷰 작성이 많은 사람일 수록 delete 구문이 여러번 실행됨)
    // 기본적으로 id 값으로 작동하기 때문에 중복값을 지울때 비효율이 나올 수 있음
    // => 따로 @Query문을 작성해줌
}
