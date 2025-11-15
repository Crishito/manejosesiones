package com.crishito.aplicacionweb.manejosesiones.controllers;

import com.crishito.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.crishito.aplicacionweb.manejosesiones.models.ItemCarro;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoService;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoServiceImpl;

import jakarta.servlet.ServletException; // Usando Jakarta
import jakarta.servlet.annotation.WebServlet; // Usando Jakarta
import jakarta.servlet.http.HttpServlet; // Usando Jakarta
import jakarta.servlet.http.HttpServletRequest; // Usando Jakarta
import jakarta.servlet.http.HttpServletResponse; // Usando Jakarta
import jakarta.servlet.http.HttpSession; // Usando Jakarta

import java.io.IOException;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener el ID del producto de la URL. Primero se lee como String para validar.
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto inválido.");
            return;
        }

        Long id = Long.parseLong(idStr);
        ProductoService service = new ProductoServiceImpl();
        Optional<Producto> producto = service.porId(id);

        if (producto.isPresent()) {

            // 2. Crear un nuevo ItemCarro (cantidad 1 por defecto)
            ItemCarro item = new ItemCarro(1, producto.get());

            // 3. Obtener la sesión HTTP
            HttpSession session = req.getSession();

            // 4. Obtener el carrito de la sesión o crearlo si es null
            DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");

            if (detalleCarro == null) {
                detalleCarro = new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            }

            // 5. Agregar el ítem al carrito (la lógica de DetalleCarro maneja el incremento)
            detalleCarro.addItemCarro(item);

        }

        // 6. Redirigir al usuario a la vista del carrito para que vea los cambios
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}