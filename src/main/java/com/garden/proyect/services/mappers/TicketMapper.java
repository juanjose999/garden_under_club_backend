package com.garden.proyect.services.mappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garden.proyect.entities.Evento;
import com.garden.proyect.entities.Ticket;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.dtos.ticket.TicketRequestDto;
import com.garden.proyect.services.dtos.ticket.TicketResponseDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class TicketMapper {

    public static Ticket requestDtoToTicket(TicketRequestDto ticketRequestDto, Evento evento, Usuario usuario) {
        Ticket ticket = new Ticket();
        ticket.setCodigo(ticketRequestDto.codigo());
        ticket.setTipo(ticketRequestDto.tipo());
        ticket.setPrecio(ticketRequestDto.precio());
        ticket.setEstado(ticketRequestDto.estado());
        ticket.setEvento(evento);
        ticket.setUsuario(usuario);
        return ticket;
    }

    public static TicketResponseDto ticketToRequestDto(Ticket ticket) {
        return new TicketResponseDto(
            true,
                "Ticket created successfully",
                new TicketResponseDto.Data(
                        ticket.getId(),
                        ticket.getCodigo(),
                        ticket.getTipo(),
                        ticket.getPrecio(),
                        ticket.getEstado(),
                        ticket.getFechaCompra(),
                        ticket.getFechaUso(),

                        new TicketResponseDto.EventoData(
                                ticket.getEvento().getId(),
                                ticket.getEvento().getNombre(),
                                ticket.getEvento().getCategoria(),
                                ticket.getEvento().getLugar(),
                                ticket.getEvento().getFechaEvento()
                        ),

                        new TicketResponseDto.UsuarioData(
                                ticket.getUsuario().getId(),
                                ticket.getUsuario().getNombre(),
                                ticket.getUsuario().getApellido(),
                                ticket.getUsuario().getEmail()
                        )
                )

        );
    }

}


