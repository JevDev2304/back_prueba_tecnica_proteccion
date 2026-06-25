package com.prueba.auth;

public interface JwtService {
    String generateToken();
    boolean validateToken(String token);
}
