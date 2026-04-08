package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}