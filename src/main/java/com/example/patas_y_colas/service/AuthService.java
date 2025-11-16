package com.example.patas_y_colas.service;

import com.example.patas_y_colas.dtos.AuthResponseDTO;
import com.example.patas_y_colas.dtos.LoginDTO;
import com.example.patas_y_colas.dtos.RegisterDTO;
import com.example.patas_y_colas.model.Role;
import com.example.patas_y_colas.model.Usuario; // 1. IMPORTAMOS USUARIO
import com.example.patas_y_colas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails; // Ya no es necesario aquí
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Maneja el login de un usuario.
     */
    public AuthResponseDTO login(LoginDTO request) {
        // 1. Autentica al usuario usando el manager (Bean del Paso 3)
        // Esto verifica si el email y la contraseña son correctos
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Si la autenticación fue exitosa, busca al usuario (COMO "USUARIO")
        //    Cambiamos "UserDetails" por "Usuario" para poder acceder a .getRole()
        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación"));

        // 3. Genera un token JWT para ese usuario
        String token = jwtService.generateToken(user);

        // 4. Devuelve el token Y EL ROL (¡CAMBIO AQUÍ!)
        return new AuthResponseDTO(token, user.getRole());
    }

    /**
     * Maneja el registro de un nuevo usuario.
     */
    public AuthResponseDTO register(RegisterDTO request) {
        // 1. Verifica si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }
        
        // 2. Crea el nuevo objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        // 3. ¡IMPORTANTE! Encripta la contraseña
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // 4. Asigna un rol por defecto.
        // Lógica simple: el primer usuario (id=1) es ADMIN, el resto son USER.
        // En una app real, esto se manejaría de forma diferente.
        if (usuarioRepository.count() == 0) {
            usuario.setRole(Role.ROLE_ADMIN);
        } else {
            usuario.setRole(Role.ROLE_USER);
        }

        // 5. Guarda el nuevo usuario en la BD
        usuarioRepository.save(usuario);

        // 6. Genera un token para el nuevo usuario
        String token = jwtService.generateToken(usuario);

        // 7. Devuelve el token Y EL ROL (¡CAMBIO AQUÍ!)
        return new AuthResponseDTO(token, usuario.getRole());
    }
}