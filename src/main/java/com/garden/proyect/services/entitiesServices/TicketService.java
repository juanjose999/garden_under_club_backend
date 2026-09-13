package com.garden.proyect.services.entitiesServices;

import com.garden.proyect.entities.Evento;
import com.garden.proyect.entities.Ticket;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.EventoRepository;
import com.garden.proyect.repositories.TicketRepository;
import com.garden.proyect.services.dtos.ticket.TicketRequestDto;
import com.garden.proyect.services.dtos.ticket.TicketResponseDto;
import com.garden.proyect.services.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final UsuarioService usuarioService;
    private final EventoRepository eventoRepository;
    private final TicketRepository ticketRepository;

    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto, Usuario usuario) {

        Optional<Evento> evento = eventoRepository.findById(ticketRequestDto.eventoId());

        if(usuario == null ){
            return new TicketResponseDto(
                    false,
                    "Usuario no encontrado",
                    null
            );
        }
        if(evento.isEmpty()) {
            return new TicketResponseDto(
                    false,
                    "Evento no encontrado",
                    null
            );
        };

        Ticket ticket = TicketMapper.requestDtoToTicket(ticketRequestDto, evento.get(), usuario);
        ticketRepository.save(ticket);
        return TicketMapper.ticketToRequestDto(ticket);
    }

    public List<TicketResponseDto> findAllTicketsByUsuario(Usuario usuario) {
        List<Ticket> tickets = ticketRepository.findAllByUsuarioId(usuario.getId());
        return tickets.stream().map(TicketMapper::ticketToRequestDto).collect(Collectors.toList());
    }

    public TicketResponseDto findTicketById(Long id) {
        return TicketMapper.ticketToRequestDto(ticketRepository.findById(id).orElse(null));
    }

    public Ticket updateTicket(Long id, Ticket ticket) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            return null;
        }
        ticket.setId(id);
        ticket.setEstado(ticket.getEstado());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}
