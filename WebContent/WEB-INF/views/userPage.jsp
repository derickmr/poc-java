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
                    <th width="140">Shift</th>
                    <th width="60">Users on day</th>
                    <th width="60">Users on late</th>
                    <th width="60">Details</th>


                </tr>
                <c:forEach items="${workDays}" var="workDay">
                    <tr>
                        <td align="center">${workDay.day.date}</td>
                        <c:choose>
                            <c:when test="${workDay.day.holiday or workDay.day.weekend}">
                                <c:if test="${workDay.canWorkAtHolidayOrWeekend}">
                                    <c:if test="${!empty workDay.shift}">
                                    <td align="center">${workDay.shift}</td>
                                    </c:if>
                                    <c:if test="${empty workDay.shift}">
                                        <td align="center">${workDay.desiredOriginalShift}</td>
                                    </c:if>
                                </c:if>
                                <c:if test="${!workDay.canWorkAtHolidayOrWeekend}">
                                    <td align="center">Can't work</td>
                                </c:if>
                            </c:when>
                            <c:when test="${workDay.day.weekend}">
                                <c:if test="${workDay.canWorkAtHolidayOrWeekend}">
                                    <c:if test="${!empty workDay.shift}">
                                    <td align="center">${workDay.shift}</td>
                                    </c:if>
                                    <c:if test="${empty workDay.shift}">
                                        <td align="center">${workDay.desiredOriginalShift}</td>
                                    </c:if>
                                </c:if>
                                <c:if test="${!workDay.canWorkAtHolidayOrWeekend}">
                                    <td align="center">Can't work</td>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${!empty workDay.shift}">
                                <td align="center">${workDay.shift}</td>
                                </c:if>
                                <c:if test="${empty workDay.shift}">
                                    <td align="center">${workDay.desiredOriginalShift}</td>
                                </c:if>
                            </c:otherwise>

                        </c:choose>

                        <td align="center">${workDay.day.usersOnDay}/${workDay.day.usersNeededOnDay}</td>
                        <td align="center">${workDay.day.usersOnLate}/${workDay.day.usersNeededOnLate}</td>
                        <td><a href="<c:url value='/workDayDetail/${workDay.id}' />" ><button class="adminButtons">Details</button></a> </td>
                        </tr>
                </c:forEach>
            </table>
        </c:if>

<c:if test="${!empty shiftMessages}">

    <table id="shiftMessages" class="centralize">

        <tr>
            <th width="450">Shift Messages</th>
            </tr>

        <c:forEach items="${shiftMessages}" var="shiftMessage">
            <tr>
                <c:forEach items="${userDayRelationsWithMessage}" var="userDayRelationWithMessage">
                    <c:if test="${shiftMessage.date.isEqual(userDayRelationWithMessage.day.date)}">
                        <td align="center"><a href="<c:url value="/workDayDetail/${userDayRelationWithMessage.id}"/>">${shiftMessage}</a></td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>

    </table>

</c:if>

<c:if test="${!empty normalMessages}">
    <table id = "normalMessages" class="centralize">
        <tr>
            <th width="550">Message</th>
        </tr>
        <c:forEach items="${normalMessages}" var="normalMessage">
            <tr>
                <td align="center">${normalMessage}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
