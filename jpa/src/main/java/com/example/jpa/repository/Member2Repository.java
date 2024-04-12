package com.example.jpa.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Team2;

import java.util.List;

public interface Member2Repository extends JpaRepository<Member2, Long>, QuerydslPredicateExecutor<Member2> {

    @Query("Select m From Member2 m ")
    List<Member2> findByMembers(Sort sort);

    @Query("Select m.userName, m.age From Member2 m")
    List<Object[]> findByMembers2();

    // 특정 나이보다 많은 회원 조회
    @Query("select m from Member2 m where m.age > ?1")
    List<Member2> findByAgeList(int age);

    // 특정 팀의 회원 조회
    @Query("Select m from Member2 m where m.team2 = ?1")
    List<Member2> findByTeamEqual(Team2 team2);

    @Query("Select m from Member2 m where m.team2.id = ?1")
    List<Member2> findByTeamIdEqual(Long id);

    // 집계함수
    @Query("Select COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) From Member2 m")
    List<Object[]> aggregate();

    // join jpql_team t1_0 on t1_0.team_id=m1_0.team2_team_id
    // on을 써주지 않아도 조건에 맞춰서 on 을 넣어줌
    @Query("Select m FROM Member2 m JOIN m.team2 t WHERE t.name = :teamName")
    List<Member2> findByTeamMember(String teamName);

    @Query("Select m, t FROM Member2 m JOIN m.team2 t WHERE t.name = :teamName")
    List<Object[]> findByTeamMember2(String teamName);

    // 외부조인 / LEFT JOIN ~ ON(where과 같음) ~
    @Query("Select m FROM Member2 m LEFT JOIN m.team2 t ON t.name = :teamName")
    List<Member2> findByTeamMember3(String teamName);
}
