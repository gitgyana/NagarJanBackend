package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Grievances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrievancesRepository extends JpaRepository<Grievances, Long> {
}