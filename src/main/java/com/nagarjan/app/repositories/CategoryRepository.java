package com.nagarjan.app.repositories;

import com.nagarjan.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}