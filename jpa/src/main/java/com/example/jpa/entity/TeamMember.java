package com.example.jpa.entity;

import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TeamMember {

    @Id
    @Column(name = "member_id")
    private String id;

    @Column(name = "user_name")
    private String userName;

    // sql 코드 외래키 제약조건
    // Many(팀멤버)
    @ManyToOne
    private Team team;
}
