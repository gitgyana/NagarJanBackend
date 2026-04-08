package com.nagarjan.app.entities;

import com.nagarjan.app.entities.enums.ClassificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "grievance_classification",
        indexes = {
                @Index(name = "idx_confidence", columnList = "confidence_score")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrievanceClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "confidence_score", precision = 5, scale = 2)
    private BigDecimal confidenceScore;

    @Column(name = "model_version", length = 50)
    private String modelVersion;

    @Column(name = "classified_at", insertable = false, updatable = false)
    private OffsetDateTime classifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ClassificationStatus status;
}
