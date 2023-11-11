package com.karida.books.librarysystem.models;
import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rol;
    @Column(name = "rol_name")
    private String rol_name;
    @Column(name = "rol_number")
    private Integer rol_number;
    @Column(name = "status_c")
    private Integer status_c;
    public Rol(Integer id) {
        super();
        id_rol = id;
    }
}
