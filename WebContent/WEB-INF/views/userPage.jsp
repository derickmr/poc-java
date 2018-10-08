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
<body class="colorBackground arial">

<div>
    <h2 class="centralize">Welcome ${user.ssoId}! <a href="<c:url value="/logout" />" > <button class="adminButtons"><i class="fa fa-sign-out"> Logout </i></button></a> </h2>
</div>

<br/>

    <h3 class="centralize">Days list</h3>


        <c:if test="${!empty workDays}">
            <table id = "workDays" class="centralize">
                <tr>
                    <th width="80">Day</th>
                    <th width="120">Shift</th>
                    <th width="60">Details</th>
                </tr>
                <c:forEach items="${workDays}" var="workDay">
                    <tr>
                        <td>${workDay.day.day} - ${workDay.day.month}</td>
                        <td>${workDay.shift}</td>
                        <td><a href="<c:url value='/workDayDetail/${workDay.id}' />" ><button class="adminButtons">Details</button></a> </td>
                        </tr>
                </c:forEach>
            </table>
        </c:if>

<%--</div>--%>

</body>
</html>
