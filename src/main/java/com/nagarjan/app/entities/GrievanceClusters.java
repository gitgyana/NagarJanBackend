package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "grievance_clusters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrievanceClusters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cluster_id")
    private Long clusterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_grievance_id", nullable = false)
    private Grievances primaryGrievance;

    @Column(name = "cluster_size", nullable = false)
    private Integer clusterSize = 1;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}