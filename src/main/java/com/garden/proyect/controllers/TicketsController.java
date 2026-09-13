package com.garden.proyect.controllers;

import com.garden.proyect.entities.Ticket;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.UsuarioRepository;
import com.garden.proyect.services.dtos.ticket.TicketRequestDto;
import com.garden.proyect.services.entitiesServices.TicketService;
import com.garden.proyect.services.entitiesServices.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TicketsController {

    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;


    @PostMapping
    public ResponseEntity create(@RequestBody TicketRequestDto ticketRequestDto,
                                 @RequestHeader("Authorization") String authHeader) {

        String token = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        System.out.println("email:"+token);
        Optional<Usuario> u = usuarioRepository.findByEmail(token);

        return new ResponseEntity(ticketService.createTicket(ticketRequestDto, u.get()), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {

        return new ResponseEntity(ticketService.findTicketById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        return new ResponseEntity(ticketService.findAllTicketsByUsuario(u.get()), HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Ticket ticket) {
        return new ResponseEntity(ticketService.updateTicket(id, ticket), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
