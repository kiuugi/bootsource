package com.example.book.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "books")
@Setter
@Getter
@Builder
@Entity
public class Publisher extends BaseEntity {

    @Id
    @SequenceGenerator(name = "book_publisher_seq_gen", sequenceName = "publisher_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_publisher_seq_gen")
    @Column(name = "publisher_id")
    private Long id;

    @Column(name = "publisher_name")
    private String name;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();
}