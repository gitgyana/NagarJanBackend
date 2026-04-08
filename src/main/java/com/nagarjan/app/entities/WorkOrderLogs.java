package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "work_order_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrders workOrder;

    @Column(name = "action", length = 255)
    private String action;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Users updatedBy;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private OffsetDateTime timestamp;
}