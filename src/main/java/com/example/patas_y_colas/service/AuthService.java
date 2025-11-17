package com.example.patas_y_colas.service;

import com.example.patas_y_colas.dtos.AuthResponseDTO;
import com.example.patas_y_colas.dtos.LoginDTO;
import com.example.patas_y_colas.dtos.RegisterDTO;
import com.example.patas_y_colas.model.Role;
import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository;
// import lombok.RequiredArgsConstructor; // <-- ELIMINADO
import org.springframework.beans.factory.annotation.Autowired; // <-- AÑADIDO
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor // <-- ELIMINADO
public class AuthService {

    // --- CAMPOS (YA NO SON 'FINAL') ---
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // --- CONSTRUCTOR CON @AUTOWIRED AÑADIDO ---
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

        return new AuthResponseDTO(token, user.getRole());
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
        
        // Lógica de Admin
        if (usuarioRepository.count() == 0) {
            usuario.setRole(Role.ROLE_ADMIN);
        } else {
            usuario.setRole(Role.ROLE_USER);
        }

        usuarioRepository.save(usuario); // <-- AQUÍ SE GUARDA

        String token = jwtService.generateToken(usuario);

        return new AuthResponseDTO(token, usuario.getRole());
    }
}