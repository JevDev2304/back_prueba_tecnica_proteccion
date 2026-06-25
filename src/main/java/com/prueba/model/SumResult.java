package com.prueba.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sum_results")
@Getter
@Setter
@NoArgsConstructor
public class SumResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer a;

    @Column(nullable = false)
    private Integer b;

    @Column(nullable = false)
    private Integer result;

    @Column(nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SumResult(Integer a, Integer b, Integer result, String email) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.email = email;
    }
}
