package com.prueba.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
