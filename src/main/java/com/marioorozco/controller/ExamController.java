package com.marioorozco.controller;

import com.marioorozco.dto.ExamDTO;
import com.marioorozco.model.Exam;
import com.marioorozco.service.IExamService;
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
@RequestMapping("/exams")
public class ExamController {

    private final IExamService service;
    private final ModelMapper modelMapper;

    public ExamController(IExamService service, @Qualifier("defaultMapper") ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> findAll() throws Exception{

        List<ExamDTO> list = service.findAll().stream()
                .map(this::convertToDto).toList(); //e -> convertToDto(e)

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id) throws Exception{
        ExamDTO obj = convertToDto(service.findById(id));

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ExamDTO dto) throws Exception{
        Exam obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();
        //localhost:8080/exams/6
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@Valid @RequestBody ExamDTO dto, @PathVariable("id") Integer id) throws Exception{
        Exam obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok().body(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
        Exam obj = service.findById(id);
        EntityModel<ExamDTO> resource = EntityModel.of(convertToDto(obj));

        WebMvcLinkBuilder link1 = linkTo(methodOn(ExamController.class).findById(obj.getIdExam()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(ExamController.class).findAll());

        resource.add(link1.withRel("exam-self-info"));
        resource.add(link2.withRel("all-exams"));

        return resource;

    }

    private Exam convertToEntity(ExamDTO dto) {
        return modelMapper.map(dto, Exam.class);
    }

    private ExamDTO convertToDto(Exam entity) {
        return modelMapper.map(entity, ExamDTO.class);
    }


}
