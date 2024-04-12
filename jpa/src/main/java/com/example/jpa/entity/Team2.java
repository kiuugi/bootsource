package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString // ToString 생성시 클래스 내 모든 property 가 기준/ exclude 로 제외가능
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jpql_team")
public class Team2 {

    @SequenceGenerator(name = "jpql_team2_seq_gen", sequenceName = "jpql_team2_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpql_team2_seq_gen")
    @Id
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String name;

}
