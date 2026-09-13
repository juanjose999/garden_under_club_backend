package com.garden.proyect.services.dtos.ticket;

import java.time.LocalDateTime;

public record TicketResponseDto(
        Boolean success,
        String message,
        Data data
) {
    public record Data(

            Long id,
            String codigo,
            String tipo,
            Double precio,
            String estado,
            LocalDateTime fechaCompra,
            LocalDateTime fechaUso,

            EventoData evento,
            UsuarioData usuario

    ) {}

    public record EventoData(
            Long id,
            String nombre,
            String categoria,
            String lugar,
            LocalDateTime fechaEvento
    ) {}

    public record UsuarioData(

            Long id,
            String nombre,
            String apellido,
            String email

    ) {}
}
