package com.marioorozco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicDTO {

    private Integer idMedic;
    private Integer idSpecialty;
    private String primaryName;
    private String surname;
    private String cmpMedic;
    private String photo;
}
