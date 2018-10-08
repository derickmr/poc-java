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


<form:form action="/newCalendar" commandName="teamCalendar">
    <table>
        <tr>
            <td>
                <form:label path="startDay">
                    <spring:message text="Insert start day"/>
                </form:label>
            </td>
            <td>
                <form:input path="startDay" placeholder="Start day"/>
            </td>
        </tr>
        <tr>

        <tr>
            <td>
                <form:label path="startMonth">
                    <spring:message text="Insert start month"/>
                </form:label>
            </td>
            <td>
                <form:input path="startMonth" placeholder="Start month"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="endDay">
                    <spring:message text="Insert the end day"/>
                </form:label>
            </td>
            <td>
                <form:input path="endDay" placeholder="End day"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="endMonth">
                    <spring:message text="Insert the end month"/>
                </form:label>
            </td>
            <td>
                <form:input path="endMonth" placeholder="End month"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button class="adminButtons"><input type="submit" value="<spring:message text="Add Calendar"/>"/> </button>
            </td>
        </tr>


    </table>
</form:form>


<table id = "calendars" class="centralize">
    <tr>
        <th width="80">Day</th>
        <th width="60">Show details</th>
        <th width="60">Type of day</th>
    </tr>
    <c:forEach items="${calendars}" var="calendar">
        <c:forEach items="${calendar.days}" var="day">
        <tr>
            <td>${day.day}-${day.month}</td>
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
