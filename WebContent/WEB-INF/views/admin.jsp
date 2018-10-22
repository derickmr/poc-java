<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 26/09/2018
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Admin page</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
    <style>
        <%@include file="/WEB-INF/resources/login_style.css"%>
    </style>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body class="arial colorBackground">

<div>
<a href="calendars">Administrate team calendars</a>
</div>

<div>
    <h1 class="centralize">
        Welcome ${admin.ssoId}! <a href="<c:url value="/logout" />" > <button class="adminButtons"><i class="fa fa-sign-out"> Logout </i></button></a>
    </h1>
</div>

<div>
<h2>
    Add/Edit an user
</h2>
</div>

<c:url var="addAction" value="/newUser" ></c:url>

<form:form action="${addAction}" commandName="user">
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
                <form:label path="ssoId">
                    <spring:message text="SSO ID"/>
                </form:label>
            </td>
            <td>
                <form:input path="ssoId" placeholder="Single Sign On ID" required="true"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password"/>
                </form:label>
            </td>
            <td>
                <form:input type="password" path="password" placeholder="Password" required="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty user.ssoId}">
                   <button class="adminButtons"><input type="submit"
                           value="<spring:message text="Edit User"/>" /> </button>
                </c:if>
                <c:if test="${empty user.ssoId}">
                   <button class="adminButtons"><input type="submit"
                           value="<spring:message text="Add User"/>" /> </button>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>
<br/>
<form action="/newNormalMessage">
    <label for="message">Send a message to your team!</label>
    <input type="text" class="form-control" id="message" name="message">

    <br/>
    <br/>

    <button type="submit" class="adminButtons">Send message</button>

</form>
<h3 class="centralize">Users list</h3>
<c:if test="${!empty users}">
    <table id = "users" class="centralize">
        <tr>
            <th width="80">User ID</th>
            <th width="120">User SSO ID</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.ssoId}</td>
                <td><a href="<c:url value='/edit/${user.id}' />" ><button class="adminButtons">Edit</button></a> </td>
                <td><a href="<c:url value='/remove/${user.id}' />" ><button class="adminButtons">Delete</button></a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br/>
<h3 class="centralize">Messages sent</h3>
<c:if test="${!empty normalMessages}">
    <table id = "normalMessages" class="centralize">
        <tr>
            <th width="450"></th>
            <th width="40"></th>
        </tr>
        <c:forEach items="${normalMessages}" var="normalMessage">
            <tr>
                <td align="center">${normalMessage}</td>
                <td><a href="<c:url value='/deleteNormalMessage/${normalMessage.id}' />" ><button class="adminButtons">Delete</button></a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
