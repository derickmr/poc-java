<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 24/09/2018
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <title>User Page</title>
</head>
<body class="colorBackground centralize arial">

<div>

    <h2>Welcome ${pageContext.request.userPrincipal.name}! <a href="<c:url value="/logout" />" > <button class="adminButtons"><i class="fa fa-sign-out"> Logout </i></button></a> </h2>

</div>

</body>
</html>
