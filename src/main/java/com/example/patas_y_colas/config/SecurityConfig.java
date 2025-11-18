package com.example.patas_y_colas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Asegúrate de que HttpMethod esté importado
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
                
                // Permite TODAS las peticiones OPTIONS (preflight de CORS)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                
                // REGLA 1: RUTAS PÚBLICAS (No requieren token)
                //-----------------------------------------------------
                .requestMatchers("/api/auth/**").permitAll() 
                .requestMatchers("/doc/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/referrals", "/api/referrals/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews", "/api/reviews/**").permitAll()

                
                // --- ¡LÍNEA AÑADIDA PARA EL PERFIL! ---
                // Debe ir ANTES de la regla general de "/api/users/**"
                .requestMatchers("/api/users/me").authenticated()
                // -----------------------------------------

                
                // REGLA 2: RUTAS DE ADMIN (Requieren ROLE_ADMIN)
                //-----------------------------------------------------
                // Gestión de Usuarios (CRUD completo solo para Admin)
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                
                // Gestión de Productos (Crear y Borrar solo para Admin)
                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

                // Gestión de Referidos (Crear y Borrar solo para Admin)
                .requestMatchers(HttpMethod.POST, "/api/referrals").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/referrals/**").hasRole("ADMIN")
                
                // Gestión de Reseñas (Crear y Borrar solo para Admin)
                .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                
                // REGLA 3: RUTAS AUTENTICADAS (Cualquier usuario logueado)
                //-----------------------------------------------------
                // (El endpoint de perfil ya se manejó arriba)

                
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