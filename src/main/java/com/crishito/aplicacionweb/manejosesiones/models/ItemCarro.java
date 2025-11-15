package com.crishito.aplicacionweb.manejosesiones.models;

import java.util.Objects;

public class ItemCarro {

    private int cantidad;
    private Producto producto;

    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Compara si dos ItemCarro representan el MISMO producto.
     * Es crucial para que DetalleCarro.addItemCarro sepa si debe
     * añadir un ítem nuevo o incrementar la cantidad de uno existente.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCarro itemCarro = (ItemCarro) o;

        // CORRECCIÓN: Solo comparamos por el ID del producto
        return Objects.equals(this.producto.getIdProducto(), itemCarro.producto.getIdProducto());

        // Nota: La comparación de 'cantidad' se ELIMINA para que la lógica del carrito funcione.
    }

    // Es buena práctica incluir hashCode() si se redefine equals(), aunque no es crítico para este caso simple.
    @Override
    public int hashCode() {
        return Objects.hash(producto.getIdProducto());
    }

    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }
}