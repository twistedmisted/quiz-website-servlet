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
        <c:choose>
            <c:when test="${currentPage gt 1}">
                <c:set var="startPage" scope="request" value="${currentPage -1}"/>
            </c:when>
            <c:otherwise>
                <c:set var="startPage" scope="request" value="${1}"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${numberOfPages gt currentPage+1}">
                <c:set var="endPage" scope="request" value="${currentPage +2}"/>
            </c:when>
            <c:otherwise>
                <c:set var="endPage" scope="request" value="${numberOfPages}"/>
            </c:otherwise>
        </c:choose>
        <div class="center">
            <div class="pagination">
                <c:choose>
                    <c:when test="${currentPage != 1}">
                        <a href="${pageContext.request.contextPath}/admin/quizzes?page=${currentPage - 1}">&laquo;</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link disabled" href="#">&laquo;</a>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="${startPage}" end="${requestScope.endPage}" var="i">
                    <c:choose>
                        <c:when test="${currentPage == i}">
                            <a class="active" href="${pageContext.request.contextPath}/admin/quizzes?page=${i}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/quizzes?page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${currentPage != numberOfPages}">
                        <a href="${pageContext.request.contextPath}/admin/quizzes?page=${currentPage + 1}">&raquo;</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link disabled" href="#">&raquo;</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="btns">
            <a class="btn" href="${pageContext.request.contextPath}/admin"><fmt:message key="admin"/></a>
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
    </body>
    </html>
</fmt:bundle>