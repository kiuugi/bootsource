package com.example.movie.entity;

import com.example.movie.constant.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "movie_member")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @SequenceGenerator(name = "mid_seq_gen", sequenceName = "mid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mid_seq_gen")
    private Long mid;

    private String email;

    private String password;

    private String nickname;

    private MemberRole role;

}
