package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Cliente;

import java.util.List;

public interface ClienteDAO {
    List<Cliente> buscarClientes();
    void guardarCliente(Cliente cliente);
    Cliente obtenerCliente(Long id);
    void eliminarCliente(Long id);
}
