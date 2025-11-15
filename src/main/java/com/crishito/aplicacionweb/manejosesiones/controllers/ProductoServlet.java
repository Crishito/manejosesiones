package com.crishito.aplicacionweb.manejosesiones.controllers;

import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.services.LoginService;
import com.crishito.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoService;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductoService service = new ProductoServiceImpl();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            // 1. Estructura HTML y Diseño Bootstrap
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <title>Listado de Productos</title>");
            out.println("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css\" rel=\"stylesheet\">");
            out.println("</head>");

            out.println("<body style=\"background: linear-gradient(135deg, #0d6efd, #6f42c1); min-height: 100vh; padding: 20px;\">");
            out.println("<div class=\"container my-5\">");
            out.println("<div class=\"card shadow-lg p-4\">");

            out.println("    <h1 class=\"text-center mb-4 text-primary\"><i class=\"bi bi-boxes\"></i> Listado de Productos</h1>");

            // Mensaje de bienvenida condicional con alerta Bootstrap
            if (usernameOptional.isPresent())
            {
                out.println("  <div class=\"alert alert-success text-center\" role=\"alert\">Hola <strong>" + usernameOptional.get() + "</strong>, ¡Bienvenido!</div>");
            } else {
                out.println("  <div class=\"alert alert-warning text-center\" role=\"alert\">Inicia sesión para ver los precios.</div>");
            }

            // Enlace para Ver Carrito (Se mantiene el texto por claridad)
            out.println("<div class=\"text-end mb-3\">");
            out.println("<a href=\"" + req.getContextPath() + "/ver-carro\" class=\"btn btn-info\"><i class=\"bi bi-cart\"></i> Ver Carrito</a>");
            out.println("</div>");

            // Inicio de la tabla con clases de Bootstrap
            out.println("    <table class=\"table table-striped table-hover table-bordered text-center align-middle mt-4\">");
            out.println("               <thead class=\"table-dark\">");
            out.println("                    <tr>");
            out.println("                        <th><i class=\"bi bi-hash\"></i> Id</th>");
            out.println("                            <th><i class=\"bi bi-tag\"></i> Nombre</th>");
            out.println("                            <th><i class=\"bi bi-collection\"></i> Tipo</th>");

            // Encabezados condicionales (solo para usuarios logueados)
            if (usernameOptional.isPresent())
            {
                out.println("                            <th><i class=\"bi bi-currency-dollar\"></i> Precio</th>");
                // **COLUMNA DE OPCIONES RESTAURADA (SOLO ICONO)**
                out.println("                               <th><i class=\"bi bi-cart-plus\"></i></th>");
            }
            out.println("                    </tr>");
            out.println("            </thead>");
            out.println("            <tbody>");

            // Iteración sobre la lista de productos
            productos.forEach(p -> {
                out.println("            <tr>");
                out.println("                    <td>" + p.getIdProducto() + "</td>");
                out.println("                    <td>" + p.getNombre() + "</td>");
                out.println("                       <td>" + p.getCategoria() + "</td>");

                // Columnas de datos condicionales
                if (usernameOptional.isPresent())
                {
                    out.println("                    <td>$" + String.format("%.2f", p.getPrecio()) + "</td>");

                    // **BOTÓN 'AGREGAR' RESTAURADO (SOLO ICONO)**
                    out.println("                    <td><a href=\"" + req.getContextPath() + "/agregar-carro?id=" + p.getIdProducto() + "\" class=\"btn btn-sm btn-success\"><i class=\"bi bi-cart-plus\"></i></a></td>");
                }
                out.println("            </tr>");
            });

            // Cierre de la tabla y estructura HTML
            out.println("            </tbody>");
            out.println("    </table>");

            // Botón de Volver al Inicio
            out.println("<div class=\"text-center mt-4\">");
            out.println("<a href=\"" + req.getContextPath() + "/index.html\" class=\"btn btn-outline-primary btn-lg\"><i class=\"bi bi-house\"></i> Volver al inicio</a>");
            out.println("</div>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}