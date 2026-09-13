package com.garden.proyect.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.garden.proyect.services.dtos.evento.EventoRequestDto;
import com.garden.proyect.services.entitiesServices.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestPart("eventData") EventoRequestDto eventoRequestDto,
                                  @RequestPart("imag") List<MultipartFile> img,
                                  @RequestHeader("Authorization") String token ) throws IOException {

        Cloudinary cloudinary = new Cloudinary(Map.of(
                "cloud_name", "db50mfaw4",
                "api_key", "437933836168113",
                "api_secret", "wnEyfrl86M3Ok17rdl8bjVMJmGI"
        ));

        List<String> linsk = new ArrayList<>();
        for(MultipartFile file : img) {
            Map upload = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );
            linsk.add(upload.get("url").toString());
        }

        return new ResponseEntity<>(
                eventoService.save(eventoRequestDto, token,linsk ), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable  Long id) {
        return new ResponseEntity<>(eventoService.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(eventoService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable  Long id, @RequestBody EventoRequestDto eventoRequestDto) {
        return new ResponseEntity<>(eventoService.update(id,eventoRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable  Long id){
        return new ResponseEntity<>(eventoService.delete(id), HttpStatus.OK);
    }

}
