package com.crishito.aplicacionweb.manejosesiones.services;



import com.crishito.aplicacionweb.manejosesiones.models.Producto;

import java.util.Arrays;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    @Override
    public List<Producto> listar() {
        // Simulación de productos en memoria
        return Arrays.asList(
                new Producto(1L, "Laptop ASUS", "computadora", 1500),
                new Producto(2L, "Teclado Mecánico", "teclado", 150),
                new Producto(3L, "Monitor curvo 27", "monitor", 800)
        );
    }
}
