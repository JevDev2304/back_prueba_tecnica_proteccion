package com.prueba.sum;

import com.prueba.sum.SumResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SumResultRepository extends JpaRepository<SumResult, Long> {

    List<SumResult> findByEmailOrderByCreatedAtDesc(String email);
}
