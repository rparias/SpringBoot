package com.ronaldarias.springboot.app.models.service;

import com.ronaldarias.springboot.app.models.dao.ClienteDAO;
import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteDAO clienteDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarClientes() {
        return clienteDAO.buscarClientes();
    }

    @Override
    @Transactional
    public void guardarCliente(Cliente cliente) {
        clienteDAO.guardarCliente(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerCliente(Long id) {
        return clienteDAO.obtenerCliente(id);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        clienteDAO.eliminarCliente(id);
    }
}
