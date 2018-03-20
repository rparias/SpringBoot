package com.ronaldarias.springboot.app.models.service;

import com.ronaldarias.springboot.app.models.dao.ClienteDAO;
import com.ronaldarias.springboot.app.models.dao.FacturaDAO;
import com.ronaldarias.springboot.app.models.dao.ProductoDAO;
import com.ronaldarias.springboot.app.models.entity.Cliente;
import com.ronaldarias.springboot.app.models.entity.Factura;
import com.ronaldarias.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private FacturaDAO facturaDAO;

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
        return clienteDAO.findById(id).get();
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        //metodo heredado de CrudRepository
        clienteDAO.deleteById(id);
    }

    @Override
    public List<Producto> findByNombre(String term) {
        return productoDAO.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDAO.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoDAO.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaDAO.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDAO.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchClienteByIdWithFacturas(Long id) {
        return clienteDAO.fetchByIdWithFacturas(id);
    }
}
