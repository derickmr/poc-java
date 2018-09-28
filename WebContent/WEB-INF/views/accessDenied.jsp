<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Access denied page</title>
</head>
<body class="colorBackground arial centralize">
<div>
<div>
<h1>Dear <strong>${user}</strong>, you are not authorized to access this page.</h1>
</div>
<br/>
    <div class="centralize">
    <a href="<c:url value="/home" />"><button class="adminButtons"><i class="fa fa-home" aria-hidden="true">Home</i>
    </button></a>
    <sec:authorize access="hasRole('USER') or hasRole('ADMIN')">
          <a href="<c:url value="/logout" />" > <button class="adminButtons"><i class="fa fa-sign-out"> Logout </i></button></a>
    </sec:authorize>
    </div>
</div>
</body>
</html>
