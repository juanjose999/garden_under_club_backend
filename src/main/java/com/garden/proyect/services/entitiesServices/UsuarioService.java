package com.garden.proyect.services.entitiesServices;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.configServices.CustomUserDetailsService;
import com.garden.proyect.services.configServices.JwtService;
import com.garden.proyect.services.dtos.auth.SignupDto;
import com.garden.proyect.repositories.UsuarioRepository;
import com.garden.proyect.services.dtos.auth.SignupResponseDto;
import com.garden.proyect.services.dtos.usuario.UsuarioResponseDto;
import com.garden.proyect.services.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;


    public SignupResponseDto save(SignupDto signupDto) {

        Usuario usuario = new Usuario();
        
        usuario.setNombre(signupDto.nombre());
        usuario.setApellido(signupDto.apellido());
        usuario.setEmail(signupDto.email());
        usuario.setPassword(passwordEncoder.encode(signupDto.password()));
        usuario.setTelefono(signupDto.telefono());
        usuario.setDocumento(signupDto.documento());
        usuario.setRole("ROLE_USER");


        Usuario savedUser = usuarioRepository.save(usuario);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(usuario.getEmail());
        return authService.signup(userDetails, savedUser);
    }

    public UsuarioResponseDto findByToken(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        System.out.println("emailC: " + email);
        Optional<Usuario> u = usuarioRepository.findByEmail(email);

        return UsuarioMapper.toDto(u.get());
    }

    public Usuario findByUsernameAndPassword(String email, String password) {
        return usuarioRepository.findUsuarioByNombreAndPassword(email, password);
    }

    public Usuario update(Long id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()) return null;
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }



}
