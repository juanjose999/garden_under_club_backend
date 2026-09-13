package com.garden.proyect.controllers;

import com.garden.proyect.services.dtos.auth.LoginRequestDto;
import com.garden.proyect.services.dtos.auth.SignupDto;
import com.garden.proyect.services.entitiesServices.AuthService;
import com.garden.proyect.services.entitiesServices.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        if(loginRequestDto.email() == null && loginRequestDto.password() == null) {
            return new ResponseEntity<>(
                    Map.of("resultado","El campo de email y contrasenia no pueden estar vacios."), HttpStatus.BAD_REQUEST);
        }

        if(loginRequestDto.email() == null) {
            return new ResponseEntity<>(
                    Map.of("resultado","El campo de email no puede estar vacio."),HttpStatus.BAD_REQUEST);
        }

        if(loginRequestDto.password() == null) {
            return new ResponseEntity<>(
                    Map.of("resultado","El campo contrasenia no puede estar vacio."),HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto signupDto) {
        return new ResponseEntity<>(
                usuarioService.save(signupDto), HttpStatus.OK);
    }

}
