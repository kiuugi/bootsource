package com.example.movie.service;

import java.util.List;
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

    public default Movie dtoToEntity(MovieDto dto) {
        // Member member = Member.builder().email(dto.getWriterEmail()).build();
        // return Board.builder()
        // .bno(dto.getBno())
        // .title(dto.getTitle())
        // .writer(member)
        // .content(dto.getContent())
        // .build();
        return null;
    }

}
