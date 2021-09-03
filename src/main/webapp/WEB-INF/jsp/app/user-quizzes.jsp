<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet" type="text/css">
    <title>GmOwl</title>
</head>

<body>
<fmt:bundle basename="application_lang">
    <div id="container">
        <div id="header">
            <h1>Gm<span class="off">Owl</span></h1>
            <h2><fmt:message key="motto"/></h2>
        </div>
        <div class="menu">
            <a class="menuitem" href="${pageContext.request.contextPath}/app/home">
                <div><fmt:message key="home"/></div>
            </a>
            <a class="menuitem" href="${pageContext.request.contextPath}/app/all-quizzes">
                <div><fmt:message key="all-quizzes"/></div>
            </a>
            <a class="menuitem active" href="${pageContext.request.contextPath}/app/my-quizzes">
                <div><fmt:message key="my-quizzes"/></div>
            </a>
            <a class="menuitem" href="${pageContext.request.contextPath}/app/about">
                <div><fmt:message key="about"/></div>
            </a>
            <div class="dropdown">
                <img class="profile-icon" src="${pageContext.request.contextPath}/images/profile.png"
                     alt="profile icon">
                <div class="dropdown-content">
                    <c:if test="${user.accessLevel == 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin"><fmt:message key="admin"/></a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/app/profile"><fmt:message key="profile"/></a>
                    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="log-out"/></a>
                </div>
            </div>
        </div>
        <div class="container">
            <p class="container-title"><fmt:message key="my-quizzes"/></p>
            <div class="flex-container">
                <c:choose>
                    <c:when test="${userQuizzes.size() == 0}">
                        <p class="zero">You have not quizzes...</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="quiz" items="${userQuizzes}">
                            <a class="container-element" style="text-decoration: none"
                               href="${pageContext.request.contextPath}/app/quiz?id=${quiz.getId()}">
                                <div>
                                    <p class="quiz-title"><c:out value="${quiz.getName()}"/></p>
                                    <p class="quiz-difficulty"><c:out value="${quiz.getDifficulty()}"/></p>
                                    <p class="quiz-time"><c:out value="${quiz.getTime()} min"/></p>
                                </div>
                            </a>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</fmt:bundle>
</body>
</html>
