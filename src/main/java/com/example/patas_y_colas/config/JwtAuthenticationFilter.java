package com.example.patas_y_colas.config;

import com.example.patas_y_colas.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // Crea un constructor con los campos 'final'
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Se ejecuta UNA vez por petición

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // El bean que creamos en ApplicationConfig

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain // "Cadena" de filtros de Spring Security
    ) throws ServletException, IOException {

        // 1. Obtenemos la cabecera "Authorization" de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Si no hay cabecera, o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Salimos del filtro
        }

        // 3. Extraemos el token (quitando "Bearer ")
        jwt = authHeader.substring(7); // "Bearer " tiene 7 caracteres

        // 4. Extraemos el email (username) del token usando nuestro JwtService
        userEmail = jwtService.extractUsername(jwt);

        // 5. Verificamos que el email exista y que el usuario NO esté ya autenticado
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 6. Cargamos los UserDetails (nuestra entidad Usuario) usando el email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 7. Validamos el token (comparamos el token con el usuario de la BD)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 8. Si es válido, creamos un token de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No necesitamos credenciales (password) aquí
                        userDetails.getAuthorities() // Pasamos los roles (ej. ROLE_ADMIN)
                );
                
                // Añadimos detalles de la petición (IP, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9. ¡Guardamos la autenticación en el Contexto de Seguridad!
                // Esto "autentica" al usuario para esta petición
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 10. Pasamos al siguiente filtro de la cadena
        filterChain.doFilter(request, response);
    }
}