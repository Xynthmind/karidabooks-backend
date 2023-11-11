package com.karida.books.librarysystem.models;
import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol id_rol;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "middle_name")
    private String middle_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "phone_number")
    private Long phone_number;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status_c")
    private Integer status_c;

    public User(Integer id) {
        super();
        id_user = id;
    }
}
