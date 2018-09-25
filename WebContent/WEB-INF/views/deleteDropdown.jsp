<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 25/09/2018
  Time: 09:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete an User</title>
</head>
<body>

Delete some user!

<%--<form action="/deleteUserBySsoId" method="post">--%>
    <%--<select name="user.ssoId">--%>
        <%--<c:forEach var="line" items="${users}">--%>
            <%--<option value="${line}">${line}</option>--%>
        <%--</c:forEach>--%>
    <%--</select>--%>
    <%--<input type="submit" value="Delete">--%>
<%--</form>--%>
<form:form method="post" action="/deleteUserBySsoId" modelAttribute="user">
    <form:select path="ssoId" items="${users}"/>

    <input type="submit">
</form:form>


<a href="/admin">Cancel</a>

</body>
</html>
