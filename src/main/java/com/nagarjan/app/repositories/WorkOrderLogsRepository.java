package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.WorkOrderLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderLogsRepository extends JpaRepository<WorkOrderLogs, Long> {
}