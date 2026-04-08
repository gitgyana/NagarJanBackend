package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
        SELECT c.name, COUNT(gc.id)
        FROM GrievanceClassification gc
        JOIN gc.category c
        GROUP BY c.name
        ORDER BY COUNT(gc.id) DESC
    """)
    List<Object[]> getCategoriesByCount();
}