package org.learning.springpizzeria.SpringPizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.SpringPizzeria.model.FlashMessage;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.PizzaRepository;
import org.learning.springpizzeria.SpringPizzeria.service.IngredientService;
import org.learning.springpizzeria.SpringPizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

//    @Autowired
//    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

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
        model.addAttribute("ingredientsList", ingredientService.getIngredients());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        boolean success;
        if (bindingResult.hasErrors()){
            model.addAttribute("ingredientsList", ingredientService.getIngredients());
            return "/pizzas/create";
        }
        pizzaService.createNewPizza(formPizza);
        success = true;
        if (success){
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS, "Elemento creato con successo"));
        }

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("pizza", pizza);
            model.addAttribute("ingredientsList", ingredientService.getIngredients());
            return "/pizzas/edit";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        boolean success;
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientsList", ingredientService.getIngredients());
            return "/pizzas/edit";
        }
        try {
            Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);
            success = true;
            if (success){
                redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS, "Elemento aggiornato con successo"));
            }
            return "redirect:/pizzas/" + updatedPizza.getId();
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            boolean success= pizzaService.deleteByID(id);
            if (success){
                redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS, "Elemento cancellato con successo"));
            } else {
                redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR, "Impossibile cancellare questo elemento"));
            }
        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR, "Pizza con id: " + id + "non trovata"));
        }
        return "redirect:/pizzas";
    }


}
