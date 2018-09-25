<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome page</title>
</head>
<body>

    <div class="success">
        This is a welcome page.
    </div>

    <div>
        Go to <a href="<c:url value='/login' />">Login page</a>
        <br/>
        New Team Owner? <a href="<c:url value='/newAdmin' />">Register!</a>
    </div>

</body>
</html>
