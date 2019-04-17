<%--
  Created by IntelliJ IDEA.
  User: samoilovich_y
  Date: 06.12.2018
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
    Exception error = pageContext.getException();
    StackTraceElement[] traceElements = error.getStackTrace();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Exception</title>
</head>
<body>
<h1><%= message%>
</h1>
<h2><%= exception%>
</h2>
<!--
<%= error%>
<%= error.getLocalizedMessage()%>
<%= error.getCause()%>
<% for(int i = 0; i < traceElements.length; i++){%>
    <%=traceElements[i]%>
<%}%>
-->


</body>
</html>
