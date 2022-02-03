<%--
  Created by IntelliJ IDEA.
  User: Mario
  Date: 29/12/2021
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pagina cambio Passw</title>
    <style>

        #div1{
            margin: auto;
            width: 50%;
            border: 3px solid green;
            padding: 10px;
        }


    </style>
</head>
<body>

<div id="div1">

    <form action="${pageContext.request.contextPath}/actualizarPassw" method="get">
        <label for="passwd">Contraseña:</label><br>
        <input type="hidden" name="code" value="${code}" >
        <input type="text" id="passwd" name="passwd"><br><br>
        <input type="submit" value="Entrar">
    </form>


</div>




</body>
</html>
