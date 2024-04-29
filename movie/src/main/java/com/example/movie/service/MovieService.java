package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    MovieDto getRow(Long mno);

    void movieRemove(Long mno);

    Long movieInsert(MovieDto movieDto);

    // [Movie(mno=94, title=Movie94), MovieImage(inum=299,
    // uuid=215f145d-466b-45f3-a589-729e9881b882, imgname=img0.jpg, path=null), 1,
    // 5.0]
    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {

        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .avg(avg != null ? avg : 0.0d)
                .reviewCnt(reviewCnt)
                .build();

        // 영화 하나 상세조회 => 이미지를 모두 보여주기
        List<MovieImageDto> movieImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgname(movieImage.getImgname())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(movieImageDtos);

        return movieDto;
    }

    public default Map<String, Object> dtoToEntity(MovieDto dto) {

        Map<String, Object> entityMap = new HashMap<>();

        // Movie Entity 생성
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();
        // 생성된 movie entity 를 map 에 담기
        entityMap.put("movie", movie);

        // List<MovieImageDto> movieImageDtos 를 List<Movie> 변환
        List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();
        List<MovieImage> movieImages = new ArrayList<>();
        if (movieImageDtos != null && movieImageDtos.size() > 0) {
            for (MovieImageDto mDto : movieImageDtos) {
                MovieImage movieImage = MovieImage.builder()
                        .movie(movie)
                        .imgname(mDto.getImgname())
                        .uuid(mDto.getUuid())
                        .path(mDto.getPath())
                        .build();
                movieImages.add(movieImage);
            }
        }
        // 변환이 끝난 entity list 를 map 담기 : put
        entityMap.put("imgList", movieImages);

        // if (movieImageDtos != null && movieImageDtos.size() > 0) {
        // List<MovieImage> movieImages = movieImageDtos.stream().map(mDto ->{
        // MovieImage movieImage = MovieImage.builder()
        // .imgname(mDto.getImgname())
        // .uuid(mDto.getUuid())
        // .path(mDto.getPath())
        // .build();
        // return movieImage;
        // }).collect(Collectors.toList());

        // }

        return entityMap;
    }

}
