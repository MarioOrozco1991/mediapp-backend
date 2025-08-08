package com.marioorozco.controller;

import com.marioorozco.dto.MedicDTO;
import com.marioorozco.model.Medic;
import com.marioorozco.service.IMedicService;
import com.marioorozco.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {

    private final IMedicService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll() throws Exception {

        List<MedicDTO> list = mapperUtil.mapList(service.findAll(), MedicDTO.class, "medicMapper");
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id) throws Exception {
        MedicDTO obj = mapperUtil.map(service.findById(id), MedicDTO.class, "medicMapper");

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MedicDTO dto) throws Exception {
        Medic obj = service.save(mapperUtil.map(dto, Medic.class, "medicMapper"));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@Valid @RequestBody MedicDTO dto, @PathVariable("id") Integer id) throws Exception {
        Medic obj = service.update(mapperUtil.map(dto, Medic.class, "medicMapper"), id);

        return ResponseEntity.ok().body(mapperUtil.map(obj, MedicDTO.class, "medicMapper"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
        Medic obj = service.findById(id);
        EntityModel<MedicDTO> resource = EntityModel.of(mapperUtil.map(obj, MedicDTO.class, "medicMapper"));

        //generar link informativo
        //localhost:8080/medics/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(MedicController.class).findById(obj.getIdMedic()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(MedicController.class).findAll());

        resource.add(link1.withRel("medic-self-info"));
        resource.add(link2.withRel("all-medics"));

        return resource;

    }

}