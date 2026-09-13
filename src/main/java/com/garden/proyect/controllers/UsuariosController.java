package com.garden.proyect.controllers;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.dtos.auth.SignupDto;
import com.garden.proyect.services.entitiesServices.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuariosController {

    private final UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody SignupDto signupDto) {
        return new ResponseEntity<>(usuarioService.save(signupDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findByToken(@RequestHeader("Authorization") String token ) {
        return new ResponseEntity(usuarioService.findByToken(token), HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return new ResponseEntity(usuarioService.update(id, usuario), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
