package com.marioorozco.repository;

import com.marioorozco.model.ConsultExam;
import com.marioorozco.model.ConsultExamPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IConsultExamRepo extends IGenericRepo<ConsultExam, ConsultExamPK> {

    @Modifying // esta anotación es necesaria para indicar que el metodo modifica la base de datos
    @Query(value = "INSERT INTO consult_exam(id_consult, id_exam) VALUES(:idConsult, :idExam)", nativeQuery = true)
    Integer saveExam(@Param("idConsult") Integer idConsult, @Param("idExam") Integer idExam);
}
