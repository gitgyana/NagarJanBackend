package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}