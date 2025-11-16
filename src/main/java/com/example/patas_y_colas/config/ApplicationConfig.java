package com.example.patas_y_colas.config;

import com.example.patas_y_colas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // Inyectamos nuestro repositorio de usuarios
    private final UsuarioRepository usuarioRepository;

    /**
     * Bean 1: UserDetailsService
     * Le dice a Spring Security cómo cargar un usuario por su "username" (nuestro email).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }

    /**
     * Bean 2: PasswordEncoder
     * Define el método de encriptación de contraseñas. Usamos BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean 3: AuthenticationManager
     * El gestor principal de autenticación. Lo usaremos en nuestro AuthController.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean 4: AuthenticationProvider (El "pegamento")
     * Este bean une el UserDetailsService (Bean 1) y el PasswordEncoder (Bean 2).
     * Le dice al AuthenticationManager (Bean 3) qué método de encriptación usar
     * y cómo buscar a los usuarios.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Setea el Bean 1
        authProvider.setPasswordEncoder(passwordEncoder());     // Setea el Bean 2
        return authProvider;
    }
}