package com.prueba.sum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SumResponseDTO {

    private Integer result;
    private String message;
}
