<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 22/09/2018
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Showing all users at console</title>
</head>
<body>

<select>
    <c:forEach var="line" items="${users}">
        <option><c:out value="${line}"/></option>
    </c:forEach>
</select>

<a href="<c:url value='/admin' />">Go back</a>

</body>
</html>
