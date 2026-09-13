package com.garden.proyect.services.dtos.usuario;

public record UsuarioResponseDto(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String documento,
        Boolean activo
) {
}
