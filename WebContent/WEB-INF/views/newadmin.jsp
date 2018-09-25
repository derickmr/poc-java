<%--
  Created by IntelliJ IDEA.
  User: I500782
  Date: 24/09/2018
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>New Admin Registration</title>
</head>
<body>
<h1>New Admin Registration Form</h1>

<form:form method="POST" modelAttribute="user" class="form-horizontal">

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="ssoId">SSO ID</label>
            <div class="col-md-7">
                <form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="ssoId" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="password">Password</label>
            <div class="col-md-7">
                <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="password" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>



    <div class="row">
        <div class="form-actions floatRight">
            <input type="submit" value="Register" class="btn btn-primary btn-sm"> or <a href="<c:url value='/admin' />">Cancel</a>
        </div>
    </div>
</form:form>
</body>
</html>
