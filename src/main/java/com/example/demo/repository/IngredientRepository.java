package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
}
