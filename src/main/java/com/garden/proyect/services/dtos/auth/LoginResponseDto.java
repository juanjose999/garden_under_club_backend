package com.garden.proyect.services.dtos.auth;

import com.garden.proyect.services.dtos.usuario.UsuarioResponseDto;

import java.util.Map;

public record LoginResponseDto(
        Boolean success, String message,  Map<String, Object> data, UsuarioResponseDto user
) {
}
