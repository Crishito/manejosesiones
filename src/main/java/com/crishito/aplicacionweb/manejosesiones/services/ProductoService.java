package com.crishito.aplicacionweb.manejosesiones.services; // ¡PACKAGE CORREGIDO!

import com.crishito.aplicacionweb.manejosesiones.models.Producto; // ¡PACKAGE CORREGIDO!

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> listar();

    // Nuevo método para buscar por ID
    Optional<Producto> porId(Long id);

}