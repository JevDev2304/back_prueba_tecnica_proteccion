package com.prueba.service;

public interface JwtService {
    String generateToken();
    boolean validateToken(String token);
}
