package com.prueba.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SumRequestDTO {

    @NotNull(message = "El campo 'a' es obligatorio")
    private Integer a;

    @NotNull(message = "El campo 'b' es obligatorio")
    private Integer b;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
}
