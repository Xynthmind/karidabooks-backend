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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_order;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card id_card;
    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address id_address;
    @ManyToOne
    @JoinColumn(name = "id_parcel")
    private Parcel id_parcel;
    @Column(name = "order_date")
    private LocalDate order_date;
    @Column(name = "shipping_date")
    private LocalDate shipping_date;
    @Column(name = "arrive_date")
    private LocalDate arrive_date;
    @Column(name = "total")
    private double total;
    @Column(name = "order_status")
    private String order_status;
    @Column(name = "status_c")
    private Integer status_c;

}
