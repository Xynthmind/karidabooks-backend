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
@Table(name = "ordersdetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_order_details;
    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order id_order;
    @OneToOne
    @JoinColumn(name = "id_book")
    private Book id_book;
    @Column(name = "amount")
    private double amount;
    @Column(name = "total_objects")
    private Integer total_objects;
}
