package com.ronaldarias.springboot.app.models.service;

import com.ronaldarias.springboot.app.models.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> buscarClientes();
    void guardarCliente(Cliente cliente);
    Cliente obtenerCliente(Long id);
    void eliminarCliente(Long id);
}
