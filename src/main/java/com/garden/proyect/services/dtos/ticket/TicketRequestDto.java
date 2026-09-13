package com.garden.proyect.services.dtos.ticket;

public record TicketRequestDto(
        Long eventoId,
        String codigo,
        String tipo,
        Double precio,
        String estado
) {
}
