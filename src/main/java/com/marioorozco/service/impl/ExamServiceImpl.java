package com.marioorozco.service.impl;

import com.marioorozco.model.Exam;
import com.marioorozco.model.Patient;
import com.marioorozco.repository.IExamRepo;
import com.marioorozco.repository.IGenericRepo;
import com.marioorozco.repository.IPatientRepo;
import com.marioorozco.service.IExamService;
import com.marioorozco.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Integer> implements IExamService {

    @Autowired
    private IExamRepo repo;


    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }
}
