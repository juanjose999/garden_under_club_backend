package com.garden.proyect.services.dtos.usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDto(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String documento,
        Boolean activo,
        String role,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion
) {
}
