package com.example.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 회원찾기(email, social 회원 여부)
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email = :email and m.fromSocial= :social")
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean social);
    // left join club_member_role_set rs1_0 on cm1_0.email=rs1_0.club_member_email

}
