package com.jorel.proyectogestionmantcorrectivo.controller;

import com.jorel.proyectogestionmantcorrectivo.security.JwtUtil;
import com.jorel.proyectogestionmantcorrectivo.security.UsuarioDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Controlador responsable únicamente por autenticación (login).
     *
     * Flujo de login:
     * 1) El cliente (React/Postman) hace POST /api/auth/login con { correo, contrasena }.
     * 2) Se delega a AuthenticationManager; si es válido generamos un JWT con roles.
     * 3) El cliente debe almacenar el token y enviarlo en Authorization: Bearer <token>.
     *
     * Nota: la responsabilidad de registro se dejó en `POST /api/usuarios` para evitar duplicidad.
     */

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService usuarioDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioDetailsService usuarioDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody com.jorel.proyectogestionmantcorrectivo.controller.dto.LoginRequest request) {
        try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
        );

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(request.getCorreo());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtUtil.generateToken(userDetails.getUsername(), roles);
            return ResponseEntity.ok(new com.jorel.proyectogestionmantcorrectivo.controller.dto.AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // Registration via POST /api/usuarios is allowed (public). The explicit /api/auth/register endpoint
    // was removed to avoid duplicate registration flows.

}
