package com.nagarjan.app.entities;

import com.nagarjan.app.entities.enums.GrievanceSource;
import com.nagarjan.app.entities.enums.GrievanceStatusRaw;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "grievances_raw",
        indexes = {
                @Index(name = "idx_source_time", columnList = "source, timestamp")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrievancesRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raw_id")
    private Long rawId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private GrievanceSource source;

    @Column(name = "external_reference_id", length = 100)
    private String externalReferenceId;

    @Column(name = "raw_content", columnDefinition = "TEXT")
    private String rawContent;

    @Column(name = "media_text_content", columnDefinition = "LONGTEXT")
    private String mediaTextContent;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GrievanceStatusRaw status = GrievanceStatusRaw.INGESTED;
}