package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.movie.constant.MemberRole;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import oracle.net.aso.m;

@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void movieInsertTest() {
        // 영화 / 영화이미지 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie" + i)
                    .build();
            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1; // 1~5까지 랜덤숫자

            for (int j = 0; j < count; j++) {
                MovieImage mImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgname("img" + j + ".jpg")
                        .build();
                movieImageRepository.save(mImage);
            }
        });
    }

    @Test
    public void memberInsertTest() {
        // 맴버 샘플 데이터 추가
        IntStream.rangeClosed(1, 2).forEach(i -> {
            Member member = Member.builder()
                    .email("admin" + i + "@email.com")
                    .password(passwordEncoder.encode("1111"))
                    .role(MemberRole.ADMIN)
                    .nickname("admin" + i)
                    .build();
            memberRepository.save(member);
        });
        // IntStream.rangeClosed(1, 100).forEach(i -> {
        // Member member = Member.builder()
        // .email("mem" + i + "@email.com")
        // .password(passwordEncoder.encode("1111"))
        // .role(MemberRole.MEMBER)
        // .nickname("reviewer" + i)
        // .build();
        // memberRepository.save(member);
        // });
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 샘플 데이터 추가
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long) (Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = (long) (Math.random() * 100) + 1;
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한 리뷰글.." + i)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    public void movieListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")); // movie_mno

        Page<Object[]> list = movieRepository.getListPage(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void moviImageListTest() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .type("t")
                .keyword("Movie")
                .page(7)
                .size(10)
                .build();

        Page<Object[]> list = movieImageRepository.getTotalList(pageRequestDto.getType(), pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("mno").descending()));

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieGetTest() {
        List<Object[]> result = movieImageRepository.getRead(1L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void movieRemoveTest() {
        Movie movie = Movie.builder().mno(2L).build();
        // 이미지 삭제
        movieImageRepository.deleteByMovie(movie);
        // 리뷰삭제
        reviewRepository.deleteByMovie(movie);
        // 영화 삭제
        movieRepository.delete(movie);
    }

    // @Transactional
    @Test
    public void testReviews() {
        Movie movie = Movie.builder().mno(99L).build();

        List<Review> reviews = reviewRepository.findByMovieOrderByReviewNoDesc(movie);

        // fetch = FetchType.LAZY : select review table 만 실행

        reviews.forEach(review -> {
            System.out.println(review);
            System.out.println(review.getMember().getEmail());
            System.out.println(review.getMember().getNickname());
        });

    }

    @Commit
    @Transactional
    @Test
    public void deleteByMemberTest() {
        // 리뷰삭제
        Member member = Member.builder()
                .mid(74L)
                .build();
        reviewRepository.deleteByMember(member);
        // 회원삭제
        memberRepository.delete(member);
    }

}
