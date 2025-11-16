package com.example.patas_y_colas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                
                // REGLA 1: RUTAS PÚBLICAS (No requieren token)
                //-----------------------------------------------------
                // Rutas de autenticación
                .requestMatchers("/api/auth/**").permitAll() 
                // Rutas de Swagger (documentación)
                .requestMatchers("/doc/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                
                // Rutas públicas para VER contenido (GET)
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/referrals", "/api/referrals/**").permitAll()
                // Asumimos que tendrás un /api/reviews para tu página de reseñas
                .requestMatchers(HttpMethod.GET, "/api/reviews", "/api/reviews/**").permitAll()


                // --- ¡CAMBIO AÑADIDO (PLAN B)! ---
                // Hacemos pública la ruta temporal para promover al admin.
                // Debe ir ANTES de la regla /api/users/**
                .requestMatchers("/api/users/promote-admin").permitAll() 


                // REGLA 2: RUTAS DE ADMIN (Requieren ROLE_ADMIN)
                //-----------------------------------------------------
                // Gestión de Usuarios (CRUD completo solo para Admin)
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                
                // Gestión de Productos (Crear y Borrar solo para Admin)
                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                // .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN") // (Si añades "Editar")

                // Gestión de Referidos (Crear y Borrar solo para Admin)
                .requestMatchers(HttpMethod.POST, "/api/referrals").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/referrals/**").hasRole("ADMIN")
                
                // Gestión de Reseñas (Crear y Borrar solo para Admin)
                // Asumimos estos endpoints para tu /admin/reviews
                .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                
                // REGLA 3: RUTAS AUTENTICADAS (Cualquier usuario logueado)
                //-----------------------------------------------------
                // (Ejemplo: para tu página de /perfil. Deberías crear un /api/profile)
                // .requestMatchers("/api/profile/me").authenticated() 

                
                // REGLA 4: TODO LO DEMÁS
                //-----------------------------------------------------
                // Cualquier otra petición que no coincida, debe estar autenticada
                .anyRequest().authenticated() 
        );

        http.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}