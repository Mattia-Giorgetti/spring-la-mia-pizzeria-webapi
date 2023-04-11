package org.learning.springpizzeria.SpringPizzeria.api;

import jakarta.validation.Valid;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    @Autowired
    PizzaService pizzaService;

//    TUTTE LE PIZZE + RICERCA
    @GetMapping
    public List<Pizza> pizzaList(@RequestParam(name = "q")Optional<String> queryString){
        if (queryString.isPresent()){
            return pizzaService.getFilteredPizzas(queryString.get());
        }
        return pizzaService.getAllPizzas();
    }

//    SINGOLA PIZZA

    @GetMapping("/{id}")
    public Pizza pizzaById(@PathVariable Integer id){
        try {
            return pizzaService.getPizzaById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


//    CREATE PIZZA

    @PostMapping
    public Pizza createPizza(@Valid @RequestBody Pizza pizza ){
        return pizzaService.createNewPizza(pizza);
    }
//    UPDATE PIZZA

    @PutMapping("/{id}")
    public Pizza updatePizza(@PathVariable Integer id, @Valid @RequestBody Pizza requestPizza){
        try {
            return pizzaService.updatePizza(requestPizza,id);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

//    DELETE PIZZA

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Integer id){
        try {
            boolean success = pizzaService.deleteByID(id);
            if (!success){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile eliminare la pizza a causa di offerte attive");
            }
        } catch ( NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
