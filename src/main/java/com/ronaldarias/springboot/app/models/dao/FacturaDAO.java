package com.ronaldarias.springboot.app.models.dao;

import com.ronaldarias.springboot.app.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface FacturaDAO extends CrudRepository<Factura, Long> {
}
