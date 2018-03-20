package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteDAO extends PagingAndSortingRepository<Cliente, Long> {
    //PagingAndSortingRepository extiende de CrudRepository y tiene los mismos metodos

    //CrudRepository<Cliente, Long> hace referencia a un mapa de cliente con su id

    @Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
    Cliente fetchByIdWithFacturas(Long id);

}
