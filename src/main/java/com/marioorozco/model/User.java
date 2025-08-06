package com.marioorozco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user_data") // Especifica el nombre de la tabla en la base de datos
public class User {

    @Id
    @EqualsAndHashCode.Include
    private Integer idUser;

    @Column(nullable = false, length = 60, unique = true)//el unique=true se coloca para evitar que se repita el username en bd
    // y se genera una exepcion en bd si se intenta guarda otro registro con el mismo nombre
    private String username;

    @Column(nullable = false, length = 60)//Se deja con 60 caracteres para poder usar bcrypt, y asi hacer un hash de la contraseña
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER) // Relación muchos a muchos con Role
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"),
                inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "idRole")
    )
    private List<Role> roles; // Lista de roles asociados al usuario

}
