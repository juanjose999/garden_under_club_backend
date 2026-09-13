package com.garden.proyect.services.entitiesServices;

import com.garden.proyect.entities.Evento;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.EventoRepository;
import com.garden.proyect.repositories.UsuarioRepository;
import com.garden.proyect.services.configServices.JwtService;
import com.garden.proyect.services.dtos.evento.EventoRequestDto;
import com.garden.proyect.services.dtos.evento.EventoResponseDto;
import com.garden.proyect.services.mappers.EventoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;


    public EventoResponseDto save(EventoRequestDto eventoRequestDto,String token, List<String> linkPhoto) {
        String email = jwtService.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        Evento evento = EventoMapper.requestDtoToEntity(eventoRequestDto, usuario,  linkPhoto);

        eventoRepository.save(evento);

        return new EventoResponseDto(
                true,
                "Evento creado exitosamente",
                new EventoResponseDto.Data(
                        evento.getId(),
                        evento.getNombre(),
                        evento.getDescripcion(),
                        evento.getCategoria(),
                        evento.getLugar(),
                        evento.getCiudad(),
                        evento.getDireccion(),
                        evento.getFechaEvento(),
                        evento.getFechaCreacion(),
                        evento.getCapacidad(),
                        evento.getPrecioBase(),
                        evento.getEstado(),
                        evento.getLinkPhoto()
                )
        );
    }

    public List<EventoResponseDto> findAll() {
        return eventoRepository.findAll().
                stream().map(EventoMapper::entityToResponseDto)
                .toList();
    }

    public Optional<EventoResponseDto> findById(Long id) {
        return Optional.of(EventoMapper.entityToResponseDto(eventoRepository.findById(id).get()));
    }

    public EventoResponseDto update(Long id, EventoRequestDto eventoRequestDto) {
        Optional<Evento> findEvent = eventoRepository.findById(id);
        if(findEvent.isEmpty()){
            return new EventoResponseDto(
                    false,
                    "Evento no encontrado",
                    null
            );
        }
        Evento evento = findEvent.get();

        evento.setNombre(eventoRequestDto.nombre());
        evento.setDescripcion(eventoRequestDto.descripcion());
        evento.setCategoria(eventoRequestDto.categoria());
        evento.setLugar(eventoRequestDto.lugar());
        evento.setCiudad(eventoRequestDto.ciudad());
        evento.setDireccion(eventoRequestDto.direccion());
        evento.setFechaEvento(eventoRequestDto.fechaEvento());
        evento.setCapacidad(eventoRequestDto.capacidad());
        evento.setPrecioBase(eventoRequestDto.precioBase());

        Evento eventoActualizado = eventoRepository.save(evento);

        return EventoMapper.entityToResponseDto(eventoActualizado);
    }

    public EventoResponseDto delete(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if(evento.isEmpty()) {
            return new EventoResponseDto(
                    false,
                    "Evento no encontrado",
                    null
            );
        }
        eventoRepository.deleteById(id);
        return new EventoResponseDto(
                true,
                "Evento borrado exitosamente.",
                null
        );
    }

}
