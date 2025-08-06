package com.marioorozco.service.impl;

import com.marioorozco.model.Patient;
import com.marioorozco.repository.IGenericRepo;
import com.marioorozco.repository.IPatientRepo;
import com.marioorozco.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    @Autowired
    private IPatientRepo repo;


    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }
}
