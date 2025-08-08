package com.marioorozco.service.impl;

import com.marioorozco.model.Specialty;
import com.marioorozco.repository.IGenericRepo;
import com.marioorozco.repository.ISpecialtyRepo;
import com.marioorozco.service.ISpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Integer> implements ISpecialtyService {

    @Autowired
    private ISpecialtyRepo repo;


    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }
}
