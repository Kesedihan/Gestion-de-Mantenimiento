package com.jorel.proyectogestionmantcorrectivo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService usuarioDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UsuarioDetailsService usuarioDetailsService) {
        this.jwtUtil = jwtUtil;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    /**
     * Filtro que se ejecuta una vez por petición. Su responsabilidad:
     * - Leer el header Authorization (Bearer token)
     * - Validar el token JWT
     * - Cargar UserDetails y montar Authentication en el SecurityContext si el token es válido
     *
     * Importante: este filtro NO lanza errores si el token es inválido; simplemente no autentica
     * la petición y deja que la configuración de seguridad (AuthenticationEntryPoint) responda si
     * el recurso requiere autenticación.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        // Debug: registrar si llegó el header Authorization (sin imprimir el token)
        log.debug("Incoming request {} {} - Authorization header present={}", request.getMethod(), request.getRequestURI(), header != null);

        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            // No imprimimos el token por seguridad, solo confirmamos su longitud aproximada
            log.debug("Authorization header starts with Bearer, length={}", header.length());
            token = header.substring(7);
            try {
                if (jwtUtil.validateToken(token)) {
                    username = jwtUtil.extractUsername(token);
                } else {
                    log.debug("JWT validateToken returned false for request {}", request.getRequestURI());
                }
            } catch (Exception ex) {
                // invalid token -> continue without authentication
                log.debug("JWT validation failed for request {}: {} ({}: {})", request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage(), header == null ? "no-header" : "has-header");
            }
        } else {
            if (header != null) {
                log.debug("Authorization header present but does not start with 'Bearer '");
            }
        }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
        log.debug("Authenticated user {} for request {}", username, request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }
}
