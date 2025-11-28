package com.crishito.aplicacionweb.manejosesiones.services;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    // Métodos existentes para Producto
    List<Producto> listar();

    Optional<Producto> porId(Long id);

    // Nuevos métodos de persistencia para Producto
    void guardar(Producto producto);

    void eliminar(Long id);

    // Implementamos un mpetodo para listar una categoria y traer la categoria por id
    List<Categoria> listarCategoria();

    Optional<Categoria> porIdCategoria(Long id);

}