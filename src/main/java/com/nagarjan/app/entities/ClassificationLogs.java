package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "classification_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @Column(name = "raw_output", columnDefinition = "JSON")
    private String rawOutput;

    @Column(name = "model_latency_ms")
    private Integer modelLatencyMs;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}