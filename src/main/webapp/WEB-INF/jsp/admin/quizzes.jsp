<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="list-quizzes"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin.css">
    </head>
    <body>
    <div class="quizzes-list">
        <table class="iksweb">
            <caption class="title-list"><fmt:message key="list-quizzes"/></caption>
            <tbody style="text-align: center">
            <tr>
                <th id="name"><fmt:message key="name"/></th>
                <th id="time"><fmt:message key="time"/></th>
                <th id="difficulty"><fmt:message key="difficulty"/></th>
                <th id="subject"><fmt:message key="subject"/></th>
                <th id="questions"><fmt:message key="questions"/></th>
                <th id="actions" colspan="2"><fmt:message key="actions"/></th>
            </tr>
            <c:forEach var="quiz" items="${quizzes}">
                <tr>
                    <td><c:out value="${quiz.name}"/></td>
                    <td><c:out value="${quiz.time}"/></td>
                    <td><c:out value="${quiz.difficulty}"/></td>
                    <td><c:out value="${quiz.subject}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/quizzes/questions?id=${quiz.id}&page=${currentPage}"><fmt:message key="list-questions"/></a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/edit-quiz?id=${quiz.id}&page=${currentPage}"><fmt:message key="edit"/></a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/delete-quiz?id=${quiz.id}&page=${currentPage}"><fmt:message key="delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7" style="text-align: center">
                    <a href="${pageContext.request.contextPath}/admin/add-quiz"><fmt:message key="add-quiz"/></a>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="btns">
            <c:choose>
                <c:when test="${currentPage gt 1}">
                    <a class="element btn"
                       href="${pageContext.request.contextPath}/admin/quizzes?page=${currentPage - 1}"><fmt:message key="back"/></a>
                </c:when>
                <c:otherwise>
                    <a class="element btn disabled"><fmt:message key="back"/></a>
                </c:otherwise>
            </c:choose>
            <p class="element"><c:out value="${currentPage}"/></p>
            <c:choose>
                <c:when test="${currentPage lt numberOfPages}">
                    <a class="element btn"
                       href="${pageContext.request.contextPath}/admin/quizzes?page=${currentPage + 1}"><fmt:message key="next"/></a>
                </c:when>
                <c:otherwise>
                    <a class="element btn disabled"><fmt:message key="next"/></a>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="btns">
            <a class="btn" href="${pageContext.request.contextPath}/admin"><fmt:message key="admin"/></a>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>