<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 01/10/2018
  Time: 08:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>
    <title>Registration failed</title>
</head>
<body class="colorBackground arial centralize">
<div>
    <div>
    <h1>This user is already registered!</h1>
    </div>
    <br/>
    <div class="whiteFont">
    <a class="whiteFont" href="${goBack}">Go back!</a>
    </div>
</div>
</body>
</html>
