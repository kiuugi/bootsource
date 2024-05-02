package com.example.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> reviews = reviewRepository.findByMovieOrderByReviewNoDesc(movie);

        return reviews.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long addReview(ReviewDto reviewDto) {

        return reviewRepository.save(dtoToEntity(reviewDto)).getReviewNo();

    }

    @Override
    public void removeReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

    // /299/5 + get
    @Override
    public ReviewDto getReview(Long reviewNo) {
        Review review = reviewRepository.findById(reviewNo).get();
        return entityToDto(review);
    }

    @Override
    public Long updateReview(ReviewDto reviewDto) {
        // save() => 1) select, 2) 중복id 확인-> insert or update
        return reviewRepository.save(dtoToEntity(reviewDto)).getReviewNo();

        // Optional<Review> result = reviewRepository.findById(reviewDto.getReviewNo());
        // if (result.isPresent()) {
        // Review review = result.get();
        // review.setText(reviewDto.getText());
        // review.setGrade(reviewDto.getGrade());
        // reviewRepository.save(dtoToEntity(reviewDto));
        // }
        // return reviewDto.getReviewNo();
    }

}
