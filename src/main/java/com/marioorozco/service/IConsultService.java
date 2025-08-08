package com.marioorozco.service;

import com.marioorozco.model.Consult;
import com.marioorozco.model.Exam;

import java.util.List;

public interface IConsultService extends ICRUD<Consult, Integer> {

    Consult saveTransactional(Consult consult, List<Exam> exams);

}
