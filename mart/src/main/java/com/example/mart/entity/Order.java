package com.example.mart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "orderItems")
@Table(name = "orders") // table 명 order 사용 불가
@Entity
public class Order {
    @SequenceGenerator(name = "mart_order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_seq_gen")
    @Id
    @Column(name = "order_id") // 주문번호
    private Long id;

    @ManyToOne
    private Member member; // 회원번호

    @CreatedDate
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // enum 에 담아져 있는 상수는 가져올 떄 1, 2 이런식으로 가져와지기 때문에 번호가아니라 문자열을 가져오라고 따로 말해줌
    private OrderStatus orderStatus; // 주문상대 -ORDER, CANCEL

    @Builder.Default
    @OneToMany(mappedBy = "order") // fetch = FetchType.EAGER
    private List<OrderItem> orderItems = new ArrayList<>();
}
