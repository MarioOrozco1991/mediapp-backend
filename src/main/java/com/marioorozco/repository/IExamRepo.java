package com.marioorozco.repository;

import com.marioorozco.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamRepo extends IGenericRepo<Exam, Integer> {
}
