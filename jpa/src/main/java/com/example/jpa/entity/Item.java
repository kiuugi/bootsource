package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Item {

    @Id
    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    private Long id;

    @Column(length = 50)
    private String itemNm;

    private int price;

    private int stockNumber;

    @Column(length = 255)
    private String itemDetail;

    @Column(length = 255)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // sell, soldOut

    @CreatedDate // 생성한 시간 자동으로 넣어줌
    private LocalDateTime regTime;

    @LastModifiedDate // 마지막으로 접근한 시간
    private LocalDateTime upDateTime;
}
