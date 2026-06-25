package com.prueba.controller;

import com.prueba.dto.TokenRequestDTO;
import com.prueba.dto.TokenResponseDTO;
import com.prueba.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Obtención de token JWT")
public class AuthController {

    private final JwtService jwtService;
    private final String appPassword;
    private final long expirationMs;

    public AuthController(JwtService jwtService,
                          @Value("${app.password}") String appPassword,
                          @Value("${jwt.expiration-ms}") long expirationMs) {
        this.jwtService = jwtService;
        this.appPassword = appPassword;
        this.expirationMs = expirationMs;
    }

    @PostMapping("/token")
    @Operation(
        summary = "Obtener token JWT",
        description = "Valida la contraseña de la aplicación y devuelve un JWT para acceder a los endpoints protegidos"
    )
    public ResponseEntity<TokenResponseDTO> getToken(@Valid @RequestBody TokenRequestDTO request) {
        if (!appPassword.equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.generateToken();
        return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer", expirationMs));
    }
}
