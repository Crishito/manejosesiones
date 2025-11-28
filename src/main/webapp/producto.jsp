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
                 java.util.Optional"
%>

<%
    // Obtener la lista de productos y el Optional<String> username
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    Optional<String> usernameOptional = (Optional<String>) request.getAttribute("username");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        /* Estilo de fondo acorde al index.html y login.jsp */
        body {
            background: linear-gradient(135deg, #0d6efd, #6f42c1);
            min-height: 100vh;
            padding: 20px;
        }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="card shadow-lg p-4">

        <h1 class="text-center mb-4 text-primary"><i class="bi bi-boxes"></i> Listado de
            Productos</h1>

        <%-- Mensaje de bienvenida condicional --%>
        <% if (usernameOptional.isPresent()) { %>
        <div class="alert alert-success text-center" role="alert">
            Hola <strong><%= usernameOptional.get() %></strong>, ¡Bienvenido!

            <%-- ✨ ENLACE CREAR PRODUCTO CORREGIDO Y CON ESTILO DE BOTÓN ✨ --%>
            <p class="mt-2">
                <a href="<%= request.getContextPath() %>/producto/form" class="btn btn-sm btn-primary">
                    <i class="bi bi-plus-square"></i> Crear un producto
                </a>
            </p>
            <%-- ✨ FIN DEL ENLACE ✨ --%>
        </div>
        <% } else { %>
        <div class="alert alert-warning text-center" role="alert">
            Inicia sesión para ver los precios y agregar productos al carrito.
        </div>
        <% } %>

        <%-- Enlace para Ver Carrito --%>
        <div class="text-end mb-3">
            <a href="<%= request.getContextPath() %>/ver-carro"
               class="btn btn-info"><i class="bi bi-cart"></i> Ver Carrito</a>
        </div>


        <table class="table table-striped table-hover table-bordered text-center align-middle mt-4">
            <thead class="table-dark">
            <tr>
                <th><i class="bi bi-hash"></i> Id</th>
                <th><i class="bi bi-tag"></i> Nombre</th>
                <th><i class="bi bi-collection"></i> Tipo</th>
                <th>Stock</th>
                <th>Descripción</th>
                <th>Código</th>
                <th>Fecha Elaboración</th>
                <th>Fecha Caducidad</th>
                <th>Condición</th>
                <% if (usernameOptional.isPresent()) { %>
                <th><i class="bi bi-currency-dollar"></i> Precio</th>
                <th><i class="bi bi-cart-plus"></i> Acción</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Producto p : productos) { %>
            <tr>
                <td><%= p.getIdProducto() %>
                </td>
                <td><%= p.getNombre() %>
                </td>
                <td><%= p.getCategoria().getNombre() %>
                </td>
                <td><%= p.getStock() %>
                </td>
                <td><%= p.getDescripcion() %>
                </td>
                <td><%= p.getCodigo() %>
                </td>
                <td><%= p.getFechaElaboracion() %>
                </td>
                <td><%= p.getFechaCaducidad() %>
                </td>
                <td><%= p.getCondicion() %>
                </td>
                <% if (usernameOptional.isPresent()) { %>
                <td>$<%= String.format("%.2f", p.getPrecio()) %>
                </td>
                <td>
                    <a href="<%= request.getContextPath() %>/agregar-carro?id=<%= p.getIdProducto() %>"
                       class="btn btn-sm btn-success"><i class="bi bi-cart-plus"></i>
                        Agregar al carro</a>
                </td>
                <% } %>
            </tr>
            <% } %>
            </tbody>
        </table>

        <%-- Botón de Volver al Inicio --%>
        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/index.html" class="btn btn-outline-primary btn-lg">
                <i class="bi bi-house"></i> Volver al inicio</a>
        </div>

    </div>
</div>

</body>
</html>