package com.crishito.aplicacionweb.manejosesiones.models; // ¡PACKAGE CORREGIDO!

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleCarro {

    private List<ItemCarro> items;

    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    /**
     * Implementamos un método para agregar un producto al carro.
     * Si el ítem ya existe, incrementa la cantidad.
     * Si no existe, lo agrega a la lista.
     */
    public void addItemCarro(ItemCarro itemCarro) {

        // 1. Verificar si el ítem ya existe en la lista (usa el método equals de ItemCarro)
        // NOTA: Para que esto funcione, ItemCarro.equals() DEBE comparar solo por ID de Producto.
        if (items.contains(itemCarro)) {

            // 2. Si existe, buscar el ítem en la lista para obtener la instancia a modificar
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();

            // 3. Si se encuentra, incrementar la cantidad del ítem existente
            if (optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }

        } else {
            // 4. Si no existe, agregar el nuevo ítem a la lista
            this.items.add(itemCarro);
        }
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    public double getTotal() {
        // 5. Calcular el total usando Streams: mapea cada ItemCarro a su subtotal y suma todos los resultados
        return items.stream().mapToDouble(ItemCarro::getSubtotal).sum();
    }
}