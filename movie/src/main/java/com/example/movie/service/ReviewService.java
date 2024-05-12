package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewService {
    // 특정 영화의 모든 리뷰 가져오기
    List<ReviewDto> getListOfMovie(Long mno);

    // 특정 영화에 리뷰 등록
    Long addReview(ReviewDto reviewDto);

    // 특정 리뷰 삭제
    void removeReview(Long reviewNo);

    ReviewDto getReview(Long reviewNo);

    Long updateReview(ReviewDto reviewDto);

    public default ReviewDto entityToDto(Review review) {
        return ReviewDto.builder()
                .reviewNo(review.getReviewNo())
                .grade(review.getGrade())
                .text(review.getText())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .mno(review.getMovie().getMno())
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .build();

    }

    public default Review dtoToEntity(ReviewDto reviewDto) {
        // return Review.builder()
        // .reviewNo(reviewDto.getReviewNo())
        // .grade(reviewDto.getGrade())
        // .text(reviewDto.getText())
        // .member(Member.builder().mid(reviewDto.getMid()).build())
        // .movie(Movie.builder().mno(reviewDto.getMno()).build())
        // .build();
        Review review = new Review();
        review.setCreatedDate(reviewDto.getCreatedDate());
        review.setGrade(reviewDto.getGrade());
        review.setReviewNo(reviewDto.getReviewNo());
        review.setMember(Member.builder().mid(reviewDto.getMid()).build());
        review.setText(reviewDto.getText());
        review.setMovie(Movie.builder().mno(reviewDto.getMno()).build());
        return review;
    }
}
