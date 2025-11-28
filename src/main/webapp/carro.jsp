<%--
Vista del Carrito de Compras.
 Muestra los ítems guardados en la sesión y calcula el total.
--%>

<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="com.crishito.aplicacionweb.manejosesiones.models.*,
                 java.util.Locale"
%>


<%
    // Recupera el carrito de la sesión
    DetalleCarro detalleCarro =
            (DetalleCarro) session.getAttribute("carro");

    // Lógica de cálculo de totales
    final double IVA_RATE = 0.15; // 15% de IVA
    double subtotal = 0.0;
    double iva = 0.0;
    double totalAPagar = 0.0;
    String format = "%.2f"; // Formato para dos decimales (ej: 17.71)

    if (detalleCarro != null) {
        subtotal = detalleCarro.getTotal();
        iva = subtotal * IVA_RATE;
        totalAPagar = subtotal + iva;
    }
%>


<html>

<head>
    <title>Carro de Compras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        .list-group-item h2 {
            font-size: 2.2rem; /* Aumenta el tamaño de la fuente para el total */
        }
    </style>

</head>


<body style="background: linear-gradient(135deg, #0d6efd, #6f42c1); min-height:
100vh; padding: 20px;">


<div class="container my-5">
    <div class="card shadow-lg p-4">
        <h1 class="text-center mb-4 text-info"><i class="bi bi-cart-fill"></i> Carro
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
                <th>Precio Unitario</th>
                <th>Cantidad</th>
                <th>Total Item</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ItemCarro item : detalleCarro.getItems()) {
            %>
            <tr>
                <td><%= item.getProducto().getIdProducto()%></td>
                <td><%= item.getProducto().getNombre()%></td>
                <td>$<%= String.format(Locale.US, format, item.getProducto().getPrecio())%></td>
                <td><%= item.getCantidad()%></td>
                <td>$<%= String.format(Locale.US, format, item.getSubtotal())%></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <div class="row justify-content-end mt-4">
            <div class="col-md-6 col-lg-5">
                <div class="card border-info shadow-lg">
                    <div class="card-header bg-info text-white text-center fw-bold fs-5">
                        RESUMEN DE COMPRA
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <h5 class="mb-0 text-dark">Subtotal:</h5>
                            <h5 class="mb-0 text-dark fw-bold">$<%= String.format(Locale.US, format, subtotal) %></h5>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center bg-light">
                            <h4 class="mb-0 text-danger">IVA (15%):</h4>
                            <h4 class="mb-0 text-danger fw-bold">$<%= String.format(Locale.US, format, iva) %></h4>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center bg-primary text-white">
                            <h2 class="mb-0 fw-bold">TOTAL A PAGAR:</h2>
                            <h2 class="mb-0 fw-bold">$<%= String.format(Locale.US, format, totalAPagar) %></h2>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="d-grid gap-2
d-md-flex justify-content-md-center mt-5">
            <a href="<%= request.getContextPath()%>/productos"
               class="btn btn-primary btn-lg"><i class="bi
bi-box-seam"></i> SEGUIR COMPRANDO</a>
            <a href="<%= request.getContextPath()%>/index.html"
               class="btn btn-outline-secondary btn-lg"><i class="bi
bi-house"></i> Volver al Inicio</a>

            <a href="<%= request.getContextPath()%>/ver-carro?action=pdf"
               class="btn btn-danger btn-lg"><i class="bi bi-file-earmark-pdf"></i>
                Generar PDF</a>
            <a href="<%= request.getContextPath()%>/ver-carro?action=excel"
               class="btn btn-success btn-lg"><i class="bi
bi-file-earmark-spreadsheet"></i> Generar Excel</a>
            <%-- FIN DE ENLACES --%>
        </div>
        <%
            }
        %>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>