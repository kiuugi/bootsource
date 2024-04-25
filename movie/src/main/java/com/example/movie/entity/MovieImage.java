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
@Builder
@ToString(exclude = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieImage {

    @Id
    @SequenceGenerator(name = "inum_seq_gen", sequenceName = "inum_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inum_seq_gen")
    private Long inum;

    private String uuid;

    private String imgname;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
