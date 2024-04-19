package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno 이용해서 reply 삭제
    @Modifying // delete, update 구문은 @Modifying 이 필요하다./ 기본적으로 @Query는 select가 기본
    @Query("delete from Reply r where r.board.bno = ?1")
    void deleteByBno(Long bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
