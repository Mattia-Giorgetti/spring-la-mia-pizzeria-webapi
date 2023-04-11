package org.learning.springpizzeria.SpringPizzeria.service;

import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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
        newPizza.setIngredients(formPizza.getIngredients());
        if (formPizza.getImage().isEmpty()){
            newPizza.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/681px-Placeholder_view_vector.svg.png");
        } else {
            newPizza.setImage(formPizza.getImage());
        }

        return pizzaRepository.save(newPizza);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws RuntimeException {
        Pizza updatedPizza = getPizzaById(id);
        updatedPizza.setName(formPizza.getName());
        updatedPizza.setDescription(formPizza.getDescription());
        updatedPizza.setPrice(formPizza.getPrice());
        updatedPizza.setImage(formPizza.getImage());
        updatedPizza.setIngredients(formPizza.getIngredients());
        return pizzaRepository.save(updatedPizza);
    }

    public boolean deleteByID(Integer id) throws NoSuchElementException {
        pizzaRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
