package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "cluster_mapping",
        uniqueConstraints = {
                @UniqueConstraint(name = "uniq_cluster", columnNames = {"cluster_id", "grievance_id"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id", nullable = false)
    private GrievanceClusters cluster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;
}