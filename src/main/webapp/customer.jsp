<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18-Dec-23
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer</title>
</head>
<body>
<c:forEach items="${requestScope.customerList}" var="customer">
    <div style="border: 1px solid black; margin: 10px; padding: 10px; height: 10rem;">
        <h1>${customer.customerNumber}</h1>
        <hr>
        <h2>${customer.contactFirstName} ${customer.contactLastName}</h2>
    </div>
</c:forEach>
<a href="${pageContext.request.contextPath}/005/customer?action=add" style="border: 1px solid black; margin: 10px; padding: 10px; display: flex; justify-content: center; align-items: center; height: 10rem;">
    +
</a>
<a href="${pageContext.request.contextPath}/005/customer?back=1">back</a>
</body>
</html>
