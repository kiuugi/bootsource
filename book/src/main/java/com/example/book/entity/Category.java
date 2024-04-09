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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
@Entity
public class Category extends BaseEntity {

    @Id
    @SequenceGenerator(name = "book_category_seq_gen", sequenceName = "category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_category_seq_gen")
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Book> books = new ArrayList<>();
}
