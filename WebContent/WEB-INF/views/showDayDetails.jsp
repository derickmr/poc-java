<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 05/10/2018
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Day details</title>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>
</head>
<body class="colorBackground arial">

<h2 class="centralize">${day.date}</h2>

<div>
    <a class="whiteFont" href="/calendars">Go back to Team Calendars</a>
    <br/>
</div>

<form:form action="/editDay" commandName="day">
    <table>
        <tr class="hide">
            <td>
                <form:label path="id">
                    <spring:message text=""/>
                </form:label>
            </td>
            <td>
                <form:input path="id" hidden="true"/>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="holiday">
                    <spring:message text="Check the box if it is a holiday"/>
                </form:label>
            </td>
            <td>
                <form:checkbox path="holiday"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="weekend">
                    <spring:message text="Check the box if it is a weekend"/>
                </form:label>
            </td>
            <td>
                <form:checkbox path="weekend"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button class="adminButtons"><input type="submit" value="<spring:message text="Set weekend/holiday"/>"/>
                </button>
            </td>
        </tr>
    </table>
</form:form>

<form:form action="/editDayShift" commandName="day">
    <h2>
        Edit shifts necessity for the day
    </h2>
    <table>
        <tr class="hide">
            <td>
                <form:label path="id">
                    <spring:message text=""/>
                </form:label>
            </td>
            <td>
                <form:input path="id" hidden="true"/>
            </td>
        </tr>

        <tr>
            <td>
                <label for="usersDay">Users needed on day shift</label>
                <input type="number" class="form-control" id="usersDay" name="usersDay" min="0" value="0" required>
            </td>
        </tr>
        <tr>
            <td>
                <label for="usersLate">Users needed on late shift</label>
                <input type="number" class="form-control" id="usersLate" name="usersLate" min="0" value="0" required>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button class="adminButtons"><input type="submit"
                                                    value="<spring:message text="Set shifts necessity"/>"/>
                </button>
            </td>
        </tr>
    </table>
</form:form>

<h3 class="centralize">Users list</h3>
<c:if test="${!empty workDays}">
    <table id="workDays" class="centralize">
        <tr>
            <th width="120">User SSO ID</th>
            <th width="120">User availability</th>
        </tr>
        <c:forEach items="${workDays}" var="workDay">
            <c:if test="${workDay.user.id != owner.id}">
                <tr>
                    <td>${workDay.user.ssoId}</td>
                    <c:choose>
                        <c:when test="${workDay.day.holiday}">
                            <c:if test="${workDay.canWorkAtHolidayOrWeekend}">
                                <td>${workDay.shift}</td>
                            </c:if>
                            <c:if test="${!workDay.canWorkAtHolidayOrWeekend}">
                                <td>Can't work</td>
                            </c:if>
                        </c:when>
                        <c:when test="${workDay.day.weekend}">
                            <c:if test="${workDay.canWorkAtHolidayOrWeekend}">
                                <td>${workDay.shift}</td>
                            </c:if>
                            <c:if test="${!workDay.canWorkAtHolidayOrWeekend}">
                                <td>Can't work</td>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <td>${workDay.shift}</td>
                        </c:otherwise>

                    </c:choose>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
