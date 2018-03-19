package com.ronaldarias.springboot.app.models.service;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import com.ronaldarias.springboot.app.models.entity.Factura;
import com.ronaldarias.springboot.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    List<Cliente> buscarClientes();

    //este metodo retorna un Page para iterar las paginas
    Page<Cliente> buscarClientes(Pageable pageable);

    void guardarCliente(Cliente cliente);

    Cliente obtenerCliente(Long id);

    void eliminarCliente(Long id);

    List<Producto> findByNombre(String term);

    void saveFactura(Factura factura);

    Producto findProductoById(Long id);

    Factura findFacturaById(Long id);
}
