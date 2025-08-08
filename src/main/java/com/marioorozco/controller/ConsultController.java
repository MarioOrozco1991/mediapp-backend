package com.marioorozco.controller;

import com.marioorozco.dto.ConsultDTO;
import com.marioorozco.dto.ConsultListExamDTO;
import com.marioorozco.model.Consult;
import com.marioorozco.model.Exam;
import com.marioorozco.service.IConsultService;
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
@RequestMapping("/consults")
public class ConsultController {

    private final IConsultService service;
    private final ModelMapper modelMapper;

    public ConsultController(IConsultService service, @Qualifier("defaultMapper") ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll() throws Exception{

        List<ConsultDTO> list = service.findAll().stream()
                .map(this::convertToDto).toList(); //e -> convertToDto(e)

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) throws Exception{
        ConsultDTO obj = convertToDto(service.findById(id));

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultListExamDTO dto) throws Exception{
        Consult obj1 = convertToEntity(dto.getConsult());
        List<Exam> list = dto.getLstExam().stream().map(ex -> modelMapper.map(ex, Exam.class)).toList();

        Consult obj = service.saveTransactional(obj1, list);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
        //localhost:8080/consults/6
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> update(@Valid @RequestBody ConsultDTO dto, @PathVariable("id") Integer id) throws Exception{
        Consult obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok().body(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
        Consult obj = service.findById(id);
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(obj));

        WebMvcLinkBuilder link1 = linkTo(methodOn(ConsultController.class).findById(obj.getIdConsult()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(ConsultController.class).findAll());

        resource.add(link1.withRel("consult-self-info"));
        resource.add(link2.withRel("all-consults"));

        return resource;

    }

    private Consult convertToEntity(ConsultDTO dto) {
        return modelMapper.map(dto, Consult.class);
    }

    private ConsultDTO convertToDto(Consult entity) {
        return modelMapper.map(entity, ConsultDTO.class);
    }


}
