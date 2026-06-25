package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SumResponseDTO {

    private Integer result;
    private String message;
}
