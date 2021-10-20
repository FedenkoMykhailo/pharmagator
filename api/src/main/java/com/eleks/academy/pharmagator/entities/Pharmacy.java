package com.eleks.academy.pharmagator.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String medicineLinkTemplate;
}