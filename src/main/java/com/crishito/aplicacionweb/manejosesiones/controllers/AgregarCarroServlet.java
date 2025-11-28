package com.crishito.aplicacionweb.manejosesiones.controllers;


import com.crishito.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.crishito.aplicacionweb.manejosesiones.models.ItemCarro;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoService;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoServiceJdbcImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;


@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        //Traemos la conexión
        Connection conn = (Connection) req.getAttribute("conn");

        //Instanciamos el nuevo objeto jdbc
        ProductoService service = new ProductoServiceJdbcImpl(conn); // Usamos la implementación JDBC

        Optional<Producto> producto = service.porId(id);

        if (producto.isPresent()) {
            ItemCarro item = new ItemCarro(1, producto.get());
            HttpSession session = req.getSession();
            DetalleCarro detalleCarro;

            if (session.getAttribute("carro") == null) {
                detalleCarro = new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            } else {
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }
            detalleCarro.addItemCarro(item);
        }
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}