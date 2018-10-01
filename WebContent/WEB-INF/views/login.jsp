<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 21/09/2018
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%--<link rel="stylesheet" type="text/css" href="/WEB-INF/resources/login_style.css">--%>
<%--<jsp:include page="/WEB-INF/resources/login_style.css"/>--%>
<head>
    <%--<jsp:include page="/WEB-INF/resources/login_style.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/WEB-INF/resources/login_style.css">--%>
        <%--<link rel="stylesheet" type="text/css" href="/WEB-INF/resources/login_style.css">--%>
    <title>Login page</title>
</head>
<body class="arial">

<style>
    <%@include file="/WEB-INF/resources/login_style.css"%>
</style>

<div id="mainWrapper">
    <div class="teste">  <!-- login-container ; login-form -->
        <div class="box">
            <form action="${loginUrl}" method="post" class="form-horizontal">
                <c:if test="${param.error != null}">
                    <div class = "redColor">
                        <p>Invalid Single Sign On ID and Password.</p>
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-success greenColor arial">
                        <p class="greenColor">You have been logged out successfully.</p>
                    </div>
                </c:if>

                <div class="input-group input-sm">
                    <label class="input-group-addon" for="ssoId">SSO ID</label>
                    <input type="text" class="form-control" id="ssoId" name="ssoId" placeholder="Enter Single Sign On ID" required>
                </div>

                <div class="input-group input-sm">
                    <label class="input-group-addon" for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                </div>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}" />

                <div class="form-actions centralize">
                    <button type="submit"> Login </button>
                    <a href="/"> <button type="button" class="cancelbtn">Cancel</button> </a>
                </div>
            </form>
        </div>

        <div class="login-card">
                <c:url var="loginUrl" value="/login" />
        </div>
    </div>
</div>


</body>
</html>
