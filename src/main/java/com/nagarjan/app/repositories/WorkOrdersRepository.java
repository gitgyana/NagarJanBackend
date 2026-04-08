package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.WorkOrders;
import com.nagarjan.app.entities.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrdersRepository extends JpaRepository<WorkOrders, Long> {
    long countByStatus(WorkOrderStatus status);
}