package com.ronaldarias.springboot.app.controllers;

import com.ronaldarias.springboot.app.models.dao.ClienteDAO;
import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@SessionAttributes("cliente")   //sirve para mantener los datos del cliente hasta q status.setComplete() en editar
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/listar")
    public String listarClientes(Model model){
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clienteDAO.buscarClientes());
        return "listar";
    }

    @GetMapping("/form")
    public String crearCliente(Model model){
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardarCliente(@Valid Cliente cliente, BindingResult bindingResult, Model model, SessionStatus status){

        model.addAttribute("titulo", "Formulario de Cliente");
        //los resultados de @Valid se colocan en el bindingResult
        if(bindingResult.hasErrors())
            return "form";
        else{
            clienteDAO.guardarCliente(cliente);

            status.setComplete();   //con esto se elimina el objeto cliente de la session

            return "redirect:listar";
        }
    }

    @GetMapping("/form/{id}")
    public String editarCliente(@PathVariable(value="id") Long id, Model model){
        Cliente cliente;
        if(id > 0)
            cliente = clienteDAO.obtenerCliente(id);
        else
            return "redirect:/listar";
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");

        return "form";
    }
}
