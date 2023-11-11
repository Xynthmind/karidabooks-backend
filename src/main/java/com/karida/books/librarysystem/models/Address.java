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
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_address;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
    @Column(name = "country")
    private String country;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private String house_number;
    @Column(name = "zc")
    private String zc;
    @Column(name = "delivery_instructions")
    private String delivery_instructions;
}
