package com.marioorozco.service.impl;

import com.marioorozco.model.Medic;
import com.marioorozco.repository.IGenericRepo;
import com.marioorozco.repository.IMedicRepo;
import com.marioorozco.service.IMedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDImpl<Medic, Integer> implements IMedicService {

    @Autowired
    private IMedicRepo repo;


    @Override
    protected IGenericRepo<Medic, Integer> getRepo() {
        return repo;
    }
}
