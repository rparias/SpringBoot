package com.ronaldarias.springboot.app.models.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String descripcion;

    private String observacion;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "create_at")
    private Date createAt;

    //Varias facturas tienen un cliente
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "factura_id")    //es unidireccional
    private List<ItemFactura> itemsFactura;

    //metodo auxiliar para agregar items en la factura
    public void agregarItemsFactura(ItemFactura item) {
        if (itemsFactura == null) {
            itemsFactura = new ArrayList<>();
        }

        itemsFactura.add(item);
    }

    public Double calcularTotal(){
        Double total = 0.0;

        for(ItemFactura item : itemsFactura){
            total += item.calcularImporte();
        }

        return total;
    }

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItemsFactura() {
        return itemsFactura;
    }

    public void setItemsFactura(List<ItemFactura> itemsFactura) {
        this.itemsFactura = itemsFactura;
    }
}
