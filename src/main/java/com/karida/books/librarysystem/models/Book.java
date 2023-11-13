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
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_book;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category id_category;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "photo")
    private String photo;
    @Column(name = "editorial")
    private String editorial;
    @Column(name = "description")
    private String description;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "price")
    private double price;
    @Column(name = "discount")
    private double discount;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "status_c")
    private Integer status_c;

    public Book(Integer id) {
        super();
        id_book = id;
    }
}
