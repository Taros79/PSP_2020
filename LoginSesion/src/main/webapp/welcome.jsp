<%@ page import="dao.modelo.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<% Usuario u = (Usuario) request.getSession().getAttribute("user"); %>
<h1>Hola <%= u.getName() %> ahora estas logeado como un pro</h1>
<form action="./logout" method="get">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
