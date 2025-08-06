package com.marioorozco.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Integer idPatient;


    @NotNull  //Para validar que viaje la propiedad idPaciente en el body
    @NotEmpty //Para validar que el valor de la propiedad no sea vacia ""
    @NotBlank //Para validar que e valor de la propiedad no sea un espacio en blanco " "
    @Size(min = 3, max = 70, message = "firstname.size")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 70, message = "lastname.size")
    private String lastName;

    @NotNull
    private String dni;

    @NotNull
    private String address;

    @NotNull
    @Pattern(regexp = "[0-9]+") // para que solo acepte numeros
    private String phone;

    @NotNull
    @Email // para validar que envie un email.
    private String email;
}
