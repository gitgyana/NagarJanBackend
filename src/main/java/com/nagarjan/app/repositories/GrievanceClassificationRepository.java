package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.GrievanceClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrievanceClassificationRepository extends JpaRepository<GrievanceClassification, Long> {
}