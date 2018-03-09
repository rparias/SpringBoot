package com.ronaldarias.springboot.app.controllers;

import com.ronaldarias.springboot.app.models.dao.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/listar")
    public String listarClientes(Model model){
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clienteDAO.buscarClientes());
        return "listar";
    }
}
