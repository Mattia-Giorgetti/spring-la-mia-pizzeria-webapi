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
        if (formPizza.getImage().isEmpty()){
            newPizza.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/681px-Placeholder_view_vector.svg.png");
        } else {
            newPizza.setImage(formPizza.getImage());
        }

        return pizzaRepository.save(newPizza);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws RuntimeException {
        Pizza updatedBook = getPizzaById(id);
        updatedBook.setName(formPizza.getName());
        updatedBook.setDescription(formPizza.getDescription());
        updatedBook.setPrice(formPizza.getPrice());
        updatedBook.setImage(formPizza.getImage());
        return pizzaRepository.save(updatedBook);
    }

    public boolean deleteByID(Integer id) throws RuntimeException{
        pizzaRepository.findById(id).orElseThrow(() -> new RuntimeException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
