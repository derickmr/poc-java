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
    <%--<script src="/WEB-INF/resources/main.js"></script>--%>
</head>
<body class="arial colorBackground">
<script
        src="http://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
<script src="<c:url value="/WEB-INF/resources/main.js" />"></script>
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

    <label for="usersDay">Users needed on day shift</label>
    <input type="number" class="form-control" id="usersDay" name="usersDay" value="0" min="0" required>

    <br/>

    <label for="usersLate">Users needed on late shift</label>
    <input type="number" class="form-control" id="usersLate" name="usersLate" value="0" min="0" required>
    <br/>
    <button type="submit" class="adminButtons">Create calendar</button>

</form>

<form action="/newShiftMessage">
    <label for="day-needed">Day needed</label>
    <input type="date" class="form-control" id="day-needed" name="day-needed">

    <br/>
    <br/>

    <label>Select shift needed for the day</label>
    <select name="shift">
        <option value="Day">Day</option>
        <option value="Late">Late</option>
    </select>

    <br/>
    <br/>

    <button type="submit" class="adminButtons">Send shift necessity</button>

</form>

<table id = "calendars" class="centralize">
    <tr>
        <th width="80">Day</th>
        <th width="60">Show details</th>
        <th width="60">Type of day</th>
        <th width="60">Users on day</th>
        <th width="60">Users on late</th>
    </tr>
    <c:forEach items="${calendars}" var="calendar">
        <c:forEach items="${calendar.days}" var="day">
        <tr>
            <td>${day.date}</td>
            <td><a href="<c:url value='/showDayDetails/${day.id}' />" ><button class="adminButtons">Details</button></a> </td>
            <td align="center">
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
            <td>${day.usersOnDay}/${day.usersNeededOnDay}</td>
            <td>${day.usersOnLate}/${day.usersNeededOnLate}</td>

        </tr>
        </c:forEach>
    </c:forEach>
</table>
<br/>
<c:if test="${!empty shiftMessages}">

    <table id="shiftMessages" class="centralize">

        <tr>
            <th width="550">Shift Messages</th>
            <th width="40">Delete</th>
        </tr>

    <c:forEach items="${shiftMessages}" var="shiftMessage">
        <tr>
            <td align="center">${shiftMessage}</td>
            <td align="center"><a href="<c:url value='/deleteShiftMessage/${shiftMessage.id}' />" ><button class="adminButtons">Delete</button></a></td>
        </tr>
    </c:forEach>

    </table>

</c:if>

</body>
</html>