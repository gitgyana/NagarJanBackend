package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.enums.PriorityFactors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityFactorsRepository extends JpaRepository<PriorityFactors, Long> {
}