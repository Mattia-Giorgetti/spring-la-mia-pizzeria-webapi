package org.learning.springpizzeria.SpringPizzeria.repository;

import org.learning.springpizzeria.SpringPizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
