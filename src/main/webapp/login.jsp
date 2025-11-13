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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        /* Estilo para que el fondo coincida con el index.html */
        body {
            /* Mismo degradado que tu index.html */
            background: linear-gradient(135deg, #0d6efd, #6f42c1);
        }
        .card {
            border-radius: 1rem;
        }
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center vh-100">
<div class="card shadow-lg p-4" style="width: 24rem;">
    <h1 class="text-center mb-4 text-primary"><i class="bi bi-person-lock"></i> Inicio de Sesión</h1>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <div class="mb-3">
            <label for="username" class="form-label"><i class="bi bi-person"></i> Usuario</label>
            <input type="text" name="username" id="username" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label"><i class="bi bi-key"></i> Contraseña</label>
            <input type="password" name="password" id="password" class="form-control" required>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-lg mt-3"><i class="bi bi-door-open"></i> Iniciar Sesión</button>
        </div>
    </form>
</div>
</body>
</html>