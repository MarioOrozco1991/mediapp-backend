package com.marioorozco.controller;

import com.marioorozco.dto.PatientDTO;
import com.marioorozco.model.Patient;
import com.marioorozco.service.IPatientService;
import com.marioorozco.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    //@Autowired
    private final IPatientService service;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() throws Exception{

        List<PatientDTO> list = mapperUtil.mapList(service.findAll(), PatientDTO.class);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id) throws Exception{
        PatientDTO obj = mapperUtil.map(service.findById(id), PatientDTO.class);

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDTO dto) throws Exception{
        Patient obj = service.save(mapperUtil.map(dto, Patient.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @RequestBody PatientDTO dto, @PathVariable("id") Integer id) throws Exception{
        Patient obj = service.update(mapperUtil.map(dto, Patient.class), id);

        return ResponseEntity.ok().body(mapperUtil.map(obj, PatientDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
        Patient obj = service.findById(id);
        EntityModel<PatientDTO> resource = EntityModel.of(mapperUtil.map(obj, PatientDTO.class));

        WebMvcLinkBuilder link1 = linkTo(methodOn(PatientController.class).findById(obj.getIdPatient()));
        WebMvcLinkBuilder link2 = linkTo(methodOn(PatientController.class).findAll());

        resource.add(link1.withRel("patient-self-info"));
        resource.add(link2.withRel("all-patients"));

        return resource;

    }

}
