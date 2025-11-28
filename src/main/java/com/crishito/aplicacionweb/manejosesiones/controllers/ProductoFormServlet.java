package com.crishito.aplicacionweb.manejosesiones.controllers;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoService;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoServiceJdbcImpl;
import com.crishito.aplicacionweb.manejosesiones.services.ServiceJdbcException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/producto/form")
public class ProductoFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        Long id = 0L;
        try {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                id = Long.parseLong(idParam);
            }
        } catch (NumberFormatException e) {
            id = 0L;
        }

        Producto producto = new Producto();
        producto.setCategoria(new Categoria());

        if (id > 0) {
            try {
                Optional<Producto> o = service.porId(id);
                if (o.isPresent()) {
                    producto = o.get();
                }
            } catch (ServiceJdbcException e) {
                throw new ServletException("Error al cargar producto por ID", e);
            }
        }

        try {
            req.setAttribute("categorias", service.listarCategoria());
        } catch (ServiceJdbcException e) {
            throw new ServletException("Error al listar categorías", e);
        }

        req.setAttribute("producto", producto);
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        Map<String, String> errores = new HashMap<>();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String codigo = req.getParameter("codigo");
        String precioParam = req.getParameter("precio");
        String stockParam = req.getParameter("stock");
        String idCategoriaParam = req.getParameter("idCategoria");
        String fechaElaboracionParam = req.getParameter("fechaElaboracion");
        String fechaCaducidadParam = req.getParameter("fechaCaducidad");
        String idParam = req.getParameter("id");
        String condicionParam = req.getParameter("condicion");

        Long id = 0L;
        Double precio = null; // Usamos Double, puede ser null
        Integer stock = null; // Usamos Integer, puede ser null
        Long idCategoria = 0L;
        LocalDate fechaElaboracion = null;
        LocalDate fechaCaducidad = null;
        int condicion = ("1".equals(condicionParam)) ? 1 : 0;

        try {
            if (idParam != null && !idParam.isBlank()) {
                id = Long.parseLong(idParam);
            }
        } catch (NumberFormatException e) {
            // Se ignora si el ID no es válido.
        }

        // --- VALIDACIONES ---

        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "El nombre es requerido.");
        }

        // Validación de Stock
        if (stockParam == null || stockParam.isBlank()) {
            errores.put("stock", "El stock no puede estar vacío.");
        } else {
            try {
                // Usamos valueOf para obtener un Integer
                stock = Integer.valueOf(stockParam.trim());
                if (stock <= 0) {
                    errores.put("stock", "El stock debe ser mayor que 0.");
                }
            } catch (NumberFormatException e) {
                errores.put("stock", "El stock debe ser un número entero válido.");
            }
        }

        // Validación de Precio
        if (precioParam == null || precioParam.isBlank()) {
            errores.put("precio", "El precio no puede estar vacío.");
        } else {
            try {
                // Usamos valueOf para obtener un Double
                precio = Double.valueOf(precioParam.trim().replace(',', '.'));
                if (precio <= 0) {
                    errores.put("precio", "El precio debe ser mayor que 0.");
                }
            } catch (NumberFormatException e) {
                errores.put("precio", "El precio debe ser un número válido.");
            }
        }

        // LA VALIDACIÓN DE DESCRIPCIÓN FUE ELIMINADA.
        // Si 'descripcion' es null o blank, simplemente se guarda como null o String vacío.

        if (codigo == null || codigo.isBlank()) {
            errores.put("codigo", "El código no puede estar vacío.");
        }

        if (idCategoriaParam == null || idCategoriaParam.isBlank() || "0".equals(idCategoriaParam)) {
            errores.put("categoria", "La categoría es obligatoria.");
        } else {
            try {
                idCategoria = Long.parseLong(idCategoriaParam);
            } catch (NumberFormatException e) {
                errores.put("categoria", "Categoría inválida.");
            }
        }

        if (fechaElaboracionParam == null || fechaElaboracionParam.isBlank()) {
            errores.put("fechaElaboracion", "La fecha de elaboración es requerida.");
        } else {
            try {
                fechaElaboracion = LocalDate.parse(fechaElaboracionParam, dateFormat);
            } catch (DateTimeParseException e) {
                errores.put("fechaElaboracion", "Formato de fecha de elaboración inválido.");
            }
        }

        // Fecha de Caducidad (No requerida)
        if (fechaCaducidadParam != null && !fechaCaducidadParam.isBlank()) {
            try {
                fechaCaducidad = LocalDate.parse(fechaCaducidadParam, dateFormat);
            } catch (DateTimeParseException e) {
                errores.put("fechaCaducidad", "Formato de fecha de caducidad inválido.");
            }
        }

        // --- MAPEO DEL PRODUCTO ---

        Producto producto = new Producto();
        producto.setIdProducto(id > 0 ? id : null);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion); // Puede ser null/vacío
        producto.setCodigo(codigo);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCondicion(condicion);
        producto.setFechaElaboracion(fechaElaboracion);
        producto.setFechaCaducidad(fechaCaducidad);

        Categoria categoria = new Categoria();
        categoria.setId(idCategoria);
        producto.setCategoria(categoria);

        // --- GUARDAR O RECARGAR FORMULARIO ---
        if (errores.isEmpty()) {
            try {
                service.guardar(producto);
                resp.sendRedirect(req.getContextPath() + "/productos");
            } catch (ServiceJdbcException e) {
                throw new ServletException("Error al guardar el producto", e);
            }
        } else {
            req.setAttribute("errores", errores);
            req.setAttribute("producto", producto);

            try {
                req.setAttribute("categorias", service.listarCategoria());
            } catch (ServiceJdbcException e) {
                throw new ServletException("Error al listar categorías para el formulario", e);
            }

            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}