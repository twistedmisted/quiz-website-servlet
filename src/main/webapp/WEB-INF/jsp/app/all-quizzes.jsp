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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#sort-select').change(function () {
                var sortBy = $('#sort-select').val();
                window.location = "/app/all-quizzes?sortBy=" + sortBy;
            });
        });
    </script>
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
            <a class="menuitem active" href="${pageContext.request.contextPath}/app/all-quizzes">
                <div><fmt:message key="all-quizzes"/></div>
            </a>
            <a class="menuitem" href="${pageContext.request.contextPath}/app/my-quizzes">
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
            <div class="flex-header">
                <div class="header">
                    <p class="container-title"><fmt:message key="all-quizzes"/></p></div>
                <div class="sort">
                    <label class="label-select" for="sort-select"><fmt:message key="sort-by"/>:</label>
                    <select class="select" name="sortVal" id="sort-select">
                        <c:choose>
                            <c:when test="${sortBy == 'name'}">
                                <option value="name" selected><fmt:message key="name"/></option>
                                <option value="difficulty"><fmt:message key="difficulty"/></option>
                                <option value="questions"><fmt:message key="questions"/></option>
                            </c:when>
                            <c:when test="${sortBy == 'difficulty'}">
                                <option value="name"><fmt:message key="name"/></option>
                                <option value="difficulty" selected><fmt:message key="difficulty"/></option>
                                <option value="questions"><fmt:message key="questions"/></option>
                            </c:when>
                            <c:when test="${sortBy == 'questions'}">
                                <option value="name"><fmt:message key="name"/></option>
                                <option value="difficulty"><fmt:message key="difficulty"/></option>
                                <option value="questions" selected><fmt:message key="questions"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="name"><fmt:message key="name"/></option>
                                <option value="difficulty"><fmt:message key="difficulty"/></option>
                                <option value="questions"><fmt:message key="questions"/></option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="subject" items="${subjects}">
                            <c:choose>
                                <c:when test="${subject == sortBy}">
                                    <option value="${subject}" selected><c:out value="${subject}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${subject}"><c:out value="${subject}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="flex-container">
                <c:forEach var="quiz" items="${quizzes}">
                    <a class="container-element" style="text-decoration: none"
                       href="${pageContext.request.contextPath}/app/quiz?id=${quiz.getId()}">
                        <div>
                            <p class="quiz-title"><c:out value="${quiz.getName()}"/></p>
                            <p class="quiz-difficulty"><c:out value="${quiz.getDifficulty()}"/></p>
                            <p class="quiz-time"><c:out value="${quiz.getTime()} min"/></p>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="language">
        <a class="par" href="${pageContext.request.contextPath}?lang=en">
            <i><fmt:message key="language.en"/></i>
        </a>
        <a class="par" href="${pageContext.request.contextPath}?lang=ua">
            <i><fmt:message key="language.ua"/></i>
        </a>
    </div>
</fmt:bundle>
</body>
</html>
