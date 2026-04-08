package com.nagarjan.app.entities.enums;

import com.nagarjan.app.entities.Grievances;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "priority_factors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriorityFactors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @Enumerated(EnumType.STRING)
    @Column(name = "factor_name", nullable = false)
    private PriorityFactorName factorName;

    @Column(name = "factor_value", precision = 5, scale = 2)
    private BigDecimal factorValue;

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;
}