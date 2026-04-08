package com.nagarjan.app.entities;

import com.nagarjan.app.entities.enums.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "work_orders",
        indexes = {
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_team", columnList = "assigned_team_id")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_id")
    private Long workOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievances grievance;

    @Column(name = "priority_score", precision = 6, scale = 2)
    private BigDecimal priorityScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkOrderStatus status = WorkOrderStatus.ASSIGNED;

    @Column(name = "assigned_at", insertable = false, updatable = false)
    private OffsetDateTime assignedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;
}