package com.prueba.repository;

import com.prueba.model.SumResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SumResultRepository extends JpaRepository<SumResult, Long> {

    List<SumResult> findByEmailOrderByCreatedAtDesc(String email);
}
