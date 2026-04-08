package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Grievances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrievancesRepository extends JpaRepository<Grievances, Long> {
    Optional<Grievances> findByRaw_RawId(Long rawRawId);
}