package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.ClassificationLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationLogsRepository extends JpaRepository<ClassificationLogs, Long> {
}