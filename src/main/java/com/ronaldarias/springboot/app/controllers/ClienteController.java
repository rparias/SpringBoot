package com.ronaldarias.springboot.app.controllers;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import com.ronaldarias.springboot.app.models.service.ClienteService;
import com.ronaldarias.springboot.app.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@SessionAttributes("cliente")   //sirve para mantener los datos del cliente hasta q status.setComplete() en editar
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String UPLOADS_FOLDER = "uploads";

    @GetMapping("/listar")
    public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

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
    public String crearCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardarCliente(@Valid Cliente cliente, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

        //los resultados de @Valid se colocan en el bindingResult
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        //para almacenar la foto
        if(!foto.isEmpty()){

            if(cliente.getId() != null && cliente.getId() > 0
                    && cliente.getFoto()!= null && cliente.getFoto().length() > 0){

                Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();

                if(archivo.exists() && archivo.canRead()){
                    archivo.delete();
                }

            }

            //creo un nombre unico para cada foto subida
            String uniqueFileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();

            //con esto se hace una ruta absoluta
            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFileName);
            Path rootAbsolutePath = rootPath.toAbsolutePath();

            log.info("rootPath: " + rootPath);
            log.info("rootAbsolutePath: " + rootAbsolutePath);

            try {
                Files.copy(foto.getInputStream(), rootAbsolutePath);
                flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFileName + "'");
                cliente.setFoto(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String mensajeFlush = (cliente.getId() != null) ? "Cliente editado exitosamente" : "Cliente creado con éxito";

        clienteService.guardarCliente(cliente);

        status.setComplete();   //con esto se elimina el objeto cliente de la session

        //mensaje para el usuario
        flash.addFlashAttribute("success", mensajeFlush);

        return "redirect:listar";
    }

    @GetMapping("/form/{id}")
    public String editarCliente(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente;
        if (id > 0) {
            cliente = clienteService.obtenerCliente(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El cliente no existe");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El id del cliente no puede ser cero");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");

        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Cliente cliente = clienteService.obtenerCliente(id);
            clienteService.eliminarCliente(id);

            //mensaje para el usuario
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");

            //cuando se elimina el cliente tambien se elimina la foto
            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
            File archivo = rootPath.toFile();

            if(archivo.exists() && archivo.canRead()){
                if(archivo.delete()){
                    flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito");
                }
            }
        }

        return "redirect:/listar";
    }

    // :.+ permite que Spring no borre o trunke la extension del archivo .jpg .png etc
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){
        Path pathFoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
        log.info("pathFoto: " + pathFoto);

        Resource recurso = null;
        try {
            recurso = new UrlResource(pathFoto.toUri());

            if(!recurso.exists() || !recurso.isReadable()){
                throw new RuntimeException("Error, no se puede cargar la imagen " + pathFoto.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() +"\"")
                .body(recurso);
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash){

        Cliente cliente = clienteService.obtenerCliente(id);
        if(cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());

        return "ver";
    }
}
