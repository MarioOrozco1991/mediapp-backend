package com.marioorozco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medic {

    @ManyToOne//FK
    @JoinColumn(name = "id_specialty", nullable = false, foreignKey = @ForeignKey(name = "FK_MEDIC_SPECIALTY"))//esto es
    // para definir el nombre de la columna de la llave foranea en este caso con el nombre "id_specialty" y se le coloca
    // el nombre de la llave foranea para que no se genere un nombre por defecto en este caso "FK_MEDIC_SPECIALTY"
    private Specialty specialty;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMedic;

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 12)
    private String cmp;

    @Column(length = 250)
    private String photoUrl;

}
