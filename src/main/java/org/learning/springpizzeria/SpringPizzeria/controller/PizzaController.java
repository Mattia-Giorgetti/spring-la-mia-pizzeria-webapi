package org.learning.springpizzeria.SpringPizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.PizzaRepository;
import org.learning.springpizzeria.SpringPizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String index(Model model, @RequestParam(name="q") Optional<String> searchcontent){
        List<Pizza> pizzas;
        if (searchcontent.isPresent()){
        pizzas = pizzaService.getFilteredPizzas(searchcontent.get());
        model.addAttribute("searchcontent", searchcontent.get());
        } else {
            pizzas = pizzaService.getAllPizzas();
        }
        model.addAttribute("pizzas", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
       try {
           Pizza pizza = pizzaService.getPizzaById(id);
           model.addAttribute("pizza", pizza);
           return "/pizzas/show";
       } catch (RuntimeException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/pizzas/create";
        }
        pizzaService.createNewPizza(formPizza);
        return "redirect:/pizzas";
    }
}
