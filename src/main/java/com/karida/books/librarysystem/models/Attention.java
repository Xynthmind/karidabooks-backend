package com.karida.books.librarysystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "attentions")
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_attention;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
    @Column(name = "descript")
    private String description;
    @Column(name = "req_type")
    private Integer req_type;
    @Column(name = "att_date")
    private LocalDate att_date;
    @Column(name = "att_time")
    private LocalTime att_time;
    @Column(name = "status_c")
    private Integer status_c;


}
