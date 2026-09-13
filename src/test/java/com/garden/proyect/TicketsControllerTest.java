package com.garden.proyect;

import com.garden.proyect.controllers.TicketsController;
import com.garden.proyect.entities.Ticket;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.UsuarioRepository;
import com.garden.proyect.services.dtos.ticket.TicketRequestDto;
import com.garden.proyect.services.dtos.ticket.TicketResponseDto;
import com.garden.proyect.services.entitiesServices.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TicketsControllerTest {



    @Mock
    private TicketService ticketService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TicketsController ticketsController;



    @Test
    void createTicket() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        "juan@gmail.com",
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        Usuario usuario = new Usuario();
        usuario.setEmail("juan@gmail.com");

        when(usuarioRepository.findByEmail("juan@gmail.com"))
                .thenReturn(Optional.of(usuario));

        when(ticketService.createTicket(any(), any()))
                .thenReturn(new TicketResponseDto(true,"ok",null));

        ResponseEntity<?> response =
                ticketsController.create(new TicketRequestDto(
                        1L,
                        "121", "GENERAL",
                        12000.00,
                        "ACTIVO"
                ), "Bearer token");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void findTicket() {

        TicketResponseDto responseDto = new TicketResponseDto(
                true,
                "ok",
                null
        );

        when(ticketService.findTicketById(1L))
                .thenReturn(responseDto);

        ResponseEntity<?> response = ticketsController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());

        verify(ticketService).findTicketById(1L);
    }

    @Test
    void findAllTickets() {

        // Simular usuario autenticado
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        "juan@gmail.com",
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Usuario encontrado en la BD
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@gmail.com");

        when(usuarioRepository.findByEmail("juan@gmail.com"))
                .thenReturn(Optional.of(usuario));

        // Respuesta simulada del servicio
        List<TicketResponseDto> tickets = List.of(
                new TicketResponseDto(true, "Ticket 1", null),
                new TicketResponseDto(true, "Ticket 2", null)
        );

        when(ticketService.findAllTicketsByUsuario(usuario))
                .thenReturn(tickets);

        // Ejecutar método del controlador
        ResponseEntity<?> response = ticketsController.findAll();

        // Validaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());

        verify(usuarioRepository).findByEmail("juan@gmail.com");
        verify(ticketService).findAllTicketsByUsuario(usuario);
    }

    @Test
    void updateTicket() {

        Long id = 1L;

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setEstado("INACTIVE");

        when(ticketService.updateTicket(id, ticket))
                .thenReturn(ticket);

        ResponseEntity<?> response = ticketsController.update(id, ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());

        verify(ticketService).updateTicket(id, ticket);
    }

    @Test
    void deleteTicket() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setEstado("ACTIVO");

        ResponseEntity<?> response = ticketsController.delete(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}