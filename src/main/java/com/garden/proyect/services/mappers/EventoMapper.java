package com.garden.proyect.services.mappers;

import com.garden.proyect.entities.Evento;
import com.garden.proyect.entities.Usuario;
import com.garden.proyect.services.dtos.evento.EventoRequestDto;
import com.garden.proyect.services.dtos.evento.EventoResponseDto;

import java.util.List;

public class EventoMapper {

    public static Evento requestDtoToEntity(EventoRequestDto eventoRequestDto, Usuario usuario, List<String> linkPhoto) {
        Evento evento = new Evento();
        evento.setNombre(eventoRequestDto.nombre());
        evento.setDescripcion(eventoRequestDto.descripcion());
        evento.setCategoria(eventoRequestDto.categoria());
        evento.setLugar(eventoRequestDto.lugar());
        evento.setCiudad(eventoRequestDto.ciudad());
        evento.setDireccion(eventoRequestDto.direccion());
        evento.setCapacidad(eventoRequestDto.capacidad());
        evento.setPrecioBase(eventoRequestDto.precioBase());
        evento.setEstado("ACTIVO");
        evento.setOrganizador(usuario);
        evento.setLinkPhoto(linkPhoto);
        return evento;
    }

    public static EventoResponseDto entityToResponseDto(Evento evento) {
        return new EventoResponseDto(
                true,
                "Evento encontrado",
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

}
