<%--
  Created by IntelliJ IDEA.
  User: Bluematrix
  Date: 12/11/2025
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Usuario</title>
 </head>
<body class="bg-light d-flex align-items-center justify-content-center vh-100">
<div class="card shadow p-4" style="width: 24rem;">
    <h1 class="text-center mb-4 text-primary">Inicio de Sesión</h1>
    <form action="/manejosesiones/login" method="post">
        <div class="mb-3">
            <label for="user" class="form-label">Usuario</label>
            <input type="text" name="user" id="user" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input type="password" name="password" id="password" class="form-control" required>
        </div>
        <div class="d-grid">
            <input type="submit" value="Iniciar Sesión" class="btn btn-primary">
        </div>
    </form>
</div>
</body>
</html>