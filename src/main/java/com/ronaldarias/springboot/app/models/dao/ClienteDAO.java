package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteDAO extends PagingAndSortingRepository<Cliente, Long> {
    //PagingAndSortingRepository extiende de CrudRepository y tiene los mismos metodos

    //CrudRepository<Cliente, Long> hace referencia a un mapa de cliente con su id


}
