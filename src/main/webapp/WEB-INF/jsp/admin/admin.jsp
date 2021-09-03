<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="admin"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin.css">
    </head>
    <body>
    <div>
        <div class="admin-menu">
            <p class="title"><fmt:message key="admin"/></p>
            <a class="admin-menuitem" href="${pageContext.request.contextPath}/admin/users">
                <div><fmt:message key="list-users"/></div>
            </a>
            <a class="admin-menuitem" href="${pageContext.request.contextPath}/admin/quizzes">
                <div><fmt:message key="list-quizzes"/></div>
            </a>
            <a class="admin-menuitem" href="${pageContext.request.contextPath}/app/home">
                <div><fmt:message key="home"/></div>
            </a>
            <a class="admin-menuitem" href="${pageContext.request.contextPath}/app/logout">
                <div><fmt:message key="log-out"/></div>
            </a>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>