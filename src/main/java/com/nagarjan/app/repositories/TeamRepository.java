package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}