package com.prueba.sum;

import com.prueba.sum.SumRequestDTO;
import com.prueba.sum.SumResponseDTO;
import com.prueba.sum.SumResult;

import java.util.List;

public interface SumService {

    SumResponseDTO sum(SumRequestDTO request);

    List<SumResult> findByEmail(String email);
}
