package com.nagarjan.app.entities;

import com.nagarjan.app.entities.enums.DuplicateMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "grievance_duplicates",
        indexes = {
                @Index(name = "idx_dup", columnList = "grievance_id, duplicate_of")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrievanceDuplicates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duplicate_of", nullable = false)
    private Grievances duplicateOf;

    @Column(name = "similarity_score", precision = 5, scale = 2)
    private BigDecimal similarityScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private DuplicateMethod method;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}