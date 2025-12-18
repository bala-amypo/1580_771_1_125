package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
}
