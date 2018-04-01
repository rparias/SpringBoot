package com.ronaldarias.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model, Principal principal, RedirectAttributes flash){

        //principal permite validar

        //si es distinto de null es porque ya tiene sesion iniciada
        if(principal != null){
            flash.addFlashAttribute("info", "Ya ha iniciado sesión previamente");
            return "redirect:/";
        }

        if(error != null){
            model.addAttribute("error", "Nombre de usuario o contraseña incorrecta");
        }

        model.addAttribute("titulo", "Inicio de sesión");
        return "/login";
    }

}
