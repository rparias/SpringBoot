package com.ronaldarias.springboot.app.models.service;

import com.ronaldarias.springboot.app.models.dao.ClienteDAO;
import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        //metodo heredado de CrudRepository
        return (List<Cliente>) clienteDAO.findAll();
    }

    @Override
    public Page<Cliente> buscarClientes(Pageable pageable) {
        return clienteDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public void guardarCliente(Cliente cliente) {
        //metodo heredado de CrudRepository
        clienteDAO.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerCliente(Long id) {
        //metodo heredado de CrudRepository
        return clienteDAO.findOne(id);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        //metodo heredado de CrudRepository
        clienteDAO.delete(id);
    }
}
