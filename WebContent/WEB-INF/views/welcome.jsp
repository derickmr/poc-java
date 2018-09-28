<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>
    <title>Welcome page</title>
</head>
<body class="centralize colorBackground arial">
<div id="main">
    <div class="success">
       <h2 class="arial"> Welcome to the Team Management System page! </h2>
    </div>
    <br/>
    <div>
        <h4 class="centralize arial">Go to <a class="centralize arial" href="<c:url value='/login' />"> Login page</a></h4>
        <h4 class="centralize arial">New Team Owner? <a href="<c:url value='/newAdmin' />"> Register!</a></h4>
    </div>
</div>
</body>
</html>
