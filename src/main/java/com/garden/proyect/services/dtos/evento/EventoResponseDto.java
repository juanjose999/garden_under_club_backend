package com.garden.proyect.services.dtos.evento;

import java.util.List;

public record EventoResponseDto(
        Boolean success,
        String message,
        Data data
) {
    public record Data(

            Long id,
            String nombre,
            String descripcion,
            String categoria,
            String lugar,
            String ciudad,
            String direccion,
            java.time.LocalDateTime fechaEvento,
            java.time.LocalDateTime fechaCreacion,
            Integer capacidad,
            Double precioBase,
            String estado,
            List<String> linkPhoto

    ) {
    }
}
