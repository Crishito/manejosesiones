package com.crishito.aplicacionweb.manejosesiones.services;

import com.crishito.aplicacionweb.manejosesiones.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService { // <-- ¡ESTO ES LA CLASE DE IMPLEMENTACIÓN!

    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                // Asegúrate de que los precios sean Double
                new Producto(1L, "notebook", "computacion", 175000.0),
                new Producto(2L, "mesa escritorio", "oficina", 100000.0),
                new Producto(3L, "teclado mecanico", "computacion", 40000.0)
        );
    }

    @Override
    public Optional<Producto> porId(Long id) {
        /*
         * Se utiliza streams para buscar el producto.
         * Importante: Usamos p.getIdProducto() para coincidir con tu clase Producto.
         */
        return listar().stream()
                .filter(p -> p.getIdProducto().equals(id))
                .findAny();
    }
}