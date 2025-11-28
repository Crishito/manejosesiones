<%--
  Created by IntelliJ IDEA.
  User: Bluematrix
  Date: 12/11/2025
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.crishito.aplicacionweb.manejosesiones.models.*,
                 java.util.List,
                 java.util.Map,
                 java.util.HashMap"
%>

<%
    // 1. OBTENER E INICIALIZAR VARIABLES (CRUCIAL para evitar NullPointerException)
    // Aseguramos que 'producto' y 'errores' nunca sean null antes de usarlos.
    Producto producto = (Producto) request.getAttribute("producto");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");

    // Inicializar el mapa de errores
    Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
    if (errores == null) {
        errores = new HashMap<>();
    }

    // Inicializar el objeto producto si es null (Ej: primera vez por GET)
    if (producto == null) {
        producto = new Producto();
    }

    // Asegurar que la categoría no sea null para el select
    if(producto.getCategoria() == null) {
        producto.setCategoria(new Categoria());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= (producto.getIdProducto() != null) ? "Editar Producto" : "Crear Nuevo Producto" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #0d6efd, #6f42c1);
            min-height: 100vh;
            padding: 20px;
        }
        .card {
            max-width: 600px;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="card shadow-lg p-4">

        <h1 class="text-center mb-4 text-primary"><i class="bi bi-box-seam"></i> <%= (producto.getIdProducto() != null) ? "Editar Producto" : "Crear Nuevo Producto" %></h1>

        <form action="<%= request.getContextPath() %>/producto/form" method="post">

            <%-- ID Oculto para Edición --%>
            <% if (producto.getIdProducto() != null) { %>
            <input type="hidden" name="id" value="<%= producto.getIdProducto() %>">
            <% } %>

            <%-- 1. Nombre --%>
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre"
                       class="form-control <%= errores.containsKey("nombre") ? "is-invalid" : "" %>"
                       value="<%= (producto.getNombre() != null) ? producto.getNombre() : "" %>">
                <% if (errores.containsKey("nombre")) { %>
                <div class="invalid-feedback"><%= errores.get("nombre") %></div>
                <% } %>
            </div>

            <%-- 2. Categoría (Select) --%>
            <div class="mb-3">
                <label for="idCategoria" class="form-label">Categoría</label>
                <select name="idCategoria" id="idCategoria"
                        class="form-select <%= errores.containsKey("categoria") ? "is-invalid" : "" %>">
                    <option value="0">-- Seleccionar Categoría --</option>
                    <% if (categorias != null) { %>
                    <% for (Categoria cat : categorias) { %>
                    <option value="<%= cat.getId() %>"
                            <%= (producto.getCategoria().getId() != null && cat.getId().equals(producto.getCategoria().getId())) ? "selected" : "" %>>
                        <%= cat.getNombre() %>
                    </option>
                    <% } %>
                    <% } %>
                </select>
                <% if (errores.containsKey("categoria")) { %>
                <div class="invalid-feedback"><%= errores.get("categoria") %></div>
                <% } %>
            </div>

            <div class="row">
                <%-- 3. Stock --%>
                <div class="col-md-6 mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="number" name="stock" id="stock"
                           class="form-control <%= errores.containsKey("stock") ? "is-invalid" : "" %>"

                           value="<%= (producto.getStock() != null && producto.getStock() > 0) ? producto.getStock() : "" %>">
                    <% if (errores.containsKey("stock")) { %>
                    <div class="invalid-feedback"><%= errores.get("stock") %></div>
                    <% } %>
                </div>

                <%-- 4. Precio --%>
                <div class="col-md-6 mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="number" step="0.01" name="precio" id="precio"
                           class="form-control <%= errores.containsKey("precio") ? "is-invalid" : "" %>"
                    <%-- CORRECCIÓN: Ahora es legal si Producto.precio es Double --%>
                           value="<%= (producto.getPrecio() != null && producto.getPrecio() > 0) ? String.format("%.2f", producto.getPrecio()) : "" %>">
                    <% if (errores.containsKey("precio")) { %>
                    <div class="invalid-feedback"><%= errores.get("precio") %></div>
                    <% } %>
                </div>
            </div>

            <%-- 5. Descripción --%>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea name="descripcion" id="descripcion" cols="30" rows="3"
                          class="form-control <%= errores.containsKey("descripcion") ? "is-invalid" : "" %>"><%= (producto.getDescripcion() != null) ? producto.getDescripcion() : "" %></textarea>
                <% if (errores.containsKey("descripcion")) { %>
                <div class="invalid-feedback"><%= errores.get("descripcion") %></div>
                <% } %>
            </div>

            <%-- 6. Código --%>
            <div class="mb-3">
                <label for="codigo" class="form-label">Código</label>
                <input type="text" name="codigo" id="codigo"
                       class="form-control <%= errores.containsKey("codigo") ? "is-invalid" : "" %>"
                       value="<%= (producto.getCodigo() != null) ? producto.getCodigo() : "" %>">
                <% if (errores.containsKey("codigo")) { %>
                <div class="invalid-feedback"><%= errores.get("codigo") %></div>
                <% } %>
            </div>

            <div class="row">
                <%-- 7. Fecha de Elaboración --%>
                <div class="col-md-6 mb-3">
                    <label for="fechaElaboracion" class="form-label">Fecha de Elaboración</label>
                    <input type="date" name="fechaElaboracion" id="fechaElaboracion"
                           class="form-control <%= errores.containsKey("fechaElaboracion") ? "is-invalid" : "" %>"
                           value="<%= (producto.getFechaElaboracion() != null) ? producto.getFechaElaboracion() : "" %>">
                    <% if (errores.containsKey("fechaElaboracion")) { %>
                    <div class="invalid-feedback"><%= errores.get("fechaElaboracion") %></div>
                    <% } %>
                </div>

                <%-- 8. Fecha de Caducidad --%>
                <div class="col-md-6 mb-3">
                    <label for="fechaCaducidad" class="form-label">Fecha de Caducidad</label>
                    <input type="date" name="fechaCaducidad" id="fechaCaducidad"
                           class="form-control <%= errores.containsKey("fechaCaducidad") ? "is-invalid" : "" %>"
                           value="<%= (producto.getFechaCaducidad() != null) ? producto.getFechaCaducidad() : "" %>">
                    <% if (errores.containsKey("fechaCaducidad")) { %>
                    <div class="invalid-feedback"><%= errores.get("fechaCaducidad") %></div>
                    <% } %>
                </div>
            </div>

            <%-- 9. Producto Activo (Condición Checkbox) --%>
            <div class="mb-3 form-check">
                <input type="checkbox" name="condicion" value="1" id="condicion" class="form-check-input"
                    <%= (producto.getCondicion() == 1) ? "checked" : "" %>>
                <label class="form-check-label" for="condicion">Producto Activo (Condición)</label>
            </div>


            <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-save"></i> Guardar Producto
                </button>
                <a href="<%= request.getContextPath() %>/productos" class="btn btn-secondary btn-lg">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
            </div>
        </form>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>