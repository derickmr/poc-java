<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User registration</title>
</head>
<body>

<form:input type="text" path="userId" id="userId" class="form-control input-sm"/>
<form:input type="text" path="userPassword" id="userPassword" class="form-control input-sm"/>
<input type="submit" value="Register" class="btn btn-primary btn-sm"> or <a href="<c:url value='/admin' />">Cancel</a>

</body>
</html>
