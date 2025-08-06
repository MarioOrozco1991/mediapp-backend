package com.marioorozco.controller;

import com.marioorozco.dto.PatientDTO;
import com.marioorozco.model.Patient;
import com.marioorozco.service.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService service;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() throws Exception{

        List<PatientDTO> list = service.findAll().stream()
                .map(this::convertToDto).toList();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id) throws Exception{
        PatientDTO obj = convertToDto(service.findById(id));

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDTO dto) throws Exception{ // @Valid valida el dto, para que venga con los datos correctos definidos en la clase dto.
        Patient obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        //localhost:8080/patients/6
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @RequestBody PatientDTO dto, @PathVariable("id") Integer id) throws Exception{
        Patient obj = service.update(convertToEntity(dto), id);

        return ResponseEntity.ok().body(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/hateoas/{id}")
//    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id) throws Exception {
//        Patient obj = service.findById(id);
//        EntityModel<PatientDTO> resource = EntityModel.of(convertToDto(obj));
//
//        //generar link informativo
//        //localhost:8080/patients/1
//        WebMvcLinkBuilder link1 = linkTo(methodOn(PatientController.class).findById(obj.getIdPatient()));
//        WebMvcLinkBuilder link2 = linkTo(methodOn(PatientController.class).findAll());
//
//        resource.add(link1.withRel("patient-self-info"));
//        resource.add(link2.withRel("all-patients"));
//
//        return resource;
//
//    }

    private Patient convertToEntity(PatientDTO dto) {
        return modelMapper.map(dto, Patient.class);
    }

    private PatientDTO convertToDto(Patient entity) {
        return modelMapper.map(entity, PatientDTO.class);
    }
}
