<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Registration form</title>
</head>
<body>

<div class="success">
    Confirmation message : ${success}
    <br>
    Would you like to <a href="<c:url value='/newUser' />">Add More Users</a>?
    <br/>
    Go to <a href="<c:url value='/admin' />">Admin Page</a> OR <a href="<c:url value="/logout" />">Logout</a>
</div>


</body>
</html>
