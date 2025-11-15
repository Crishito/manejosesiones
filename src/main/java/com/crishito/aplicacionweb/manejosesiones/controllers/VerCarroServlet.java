package com.crishito.aplicacionweb.manejosesiones.controllers;

import com.crishito.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.crishito.aplicacionweb.manejosesiones.models.ItemCarro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ver-carro")
public class VerCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        DetalleCarro detalleCarro = (DetalleCarro) req.getSession().getAttribute("carro");

        // --- MANEJO DE DESCARGAS ---
        if (detalleCarro != null && !detalleCarro.getItems().isEmpty()) {

            if ("factura".equals(action)) {

                // Lógica de Generar Factura (HTML)
                String nombreArchivo = "factura_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
                resp.setContentType("text/html;charset=UTF-8");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

                try (PrintWriter out = resp.getWriter()) {
                    out.println("<!DOCTYPE html><html><head>");
                    out.println("<meta charset='UTF-8'><title>Factura de Compra</title>");
                    out.println("<style>body { font-family: sans-serif; margin: 30px; } table { width: 100%; border-collapse: collapse; margin-top: 20px; } th, td { border: 1px solid #ccc; padding: 10px; text-align: left; } .total { font-weight: bold; background-color: #f2f2f2; }</style>");
                    out.println("</head><body>");
                    out.println("<h1>FACTURA DE COMPRA</h1>");
                    out.println("<p>Cliente: " + req.getSession().getAttribute("username") + "</p>");
                    out.println("<table><thead><tr><th>Nombre</th><th>Precio Unitario</th><th>Cantidad</th><th>Subtotal</th></tr></thead><tbody>");

                    for (ItemCarro item : detalleCarro.getItems()) {
                        out.println("<tr>");
                        out.println("<td>" + item.getProducto().getNombre() + "</td>");
                        out.println("<td>$" + String.format("%.2f", item.getProducto().getPrecio()) + "</td>");
                        out.println("<td>" + item.getCantidad() + "</td>");
                        out.println("<td>$" + String.format("%.2f", item.getSubtotal()) + "</td>");
                        out.println("</tr>");
                    }

                    out.println("<tr class='total'><td colspan='3' style='text-align: right;'>TOTAL FINAL:</td><td>$" + String.format("%.2f", detalleCarro.getTotal()) + "</td></tr>");
                    out.println("</tbody></table>");
                    out.println("</body></html>");
                }
                return; // Importante: Detiene la ejecución para no reenviar al JSP.

            } else if ("excel".equals(action)) {

                // Lógica de Exportar a Excel (CSV)
                String nombreArchivo = "carrito_export_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
                resp.setContentType("text/csv;charset=ISO-8859-1");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

                try (PrintWriter out = resp.getWriter()) {
                    out.println("ID Producto;Nombre;Categoria;Precio;Cantidad;Subtotal");

                    for (ItemCarro item : detalleCarro.getItems()) {
                        String linea = String.format("%d;%s;%s;%.2f;%d;%.2f",
                                item.getProducto().getIdProducto(),
                                item.getProducto().getNombre().replace(';', ' '),
                                item.getProducto().getCategoria().replace(';', ' '),
                                item.getProducto().getPrecio(),
                                item.getCantidad(),
                                item.getSubtotal());
                        out.println(linea);
                    }
                    out.println(String.format(";;;;TOTAL;%.2f", detalleCarro.getTotal()));
                }
                return; // Importante: Detiene la ejecución para no reenviar al JSP.
            }
        }

        //Mostrar la vista del carrito (carro.jsp) ---
        getServletContext().getRequestDispatcher("/carro.jsp").forward(req, resp);
    }
}