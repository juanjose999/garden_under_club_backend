package com.garden.proyect;

import com.garden.proyect.controllers.UsuariosController;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.dtos.auth.SignupDto;
import com.garden.proyect.services.dtos.auth.SignupResponseDto;
import com.garden.proyect.services.dtos.usuario.UsuarioResponseDto;
import com.garden.proyect.services.entitiesServices.UsuarioService;
import com.garden.proyect.services.mappers.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuariosControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuariosController controller;

    @Test
    void listarUsuarios() {

        SignupDto requestDto = new SignupDto(
                "Juan",
                "Perez",
                "juan@test.com",
                "123456",
                "3001234567",
                "123456789"
        );


        UsuarioResponseDto responseDtoUser = new UsuarioResponseDto(
                1L,
                "Juan",
                "Perez",
                "juan@test.com",
                "123456",
                "3001234567",
                true
        );

        SignupResponseDto responseDto = new SignupResponseDto(
                true,
                "usuario creado",
                null,
                responseDtoUser
        );

        when(service.save(requestDto)).thenReturn(responseDto);

        ResponseEntity<?> response = controller.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        assertEquals(responseDto.user().email(), responseDto.user().email());

        verify(service).save(requestDto);
    }

    @Test
    void findUserByToken(){
        String token = "Bearer abc123";

        UsuarioResponseDto responseDtoUser = new UsuarioResponseDto(
                1L,
                "Juan",
                "Perez",
                "juan@test.com",
                "123456",
                "3001234567",
                true
        );

        SignupResponseDto responseDto = new SignupResponseDto(
                true,
                "usuario creado",
                null,
                responseDtoUser
        );

        when(service.findByToken(token)).thenReturn(responseDtoUser);

        ResponseEntity<?> response = controller.findByToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoUser, response.getBody());

        verify(service).findByToken(token);
    }

    @Test
    void debeActualizarUsuarioCorrectamente() {

        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@test.com");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(id);
        usuarioActualizado.setNombre("Juan");
        usuarioActualizado.setApellido("Perez");
        usuarioActualizado.setEmail("juan1234nuevo@test.com");

        when(service.update(id, usuario)).thenReturn(usuarioActualizado);

        ResponseEntity<?> response = controller.update(id, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioActualizado, response.getBody());

        verify(service).update(id, usuario);
    }

    @Test
    void deleteUser(){
        Long id = 1L;

        // Act
        ResponseEntity<?> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());

        verify(service).delete(id);
    }

}