package com.garden.proyect.services.dtos.auth;

import com.garden.proyect.services.dtos.usuario.UsuarioResponseDto;

import java.util.Map;

public record SignupResponseDto(
        Boolean success, String message, Map<String, Object> data, UsuarioResponseDto user
) {
}
