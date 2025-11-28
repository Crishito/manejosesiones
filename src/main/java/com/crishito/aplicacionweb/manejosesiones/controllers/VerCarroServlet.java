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
import java.util.Locale; // Importar Locale para formato uniforme

@WebServlet("/ver-carro")
public class VerCarroServlet extends HttpServlet {

    // Constante para la tasa de IVA y formato de números
    private static final double IVA_RATE = 0.15;
    private static final String FORMAT = "%.2f";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        DetalleCarro detalleCarro = (DetalleCarro) req.getSession().getAttribute("carro");

        // --- MANEJO DE DESCARGAS ---
        if (detalleCarro != null && !detalleCarro.getItems().isEmpty()) {

            // Realizar los cálculos necesarios una sola vez
            double subtotal = detalleCarro.getTotal();
            double iva = subtotal * IVA_RATE;
            double totalAPagar = subtotal + iva;

            // Usamos Locale.US para garantizar que el separador decimal sea el punto,
            // crucial para la correcta lectura de archivos CSV y para la uniformidad
            String strSubtotal = String.format(Locale.US, FORMAT, subtotal);
            String strIva = String.format(Locale.US, FORMAT, iva);
            String strTotalAPagar = String.format(Locale.US, FORMAT, totalAPagar);


            if ("pdf".equals(action) || "factura".equals(action)) { // Se mantiene 'factura' por compatibilidad, pero en el JSP se usa 'pdf'

                // Lógica de Generar Factura/PDF (HTML)
                String nombreArchivo = "factura_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
                resp.setContentType("text/html;charset=UTF-8");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

                try (PrintWriter out = resp.getWriter()) {
                    out.println("<!DOCTYPE html><html><head>");
                    out.println("<meta charset='UTF-8'><title>Factura de Compra</title>");
                    // Estilos base para la factura HTML
                    out.println("<style>body { font-family: sans-serif; margin: 30px; } table { width: 100%; border-collapse: collapse; margin-top: 20px; } th, td { border: 1px solid #ccc; padding: 10px; text-align: left; } .resumen-compra { margin-top: 20px; border: 1px solid #ccc; padding: 10px; float: right; width: 40%; } .total-final { background-color: #0d6efd; color: white; font-weight: bold; font-size: 1.2em; }</style>");
                    out.println("</head><body>");
                    out.println("<h1>FACTURA DE COMPRA</h1>");
                    out.println("<p>Cliente: " + req.getSession().getAttribute("username") + "</p>");

                    // Tabla de productos
                    out.println("<table><thead><tr><th>Nombre</th><th>Precio Unitario</th><th>Cantidad</th><th>Subtotal</th></tr></thead><tbody>");

                    for (ItemCarro item : detalleCarro.getItems()) {
                        out.println("<tr>");
                        out.println("<td>" + item.getProducto().getNombre() + "</td>");
                        out.println("<td>$" + String.format(Locale.US, FORMAT, item.getProducto().getPrecio()) + "</td>");
                        out.println("<td>" + item.getCantidad() + "</td>");
                        out.println("<td>$" + String.format(Locale.US, FORMAT, item.getSubtotal()) + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</tbody></table>");

                    // 💡 INCLUSIÓN DEL RESUMEN DE TOTALES EN EL PDF/HTML
                    out.println("<div class='resumen-compra'>");
                    out.println("<h2>Resumen de Totales</h2>");
                    out.println("<table><tbody>");
                    out.println("<tr><td>Subtotal:</td><td>$" + strSubtotal + "</td></tr>");
                    out.println("<tr style='color: red;'><td>IVA (" + (int)(IVA_RATE * 100) + "%):</td><td>$" + strIva + "</td></tr>");
                    out.println("<tr class='total-final'><td>TOTAL A PAGAR:</td><td>$" + strTotalAPagar + "</td></tr>");
                    out.println("</tbody></table>");
                    out.println("</div>");

                    out.println("</body></html>");
                }
                return;

            } else if ("excel".equals(action)) {

                // Lógica de Exportar a Excel (CSV)
                String nombreArchivo = "carrito_export_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
                resp.setContentType("text/csv;charset=ISO-8859-1");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

                try (PrintWriter out = resp.getWriter()) {
                    out.println("ID Producto;Nombre;Categoria;Precio;Cantidad;Subtotal");

                    for (ItemCarro item : detalleCarro.getItems()) {
                        String linea = String.format(Locale.US, "%d;%s;%s;" + FORMAT + ";%d;" + FORMAT,
                                item.getProducto().getIdProducto(),
                                item.getProducto().getNombre().replace(';', ' '),
                                item.getProducto().getCategoria().getNombre().replace(';', ' '),
                                item.getProducto().getPrecio(),
                                item.getCantidad(),
                                item.getSubtotal());
                        out.println(linea);
                    }

                    // 💡 INCLUSIÓN DEL RESUMEN DE TOTALES EN EL CSV
                    out.println("\n"); // Línea de separación
                    out.println(";;;;SUBTOTAL;" + strSubtotal);
                    out.println(";;;;IVA (" + (int)(IVA_RATE * 100) + "%);" + strIva);
                    out.println(";;;;TOTAL A PAGAR;" + strTotalAPagar);
                }
                return;
            }
        }

        //Mostrar la vista del carrito (carro.jsp) ---
        getServletContext().getRequestDispatcher("/carro.jsp").forward(req, resp);
    }
}