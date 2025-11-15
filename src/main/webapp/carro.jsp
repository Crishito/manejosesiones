<%--
  Created by IntelliJ IDEA.
  User: Bluematrix
  Date: 14/11/2025
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista del Carrito de Compras.
  Muestra los ítems guardados en la sesión y calcula el total.
--%>
<%--
  VISTA FINAL: Carro de Compras
--%>
<%--
  Vista del Carrito de Compras.
  Muestra los ítems guardados en la sesión y calcula el total.
--%>
<%--
  Vista del Carrito de Compras.
  Muestra los ítems guardados en la sesión y calcula el total.
--%>

<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="com.crishito.aplicacionweb.manejosesiones.models.*"
%>


<%
    // Recupera el carrito de la sesión
    DetalleCarro detalleCarro =
            (DetalleCarro) session.getAttribute("carro");
%>


<html>

<head>
    <title>Carro de Compras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

</head>


<body style="background: linear-gradient(135deg, #0d6efd, #6f42c1); min-height:
100vh; padding: 20px;">


<div class="container my-5">
    <div class="card shadow-lg
p-4">
        <h1 class="text-center
mb-4 text-info"><i class="bi bi-cart-fill"></i> Carro
            de Compras</h1>

        <%
            if (detalleCarro == null ||
                    detalleCarro.getItems().isEmpty()) {
        %>
        <div class="alert
alert-warning text-center" role="alert">
            <i class="bi
bi-info-circle"></i> Lo sentimos, no hay productos en el carro de
            compras.
        </div>
        <div class="text-center">
            <a href="<%= request.getContextPath()%>/productos"
               class="btn btn-primary btn-lg mt-3"><i class="bi
bi-box-seam"></i> SEGUIR COMPRANDO</a>
        </div>
        <%
        } else {
        %>
        <table class="table
table-striped table-hover table-bordered text-center align-middle">
            <thead class="table-info">
            <tr>
                <th>Id Producto</th>
                <th>Nombre</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemCarro item :
                        detalleCarro.getItems()) {
            %>
            <tr>
                <td><%= item.getProducto().getIdProducto()%></td>
                <td><%= item.getProducto().getNombre()%></td>
                <td>$<%= String.format("%.2f",
                        item.getProducto().getPrecio())%></td>
                <td><%= item.getCantidad()%></td>
                <td>$<%= String.format("%.2f",
                        item.getSubtotal())%></td>
            </tr>
            <%
                }
            %>
            <tr class="table-dark">
                <td colspan="4"
                    class="text-end">Total Final:</td>
                <td class="text-end">$<%=
                String.format("%.2f", detalleCarro.getTotal())%></td>
            </tr>
            </tbody>
        </table>


        <div class="d-grid gap-2
d-md-flex justify-content-md-center mt-4">
            <a href="<%= request.getContextPath()%>/productos"
               class="btn btn-primary btn-lg"><i class="bi
bi-box-seam"></i> SEGUIR COMPRANDO</a>
            <a href="<%= request.getContextPath()%>/index.html"
               class="btn btn-outline-secondary btn-lg"><i class="bi
bi-house"></i> Volver al Inicio</a>

            <%-- ENLACES CORREGIDOS PARA USAR EL VERCARROSERVLET --%>
            <a href="<%= request.getContextPath()%>/ver-carro?action=factura"
               class="btn btn-info btn-lg"><i class="bi bi-receipt"></i>
                Generar Factura</a>
            <a href="<%= request.getContextPath()%>/ver-carro?action=excel"
               class="btn btn-success btn-lg"><i class="bi
bi-file-earmark-spreadsheet"></i> Exportar a Excel</a>
            <%-- FIN DE ENLACES CORREGIDOS --%>
        </div>
        <%
            }
        %>
    </div>

</div>


</body>

</html>