package com.prueba.service;

import com.prueba.dto.SumRequestDTO;
import com.prueba.dto.SumResponseDTO;
import com.prueba.model.SumResult;

import java.util.List;

public interface SumService {

    SumResponseDTO sum(SumRequestDTO request);

    List<SumResult> findByEmail(String email);
}
