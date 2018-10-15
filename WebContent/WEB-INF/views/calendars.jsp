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

    <label for="usersDay">Users needed on day shift</label>
    <input type="text" class="form-control" id="usersDay" name="usersDay">

    <br/>

    <label for="usersLate">Users needed on late shift</label>
    <input type="text" class="form-control" id="usersLate" name="usersLate">

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

<%--<form action="/newNormalMessage">--%>
    <%--<label for="message">Send a message to your team!</label>--%>
    <%--<input type="text" class="form-control" id="message" name="message">--%>

    <%--<br/>--%>
    <%--<br/>--%>

    <%--<button type="submit" class="adminButtons">Send message</button>--%>

<%--</form>--%>

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
        <%--<p>${shiftMessage}</p>--%>
        <tr>
            <td>${shiftMessage}</td>
            <td><a href="<c:url value='/deleteShiftMessage/${shiftMessage.id}' />" ><button class="adminButtons">Delete</button></a></td>
        </tr>
    </c:forEach>

    </table>

</c:if>

<%--<c:if test="${!empty normalMessages}">--%>
    <%--<table id = "normalMessages" class="centralize">--%>
        <%--<tr>--%>
            <%--<th width="550">Message</th>--%>
            <%--<th width="40">Delete</th>--%>
        <%--</tr>--%>
    <%--<c:forEach items="${normalMessages}" var="normalMessage">--%>
        <%--&lt;%&ndash;<p>${normalMessage}</p>&ndash;%&gt;--%>
        <%--<tr>--%>
            <%--<td>${normalMessage}</td>--%>
            <%--<td><a href="<c:url value='/deleteNormalMessage/${normalMessage.id}' />" ><button class="adminButtons">Delete</button></a></td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</table>--%>
<%--</c:if>--%>
</body>
</html>