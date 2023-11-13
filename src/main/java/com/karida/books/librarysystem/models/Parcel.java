package com.karida.books.librarysystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_parcel;
    @Column(name = "company_name")
    private String company_name;
    @Column(name = "manager")
    private String manager;
    @Column(name = "address_p")
    private String address_p;
    @Column(name = "email")
    private String email;
    @Column(name = "status_c")
    private Integer status_c;

    public Parcel(Integer id) {
        super();
        id_parcel = id;
    }
}
