package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.GrievanceClassification;
import com.nagarjan.app.entities.enums.ClassificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GrievanceClassificationRepository extends JpaRepository<GrievanceClassification, Long> {
    long countByStatus(ClassificationStatus status);

    @Query(value = """
                SELECT
                    c.name AS category,
                    COUNT(gc.id) AS total_count,
                    AVG(gc.confidence_score) AS avg_confidence,
                    SUM(CASE WHEN gc.status = 'AUTO' THEN 1 ELSE 0 END) AS auto_count,
                    SUM(CASE WHEN gc.status = 'MANUAL_REVIEW' THEN 1 ELSE 0 END) AS manual_count
                FROM grievance_classification gc
                JOIN Categories c ON gc.category_id = c.category_id
                GROUP BY c.name
                ORDER BY total_count DESC
            """, nativeQuery = true)
    List<Map<String, Object>> getDashboardClassificationData();
}