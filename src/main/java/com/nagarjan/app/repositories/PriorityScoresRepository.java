package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.PriorityScores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityScoresRepository extends JpaRepository<PriorityScores, Long> {
}