package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "priority_scores",
        indexes = {
                @Index(name = "idx_priority", columnList = "final_score DESC")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriorityScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @Column(name = "severity_score", precision = 5, scale = 2)
    private BigDecimal severityScore;

    @Column(name = "recurrence_score", precision = 5, scale = 2)
    private BigDecimal recurrenceScore;

    @Column(name = "age_score", precision = 5, scale = 2)
    private BigDecimal ageScore;

    @Column(name = "density_score", precision = 5, scale = 2)
    private BigDecimal densityScore;

    @Column(name = "final_score", precision = 6, scale = 2)
    private BigDecimal finalScore;

    @Column(name = "computed_at", insertable = false, updatable = false)
    private OffsetDateTime computedAt;
}