package com.eleks.academy.pharmagator.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

}