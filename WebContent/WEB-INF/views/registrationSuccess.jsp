<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>User Registration form</title>
</head>
<body class="teste arial">

<div class="success">
    <p class="greenColor">${success}</p>
    <br/>
     Go to <a href="<c:url value="/login" />">Login</a> page
</div>


</body>
</html>
