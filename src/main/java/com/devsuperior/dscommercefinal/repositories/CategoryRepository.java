package com.devsuperior.dscommercefinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscommercefinal.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
