package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "area_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "active_grievances")
    private Integer activeGrievances;

    @Column(name = "resolved_grievances")
    private Integer resolvedGrievances;

    @Column(name = "avg_resolution_time")
    private Float avgResolutionTime;

    @Column(name = "last_updated", insertable = false, updatable = false)
    private OffsetDateTime lastUpdated;
}
