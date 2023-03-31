package org.learning.springpizzeria.SpringPizzeria.service;

import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzas(){
        return pizzaRepository.findAll();
    }

    public List<Pizza> getFilteredPizzas(String searchcontent){
        return pizzaRepository.findByNameContainingIgnoreCase(searchcontent);
    }

    public Pizza getPizzaById(Integer id){
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()){
            return pizza.get();
        } else throw new RuntimeException();
    }

    public Pizza createNewPizza(Pizza formPizza){
        Pizza newPizza = new Pizza();
        newPizza.setName(formPizza.getName());
        newPizza.setDescription(formPizza.getDescription());
        newPizza.setPrice(new BigDecimal(String.valueOf(formPizza.getPrice())));
        newPizza.setImage(formPizza.getImage());
        return pizzaRepository.save(newPizza);
    }
}
