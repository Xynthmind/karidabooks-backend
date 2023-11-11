package com.karida.books.librarysystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_card;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
    @Column(name = "card_owner")
    private String card_owner;
    @Column(name = "card_number")
    private Long card_number;
    @Column(name = "expiry_date")
    private LocalDate expiry_date;
    @Column(name = "cvv")
    private Integer cvv;
    @Column(name = "country")
    private String country;
    @Column(name = "street")
    private String street;
    @Column(name = "zc")
    private String zc;
}
