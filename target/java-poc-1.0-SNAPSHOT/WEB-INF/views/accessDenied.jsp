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
    <title>Access denied page</title>
</head>
<body>


<h1>Dear <strong>${user}</strong>, You are not authorized to access this page.</h1>
<br/>
    <a href="<c:url value="/home" />">Go to home</a>
    <sec:authorize access="hasRole('USER') or hasRole('ADMIN')">
        OR  <a href="<c:url value="/logout" />">Logout</a>
    </sec:authorize>

</body>
</html>
