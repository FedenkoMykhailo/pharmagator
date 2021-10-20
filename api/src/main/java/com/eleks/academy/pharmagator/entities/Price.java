package com.eleks.academy.pharmagator.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@Table(name = "prices")
@IdClass(PriceId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pharmacyId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long medicineId;
    private BigDecimal price;
    private String externalId;
    private Instant updatedAt;
}