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

    public AuthResponseDTO login(LoginDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            // Lanzamos error si la autenticación falla
            throw new RuntimeException("Credenciales incorrectas");
        }

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);

        // IMPORTANTE: Usamos el constructor con mensaje que arreglamos antes
        return new AuthResponseDTO(token, user.getRole(), "Inicio de sesión exitoso");
    }

    public AuthResponseDTO register(RegisterDTO request) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            // ESTO ES LO QUE VERÁS EN POSTMAN (Error 400)
            throw new RuntimeException("El email " + request.getEmail() + " ya está registrado.");
        }
        
        Usuario usuario = new Usuario();
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        if (request.getEmail().toLowerCase().endsWith("@lvlup.com")) {
            usuario.setRole(Role.ROLE_ADMIN);
        } else {
            usuario.setRole(Role.ROLE_USER);
        }

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);

        return new AuthResponseDTO(token, usuario.getRole(), "Usuario registrado exitosamente");
    }
}