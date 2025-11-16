package com.example.patas_y_colas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // Habilita la seguridad a nivel de método (opcional pero bueno)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; // Nuestro filtro (Paso 4)
    private final AuthenticationProvider authenticationProvider; // Nuestro provider (Paso 3)

    /**
     * Define la cadena de filtros de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // 1. Deshabilitamos CSRF (Cross-Site Request Forgery) - común en APIs REST
        http.csrf(csrf -> csrf.disable());

        // 2. Definimos las reglas de autorización (quién puede ver qué)
        http.authorizeHttpRequests(auth -> auth
                
                // REGLA 1: RUTAS PÚBLICAS
                // Hacemos públicas las rutas de Auth (Login/Register)
                .requestMatchers("/api/auth/**").permitAll() 
                // Hacemos públicas las rutas de Swagger (documentación de la API)
                .requestMatchers("/doc/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                
                // REGLA 2: RUTAS DE ADMIN (como pediste)
                // Solo los usuarios con rol "ADMIN" pueden acceder a estas rutas
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                .requestMatchers("/api/products/**").hasRole("ADMIN")
                .requestMatchers("/api/referrals/**").hasRole("ADMIN")

                // REGLA 3: (Opcional, si tuvieras rutas para USER)
                // .requestMatchers("/api/pedidos/**").hasAnyRole("ADMIN", "USER")

                // REGLA 4: TODO LO DEMÁS
                // Cualquier otra petición que no coincida, debe estar autenticada
                .anyRequest().authenticated() 
        );

        // 3. Política de Sesión: STATELESS (sin estado)
        // No guardamos sesiones en el servidor; cada petición se valida con el token.
        http.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 4. Indicamos qué proveedor de autenticación usar (el nuestro del Paso 3)
        http.authenticationProvider(authenticationProvider);

        // 5. Añadimos nuestro filtro JWT ANTES del filtro estándar de Spring
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}