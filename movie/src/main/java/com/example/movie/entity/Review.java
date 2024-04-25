package com.example.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "member", "movie" })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseEntity {

    @Id
    @SequenceGenerator(name = "reviewNo_seq_gen", sequenceName = "reviewNo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewNo_seq_gen")
    private Long reviewNo;

    private int grade;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}
