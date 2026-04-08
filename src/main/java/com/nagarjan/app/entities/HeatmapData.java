package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "heatmap_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeatmapData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "incident_count")
    private Integer incidentCount;

    @Column(name = "density_score", precision = 6, scale = 2)
    private BigDecimal densityScore;
}