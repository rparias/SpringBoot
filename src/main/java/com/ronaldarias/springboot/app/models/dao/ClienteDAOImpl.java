package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cliente> buscarClientes() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        if(cliente.getId() != null && cliente.getId() > 0)
            em.merge(cliente);
        else
            em.persist(cliente);
    }
    //el metodo guardarCliente sirve para nuevos y para editar

    @Override
    public Cliente obtenerCliente(Long id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public void eliminarCliente(Long id) {
        em.remove(obtenerCliente(id));
    }
}
