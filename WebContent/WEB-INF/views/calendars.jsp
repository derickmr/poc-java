<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 05/10/2018
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Calendars</title>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>
</head>
<body class="arial colorBackground">

<div>
<a class="whiteFont" href="/admin">Administrate users</a>
<br/>
</div>

<form action="/newCalendar">
    <label for="start-date">Start Date</label>
    <input type="date" class="form-control" id="start-date" name="start-date">

    <br/>
    <br/>

    <label for="end-date">End Date</label>
    <input type="date" class="form-control" id="end-date" name="end-date">

    <br/>
    <br/>

    <button type="submit" class="adminButtons">Create calendar</button>

</form>

<table id = "calendars" class="centralize">
    <tr>
        <th width="80">Day</th>
        <th width="60">Show details</th>
        <th width="60">Type of day</th>
    </tr>
    <c:forEach items="${calendars}" var="calendar">
        <c:forEach items="${calendar.days}" var="day">
        <tr>
            <td>${day.date}</td>
            <td><a href="<c:url value='/showDayDetails/${day.id}' />" ><button class="adminButtons">Details</button></a> </td>
            <td>
                <c:choose>
                <c:when test="${day.holiday}">
                    Holiday
                </c:when>
                <c:when test="${day.weekend}">
                    Weekend
                </c:when>
                <c:otherwise>
                    Normal day
                </c:otherwise>
                </c:choose>
            </td>

        </tr>
        </c:forEach>
    </c:forEach>
</table>
</body>
</html>
