<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet" type="text/css">
    <title>GmOwl</title>
    <style type="text/css">
        .container {
            width: 960px;
            margin-top: 60px;
            border: 2px solid black;
            color: white;
            font-size: 25px;
            padding: 20px;
        }
    </style>
</head>

<body>
<fmt:setBundle basename="application_lang" var="app"/>
<fmt:setBundle basename="about" var="about"/>
<div id="container">
    <div id="header">
        <h1>Gm<span class="off">Owl</span></h1>
        <h2><fmt:message key="motto" bundle="${app}"/></h2>
    </div>
    <div class="menu">
        <a class="menuitem" href="${pageContext.request.contextPath}/app/home">
            <div><fmt:message key="home" bundle="${app}"/></div>
        </a>
        <a class="menuitem" href="${pageContext.request.contextPath}/app/all-quizzes">
            <div><fmt:message key="all-quizzes" bundle="${app}"/></div>
        </a>
        <a class="menuitem" href="${pageContext.request.contextPath}/app/my-quizzes">
            <div><fmt:message key="my-quizzes" bundle="${app}"/></div>
        </a>
        <a class="menuitem active" href="${pageContext.request.contextPath}/app/about">
            <div><fmt:message key="about" bundle="${app}"/></div>
        </a>
        <div class="dropdown">
            <img class="profile-icon" src="${pageContext.request.contextPath}/images/profile.png"
                 alt="profile icon">
            <div class="dropdown-content">
                <c:if test="${user.accessLevel == 'admin'}">
                    <a href="${pageContext.request.contextPath}/admin"><fmt:message key="admin" bundle="${app}"/></a>
                </c:if>
                <a href="${pageContext.request.contextPath}/app/profile"><fmt:message key="profile" bundle="${app}"/></a>
                <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="log-out" bundle="${app}"/></a>
            </div>
        </div>
    </div>
    <div class="container">
        <div><fmt:message key="about.main" bundle="${about}"/></div>
        <div><fmt:message key="about.end" bundle="${about}"/></div>
    </div>
</div>
<div class="language">
    <a class="par" href="${pageContext.request.contextPath}?lang=en">
        <i><fmt:message key="language.en" bundle="${app}"/></i>
    </a>
    <a class="par" href="${pageContext.request.contextPath}?lang=ua">
        <i><fmt:message key="language.ua" bundle="${app}"/></i>
    </a>
</div>
</body>
</html>
