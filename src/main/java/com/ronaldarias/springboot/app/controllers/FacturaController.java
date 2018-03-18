package com.ronaldarias.springboot.app.controllers;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import com.ronaldarias.springboot.app.models.entity.Factura;
import com.ronaldarias.springboot.app.models.entity.ItemFactura;
import com.ronaldarias.springboot.app.models.entity.Producto;
import com.ronaldarias.springboot.app.models.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    //el servicio al ser facade contiene clientes, productos
    @Autowired
    private ClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash){
        Cliente cliente = clienteService.obtenerCliente(clienteId);
        if(cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear factura");

        return "factura/form";
    }

    //@ResponseBody en ves de devolver en formato lista devuelve en formato JSON
    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return clienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(Factura factura,
          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
          RedirectAttributes flash,
          SessionStatus status){

        for(int i = 0; i < itemId.length; i++){
            Producto producto = clienteService.findProductoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.agregarItemsFactura(linea);

            log.info("ID: " + itemId[i].toString() + " Cantidad: " + cantidad[i].toString());

            clienteService.saveFactura(factura);

            status.setComplete();

            flash.addFlashAttribute("success", "Factura creada con éxito!");
        }

        return "redirect:/ver/" + factura.getCliente().getId();
    }

}
