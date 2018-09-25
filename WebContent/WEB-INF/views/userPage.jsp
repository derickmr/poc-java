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
    <title>User Page</title>
</head>
<body>

<div>

    <h2>Welcome : ${pageContext.request.userPrincipal.name} |
        <a href="<c:url value="/logout" />" > Logout</a></h2>

</div>

</body>
</html>
