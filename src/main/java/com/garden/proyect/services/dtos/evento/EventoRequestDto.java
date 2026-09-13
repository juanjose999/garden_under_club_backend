package com.garden.proyect.services.dtos.evento;
import java.time.LocalDateTime;

public record EventoRequestDto(
        String nombre,
        String descripcion,
        String categoria,
        String lugar,
        String ciudad,
        String direccion,
        LocalDateTime fechaEvento,
        Integer capacidad,
        Double precioBase
) {
}
