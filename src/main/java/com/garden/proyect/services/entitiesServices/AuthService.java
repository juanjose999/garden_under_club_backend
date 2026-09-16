package com.garden.proyect.services.entitiesServices;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.configServices.CustomUserDetailsService;
import com.garden.proyect.services.configServices.JwtService;
import com.garden.proyect.services.dtos.auth.LoginRequestDto;
import com.garden.proyect.repositories.UsuarioRepository;
import com.garden.proyect.services.dtos.auth.LoginResponseDto;
import com.garden.proyect.services.dtos.auth.SignupResponseDto;
import com.garden.proyect.services.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    public LoginResponseDto login(LoginRequestDto loginDto) {
        System.out.println("logindto:"+loginDto.toString());
        try{
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDto.email(),
                                    loginDto.password()
                            )
                    );

            if(!authentication.isAuthenticated()){
                return new LoginResponseDto(
                        false,
                        "Login failed",
                        Map.of(),
                        null
                );
            }

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            String token = jwtService.generateToken(customUserDetailsService
                    .loadUserByUsername(loginDto.email()));

            if (token == null) {
                throw new UsernameNotFoundException("Error to create token");
            }

            Optional<Usuario> usuario = usuarioRepository.findByEmail(loginDto.email());
            System.out.println("usuario encontado"+ usuario.toString() + usuario.get().toString());

            return new LoginResponseDto(
                    true,
                    "Login Successful",
                    Map.of("type","Bearer", "token",token),
                    UsuarioMapper.toDto(usuario.get())
            );

        }catch (Exception e){
            return new LoginResponseDto(
                    false,
                    "Login failed",
                    Map.of("error",e.getMessage()),
                    null
            );
        }
    }

    public SignupResponseDto signup(UserDetails userDetails, Usuario usuario) {

        String token = jwtService.generateToken(userDetails);
        return new SignupResponseDto(
                true,
                "Signup Successful",
                Map.of("type","Bearer",
                        "token",token),
                UsuarioMapper.toDto(usuario)

        );

    }

}