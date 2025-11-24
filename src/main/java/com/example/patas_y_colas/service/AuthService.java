package com.example.patas_y_colas.service;

import com.example.patas_y_colas.dtos.AuthResponseDTO;
import com.example.patas_y_colas.dtos.LoginDTO;
import com.example.patas_y_colas.dtos.RegisterDTO;
import com.example.patas_y_colas.model.Role;
import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UsuarioRepository usuarioRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Maneja el login de un usuario.
     */
    public AuthResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación"));

        String token = jwtService.generateToken(user);

        // SE AÑADE EL MENSAJE DE ÉXITO AL FINAL
        return new AuthResponseDTO(token, user.getRole(), "Inicio de sesión exitoso");
    }

    /**
     * Maneja el registro de un nuevo usuario.
     */
    public AuthResponseDTO register(RegisterDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }
        
        Usuario usuario = new Usuario();
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Lógica de Rol
        if (request.getEmail().toLowerCase().endsWith("@lvlup.com")) {
            usuario.setRole(Role.ROLE_ADMIN);
        } else {
            usuario.setRole(Role.ROLE_USER);
        }

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);

        // SE AÑADE EL MENSAJE DE ÉXITO AL FINAL
        return new AuthResponseDTO(token, usuario.getRole(), "Usuario registrado exitosamente");
    }
}