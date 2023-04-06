package org.learning.springpizzeria.SpringPizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.SpringPizzeria.model.FlashMessage;
import org.learning.springpizzeria.SpringPizzeria.model.Ingredient;
import org.learning.springpizzeria.SpringPizzeria.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("ingredients", ingredientService.getIngredients());
        model.addAttribute("ingredient", new Ingredient());
        return "/ingredients/index";
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute(name = "ingredient") Ingredient formIngredient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            model.addAttribute("ingredients", ingredientService.getIngredients());
            return "/ingredients/index";
        }
        ingredientService.create(formIngredient);
        redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS,"Nuovo ingrediente inserito"));
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            Ingredient ingredient = ingredientService.getIngredientById(id);
            model.addAttribute("ingredient", ingredient);
            return "/ingredients/edit";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/ingredients/edit";
        }
        try {
            Ingredient updateIngredient = ingredientService.update(formIngredient, id);
            return "redirect:/ingredients";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            boolean success= ingredientService.deleteByID(id);
            if (success){
                redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS, "Elemento cancellato con successo"));
            } else {
                redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR, "Impossibile cancellare questo elemento"));
            }
        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR, "Pizza con id: " + id + "non trovata"));
        }
        return "redirect:/ingredients";
    }

}
