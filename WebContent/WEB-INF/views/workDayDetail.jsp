<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 06/10/2018
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Work day details</title>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>
</head>
<body class="colorBackground arial">
<h2 class="centralize">${workDay.day.day} - ${workDay.day.month}</h2>

<h3>Set your availability</h3>

<form:form action="/editWorkDay" commandName="workDay">
    <table>
        <c:choose>
        <c:when test="${workDay.day.holiday}">
            Is a holiday! Can you work?
            <%--<td>--%>
                <form:label path="canWorkAtHolidayOrWeekend"/>
            <%--</td>--%>
            <%--<td>--%>
                <form:checkbox path="canWorkAtHolidayOrWeekend"/>
            <%--</td>--%>
        </c:when>
            <c:when test="${workDay.day.weekend}">
                Is a weekend! Can you work?
                <%--<td>--%>
                    <form:label path="canWorkAtHolidayOrWeekend"/>
                <%--</td>--%>
                <%--<td>--%>
                    <form:checkbox path="canWorkAtHolidayOrWeekend"/>
                <%--</td>--%>
            </c:when>
            <c:otherwise>
                It is a normal day!
            </c:otherwise>

        </c:choose>

        <tr>
            <td>Set your shift: </td>
            <td><form:select path="shift">
                <form:option value="Any" label="--- Select ---" />
                <form:options items="${shiftOptions}"/>
            </form:select>
            </td>
            <%--<td><form:errors path="country" cssClass="error" /></td>--%>
        </tr>
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
            <td colspan="2">

                    <button class="adminButtons"><input type="submit" value="<spring:message text="Set shift"/>" /> </button>
                </td>
        </tr>
    </table>
</form:form>

</body>
</html>
