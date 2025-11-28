package com.crishito.aplicacionweb.manejosesiones.models;

/*
 *Autor: Christian Zumárraga
 * Fecha: 12/11/2025
 * Descripción: Esta clase Producto va a instanciar todos nuestros modelos y atributos
 */

import java.time.LocalDate;

public class Producto {

    //Declaramos la variables de mi objeto producto
    private Long idProducto;
    private String nombre;
    private Categoria categoria;

    // CORRECCIÓN: Cambiado de 'int' a 'Integer' para permitir valores nulos.
    private Integer stock;

    private String descripcion;

    // CORRECCIÓN: Cambiado de 'double' a 'Double' para permitir valores nulos.
    private Double precio;

    private String codigo;
    private LocalDate fechaElaboracion;
    private LocalDate fechaCaducidad;
    private int condicion; // 'int' está bien aquí ya que es un campo booleano (0 o 1).


    /*
     * Constructor vacío obligatorio para JavaBeans
     */
    public Producto() {
    }

    /*
     * Sobre carga de constructores
     * NOTA: Actualizado para usar Integer y Double.
     */

    public Producto(Long idProducto, String nombre, Integer stock, String descripcion, Double precio, String codigo,String tipo, LocalDate fechaElaboracion, LocalDate fechaCaducidad, int condicion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        Categoria categoria = new Categoria ();
        categoria.setNombre(tipo);
        this.stock = stock;
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigo = codigo;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaCaducidad = fechaCaducidad;
        this.condicion = condicion;
    }

    // metodos getter a setter


    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // GETTER CORREGIDO
    public Integer getStock() {
        return stock;
    }

    // SETTER CORREGIDO
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // GETTER CORREGIDO
    public Double getPrecio() {
        return precio;
    }

    // SETTER CORREGIDO
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }
}