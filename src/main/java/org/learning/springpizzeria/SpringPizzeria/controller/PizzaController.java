package org.learning.springpizzeria.SpringPizzeria.controller;

import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name="q") Optional<String> searchcontent){
        List<Pizza> pizzas;
        if (searchcontent.isPresent()){
        pizzas = pizzaRepository.findByNameContainingIgnoreCase(searchcontent.get());
        model.addAttribute("searchcontent", searchcontent);
        } else {
            pizzas = pizzaRepository.findAll();
        }
        model.addAttribute("pizzas", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()){
            model.addAttribute("pizza", pizza.get());
            return "/pizzas/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
