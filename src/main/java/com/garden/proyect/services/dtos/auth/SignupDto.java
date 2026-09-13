package com.garden.proyect.services.dtos.auth;

public record SignupDto(
        String nombre,
        String apellido,
        String email,
        String password,
        String telefono,
        String documento
) {
}

