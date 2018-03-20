package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FacturaDAO extends CrudRepository<Factura, Long> {

    @Query("select f from Factura f join fetch f.cliente c join fetch f.itemsFactura l join fetch l.producto where f.id=?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
