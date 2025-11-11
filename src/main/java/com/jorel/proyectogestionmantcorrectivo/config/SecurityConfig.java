package com.jorel.proyectogestionmantcorrectivo.config;

import com.jorel.proyectogestionmantcorrectivo.security.JwtAuthenticationEntryPoint;
import com.jorel.proyectogestionmantcorrectivo.security.JwtAuthenticationFilter;
import com.jorel.proyectogestionmantcorrectivo.security.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    /**
     * Configuración central de seguridad de la aplicación.
     * <p>
     * Responsabilidades principales:
     * - Configurar CORS para los orígenes de desarrollo.
     * - Deshabilitar CSRF (solo para desarrollo / API REST stateless).
     * - Establecer el manejo de excepciones para accesos no autorizados.
     * - Forzar SessionCreationPolicy.STATELESS porque usamos JWT.
     * - Permitir rutas públicas (login y registro) y proteger el resto con JWT.
     * - Registrar el filtro JWT antes del filtro de autenticación por formulario.
     * </p>
     * Notas de seguridad:
     * - En producción ajuste `corsConfigurationSource()` para permitir solo el origen del frontend.
     * - Asegúrese de usar `app.jwt.secret` seguro y no versionarlo en el repo.
     */

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UsuarioDetailsService usuarioDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
                // Autenticación y registro: abiertos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                // Endpoints públicos: abiertos (sin autenticación)
                .requestMatchers("/api/prioridades/**").permitAll()
                .requestMatchers("/api/solicitudes", "/api/solicitudes/**").permitAll()
                .requestMatchers("/api/ordenes", "/api/ordenes/**").permitAll()
                .requestMatchers("/api/categorias", "/api/categorias/**").permitAll()
                .requestMatchers("/api/ubicaciones", "/api/ubicaciones/**").permitAll()
                .requestMatchers("/api/equipos", "/api/equipos/**").permitAll()

                // Recursos públicos generales
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/public/**").permitAll()

                // Lo demás requiere JWT válido
                .anyRequest().authenticated()
        )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
    // Allow common local dev origins and the production frontend origin provided by the user.
    // Use exact origins when allowCredentials=true.
    configuration.setAllowedOrigins(Arrays.asList(
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:3000",
        "https://gestionresportestechsolutions.dev"
    )); // ajustar según frontend
        // Also set allowed origin patterns to be more flexible for dev (optional)
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type","X-Requested-With","Accept","Origin"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
