<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.epam.final_project.dao.model.User" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <!DOCTYPE html>
    <html>

<%--    <%--%>
<%--        User user = (User) session.getAttribute("user");--%>

<%--        if (user != null) {--%>
<%--            response.sendRedirect("/app/home");--%>
<%--        }--%>
<%--    %>--%>
    <head>
        <link href="${pageContext.request.contextPath}/styles/auth.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title><fmt:message key="sign-in"/></title>
    </head>
    <body>
    <div class="main">
        <p class="sign"><fmt:message key="sign-in"/></p>
        <form class="form1" action="${pageContext.request.contextPath}/login" method="POST">
            <c:if test="${not empty param.error}">
                <p class="incr"><fmt:message key="error.login.input"/></p>
            </c:if>
            <c:if test="${not empty param.state}">
                <p class="incr"><fmt:message key="error.login.state"/></p>
            </c:if>
            <input class="un " type="text" placeholder="Username" name="login">
            <input class="pass" type="password" placeholder="Password" name="password">
            <button class="submit" type="submit"><fmt:message key="sign-in"/></button>
            <p class="forgot">
                <a href="${pageContext.request.contextPath}/registration.jsp"><fmt:message key="register"/></a>
            </p>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>