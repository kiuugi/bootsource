package com.example.todo.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "todotbl")
@DynamicInsert // default 동작
public class Todo extends BaseEntity {

    @Id
    @SequenceGenerator(name = "todo_id_seq_gen", sequenceName = "todo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_seq_gen")
    @Column(name = "todo_id")
    private Long id;

    // @Column(nullable = false) / @DynamicInsert가 nullable 을 default 보다 먼저 연산해서 오류남
    @ColumnDefault("0") // sql 구문에서 default 값을 설정하는 것과 같음
    private Boolean completed;

    // @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean important;

    @Column(nullable = false)
    private String title;
}

// default 값을 삽입 : 아무것도 입력이 되지 않으면 default 값으로 입력
// JPA에서는 default 값으로 자동 삽입 하려면 @DynamicInsert 필요함
// @DynamicInsert : 데이터가 존재하는 필드만으로 insert sql 문 생성