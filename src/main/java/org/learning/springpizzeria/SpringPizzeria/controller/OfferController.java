package org.learning.springpizzeria.SpringPizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.SpringPizzeria.model.FlashMessage;
import org.learning.springpizzeria.SpringPizzeria.model.Offer;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.service.OfferService;
import org.learning.springpizzeria.SpringPizzeria.service.PizzaService;
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
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Integer id, Model model){
        Offer offer = new Offer();

            try {
                Pizza pizza = pizzaService.getPizzaById(id);
                offer.setPizza(pizza);
            } catch (RuntimeException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("offer", offer);
        return "/offers/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/offers/create";
        }
        Offer newOffer = offerService.createOffer(formOffer);
        return "redirect:/pizzas/" + Integer.toString(newOffer.getPizza().getId());
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            Offer offer = offerService.getOfferById(id);
            model.addAttribute("offer", offer);
            return "/offers/edit";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @PathVariable Integer id, @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/offers/edit";
        }
        try {
            Offer updatedOffer = offerService.updateOffer(formOffer, id);
            return "redirect:/pizzas/" + updatedOffer.getPizza().getId();
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, @RequestParam("pizzaId") Optional<Integer> pizzaIdParam, RedirectAttributes redirectAttributes){
        Integer pizzaId = pizzaIdParam.get();
        try {
            offerService.deleteByID(id);
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.SUCCESS,
                    "Offerta cancellata"));
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR,
                    "Offerta non trovata"));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.FlashMessageType.ERROR,
                    "Impossibile cancellare"));
        }
        if (pizzaId == null) {
            return "redirect:/books";
        }
        return "redirect:/pizzas/" + Integer.toString(pizzaId);
    }
}
