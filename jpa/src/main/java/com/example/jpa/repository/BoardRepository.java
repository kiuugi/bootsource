package com.example.jpa.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // title 로 찾기
    List<Board> findByTitle(String title);

    List<Board> findByWriter(String writer);

    List<Board> findByTitleLike(String title);

    List<Board> findByTitleStartingWith(String title);

    List<Board> findByTitleEndingWith(String title);

    List<Board> findByTitleContaining(String title);

    // writer 가 user 로 시작하는 작성자 찾기
    List<Board> findByWriterStartingWith(String writer);

    // title 이 Title 문자열이 포함되어 있거나
    // content 가 Content 문자열이 포함되어 있는 데이터 조회
    List<Board> findByTitleOrContent(String title, String content);

    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // title 이 Title 문자열이 포함되어 있고, id 가 50보다 큰 게시문 조회

    List<Board> findByTitleContainingAndIdGreaterThan(String title, Long id);

    List<Board> findByIdGreaterThanOrderByIdDesc(Long id);

    List<Board> findByIdGreaterThanOrderByIdDesc(Long id, Pageable pageable);

}
