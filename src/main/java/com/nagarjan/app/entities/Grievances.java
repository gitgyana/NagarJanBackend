package com.nagarjan.app.entities;

import com.nagarjan.app.entities.enums.GrievanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "grievances",
        indexes = {
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_location", columnList = "location_id")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grievances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grievance_id")
    private Long grievanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_id")
    private GrievancesRaw raw;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private Users citizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GrievanceStatus status = GrievanceStatus.OPEN;
}