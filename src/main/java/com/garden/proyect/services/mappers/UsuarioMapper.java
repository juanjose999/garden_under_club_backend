package com.garden.proyect.services.mappers;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.dtos.usuario.UsuarioResponseDto;

public class UsuarioMapper {

    public static UsuarioResponseDto toDto(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDocumento(),
                usuario.getActivo(),
                usuario.getRole(),
                usuario.getFechaRegistro(),
                usuario.getFechaActualizacion()
        );
    }

}
