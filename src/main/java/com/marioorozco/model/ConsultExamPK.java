package com.marioorozco.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ConsultExamPK {

    //Se creo esta clase para poder usarla como clave primaria compuesta
    //este tipo de clave primaria se usa cuando una entidad tiene una relación muchos a muchos
    //en este caso Consult y Exam tienen una relación muchos a muchos
    @ManyToOne
    @JoinColumn(name = "id_consult", foreignKey = @ForeignKey(name = "FK_CONSULT_EXAM_C"))
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", foreignKey = @ForeignKey(name = "FK_CONSULT_EXAM_E"))
    private Exam exam;
}
