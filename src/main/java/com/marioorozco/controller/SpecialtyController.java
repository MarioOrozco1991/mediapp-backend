package com.marioorozco.controller;

import com.marioorozco.dto.SpecialtyDTO;
import com.marioorozco.model.Specialty;
import com.marioorozco.service.ISpecialtyService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    private final ISpecialtyService service;

    private final ModelMapper modelMapper;

    public SpecialtyController(ISpecialtyService service, @Qualifier("defaultMapper") ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll() throws Exception{

        List<SpecialtyDTO> list = service.findAll().stream()
                .map(this::convertToDto).toList(); //e -> convertToDto(e)

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpecialtyDTO obj = convertToDto(service.findById(id));

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SpecialtyDTO dto) throws Exception{
        Specialty obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> update(@Valid @RequestBody SpecialtyDTO dto, @PathVariable("id") Integer id) throws Exception{
        Specialty obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok().body(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
        Specialty obj = service.findById(id);
        EntityModel<SpecialtyDTO> resource = EntityModel.of(convertToDto(obj));

        //generar link informativo
        //localhost:8080/specialtys/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(SpecialtyController.class).findById(obj.getIdSpecialty()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(SpecialtyController.class).findAll());

        resource.add(link1.withRel("specialty-self-info"));
        resource.add(link2.withRel("all-specialtys"));

        return resource;

    }

    private Specialty convertToEntity(SpecialtyDTO dto) {
        return modelMapper.map(dto, Specialty.class);
    }

    private SpecialtyDTO convertToDto(Specialty entity) {
        return modelMapper.map(entity, SpecialtyDTO.class);
    }


}