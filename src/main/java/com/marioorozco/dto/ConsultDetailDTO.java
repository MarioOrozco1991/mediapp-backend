package com.marioorozco.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultDetailDTO {

    private Integer idDetail;

    @JsonBackReference // Para evitar el bucle infinito de serialización, y tenga referencia a ConsultDTO
    private ConsultDTO consult;

    @NotNull
    private String diagnosis;

    @NotNull
    private String treatment;
}
