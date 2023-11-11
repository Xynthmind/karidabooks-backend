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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_category;
    @Column(name = "category_name")
    private String category_name;
    @Column(name = "status_c")
    private Integer status_c;

    public Category(Integer id) {
        super();
        id_category = id;
    }
}
