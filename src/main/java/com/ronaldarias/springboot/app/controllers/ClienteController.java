package com.ronaldarias.springboot.app.controllers;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import com.ronaldarias.springboot.app.models.service.ClienteService;
import com.ronaldarias.springboot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("cliente")   //sirve para mantener los datos del cliente hasta q status.setComplete() en editar
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model){

        //page es la pagina actual
        //size es el numero de elementos por pagina
        int size = 5;
        Pageable pageRequest = new PageRequest(page, size);
        Page<Cliente> clientes = clienteService.buscarClientes(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
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
    public String guardarCliente(@Valid Cliente cliente, BindingResult bindingResult, Model model, RedirectAttributes flash, SessionStatus status){

        //los resultados de @Valid se colocan en el bindingResult
        if(bindingResult.hasErrors()){
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        } else{
            String mensajeFlush = (cliente.getId() != null) ? "Cliente editado exitosamente" : "Cliente creado con éxito";

            clienteService.guardarCliente(cliente);

            status.setComplete();   //con esto se elimina el objeto cliente de la session

            //mensaje para el usuario
            flash.addAttribute("success", mensajeFlush);

            return "redirect:listar";
        }
    }

    @GetMapping("/form/{id}")
    public String editarCliente(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash){
        Cliente cliente;
        if(id > 0){
            cliente = clienteService.obtenerCliente(id);
            if(cliente == null){
                flash.addAttribute("error", "El cliente no existe");
                return "redirect:/listar";
            }
        }
        else{
            flash.addAttribute("error", "El id del cliente no puede ser cero");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");

        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable(value="id") Long id, RedirectAttributes flash){

        if(id > 0){
            clienteService.eliminarCliente(id);

            //mensaje para el usuario
            flash.addAttribute("success", "Cliente creado con éxito");
        }

        return "redirect:/listar";
    }
}
