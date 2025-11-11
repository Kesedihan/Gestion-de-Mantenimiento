package com.jorel.proyectogestionmantcorrectivo.controller.dto;

public class AuthResponse {
    private String token;

    public AuthResponse() {}
    public AuthResponse(String token) { this.token = token; }

    /**
     * Respuesta simplificada con el token JWT.
     * El cliente debe leer `token` y guardarlo para futuras peticiones.
     */
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
