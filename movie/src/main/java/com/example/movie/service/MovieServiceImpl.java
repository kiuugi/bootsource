package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable((Sort.by("mno").descending()));

        Page<Object[]> result = movieImageRepository.getTotalList(pageable);
        // [Movie(mno=91, title=Movie91), MovieImage(inum=290,
        // uuid=281c993e-6c60-42fb-91ca-30631f881475, imgname=img0.jpg, path=null), 5,
        // 2.0]
        Function<Object[], MovieDto> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Double) en[3], (Long) en[2]));
        return new PageResultDto<>(result, function);
    }

    @Override
    public MovieDto getRow(Long mno) {

        List<Object[]> result = movieImageRepository.getRead(mno);

        Movie movie = (Movie) result.get(0)[0];
        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        // result 길이만큼 반복해서 MovieImage 가져오기
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });

        // List<MovieImage> movieImages = result.stream().map(en ->
        // (MovieImage)en[1]).collect(Collectors.toList());

        return entityToDto(movie, movieImages, avg, reviewCnt);
    }

    @Transactional
    @Override
    public void movieRemove(Long mno) {

        Movie movie = movieRepository.findById(mno).get();

        movieImageRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        movieRepository.deleteById(mno);
    }

    @Transactional
    @Override
    public Long movieInsert(MovieDto movieDto) {
        // 영화정보 : title => Movie Entity
        // 이미지 : MovieImage Entity

        // dto => entity
        Map<String, Object> entityMap = dtoToEntity(movieDto);
        // Movie 삽입
        Movie movie = movieRepository.save((Movie) entityMap.get("movie")); // object로 담겼으니 Movie로 형변환
        // MovieImage 삽입
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("imgList");
        movieImages.forEach(img -> movieImageRepository.save(img));

        return movie.getMno();
    }

}
