package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Cliente;

import java.util.List;

public interface ClienteDAO {
    public List<Cliente> buscarClientes();
    public void guardarCliente(Cliente cliente);
}
