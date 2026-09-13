package com.garden.proyect.services.configServices;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("buscar usuario: " + username);

        try{

            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            if (usuario.isEmpty()) {
                throw new UsernameNotFoundException(
                        "Usuario no encontrado"
                );
            }

            System.out.println(
                    "Usuario encontrado: " + usuario.get().getEmail() + usuario.get().getPassword()
            );

            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.get().getEmail())
                    .password(usuario.get().getPassword())
                    .roles("USER")
                    .build();
        }catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
