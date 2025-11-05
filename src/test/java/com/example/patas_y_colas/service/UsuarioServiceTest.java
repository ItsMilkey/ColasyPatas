package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository;
import com.example.patas_y_colas.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setRut("12.345.678-9");
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setContrasena("1234");
        usuario.setRol("admin");
    }

    @Test
    void testFindByRut_ReturnsUsuario() {
        when(usuarioRepository.findById(usuario.getRut())).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findByRut(usuario.getRut());

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
    }

    @Test
    void testSave_NewUsuario_ReturnsSavedUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.save(usuario);

        assertNotNull(saved);
        assertEquals("juan@example.com", saved.getEmail());
    }

    @Test
    void testDelete_UsuarioExiste() {
        when(usuarioRepository.existsById(usuario.getRut())).thenReturn(true);

        usuarioService.delete(usuario.getRut());

        verify(usuarioRepository).deleteById(usuario.getRut());
    }

    @Test
    void testDelete_UsuarioNoExiste_LanzaExcepcion() {
        when(usuarioRepository.existsById(usuario.getRut())).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.delete(usuario.getRut());
        });

        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
    }

    @Test
    void testBuscarPorCorreo_UsuarioExiste() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario found = usuarioService.buscarPorCorreo(usuario.getEmail());

        assertEquals(usuario.getRut(), found.getRut());
    }

    @Test
    void testBuscarPorCorreo_UsuarioNoExiste_LanzaExcepcion() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.buscarPorCorreo(usuario.getEmail());
        });
    }

    @Test
    void testAutenticar_ConCredencialesCorrectas() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        boolean autenticado = usuarioService.autenticar(usuario.getEmail(), "1234");

        assertTrue(autenticado);
    }

    @Test
    void testAutenticar_ConCredencialesIncorrectas() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        boolean autenticado = usuarioService.autenticar(usuario.getEmail(), "wrongpass");

        assertFalse(autenticado);
    }

    @Test
    void testAutenticar_EmailNoExiste() {
        when(usuarioRepository.findByEmail("otro@email.com")).thenReturn(Optional.empty());

        boolean autenticado = usuarioService.autenticar("otro@email.com", "1234");

        assertFalse(autenticado);
    }
}
