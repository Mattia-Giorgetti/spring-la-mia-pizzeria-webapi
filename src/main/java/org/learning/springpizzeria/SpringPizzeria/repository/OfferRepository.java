package org.learning.springpizzeria.SpringPizzeria.repository;

import org.learning.springpizzeria.SpringPizzeria.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
