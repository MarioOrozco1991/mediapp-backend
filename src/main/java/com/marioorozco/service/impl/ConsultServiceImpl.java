    package com.marioorozco.service.impl;

import com.marioorozco.model.Consult;
import com.marioorozco.model.Exam;
import com.marioorozco.repository.IConsultExamRepo;
import com.marioorozco.repository.IConsultRepo;
import com.marioorozco.repository.IGenericRepo;
import com.marioorozco.service.IConsultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

        private final IConsultRepo consultRepo;
        private final IConsultExamRepo ceRepo;

        @Override
        protected IGenericRepo<Consult, Integer> getRepo() {
            return consultRepo;
        }

        @Transactional /* esta anotacion es para que se maneje la transaccion de manera correcta, para que este bloque de codigo
        se ejecute como una sola transaccion, si falla algo se revierte y no se guarden datos inconsistentes en la bd,
        solo se utiliza en donde modificamos datos, en este caso en el metodo saveTransactional, ya que guardamos el maestro y detalle*/
        @Override
        public Consult saveTransactional(Consult consult, List<Exam> exams) {
            consultRepo.save(consult); //GUARDANDO EL MAESTRO DETALLE
            exams.forEach(ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

            return consult;
        }
    }
