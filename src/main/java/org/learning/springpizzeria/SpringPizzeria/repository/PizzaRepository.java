package org.learning.springpizzeria.SpringPizzeria.repository;

import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Integer> {
}
