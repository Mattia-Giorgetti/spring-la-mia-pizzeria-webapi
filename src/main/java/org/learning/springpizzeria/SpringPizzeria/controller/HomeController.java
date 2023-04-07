package org.learning.springpizzeria.SpringPizzeria.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(Model model, Authentication authentication){
        model.addAttribute("username", authentication.getName());
        return "home";
    }
}
