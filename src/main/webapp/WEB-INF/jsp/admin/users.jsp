<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="list-users"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin.css">
    </head>
    <body>
    <div class="users-list">
        <table class="iksweb">
            <caption class="title-list">List of Users</caption>
            <tbody>
            <tr>
                <th id="login"><fmt:message key="login"/></th>
                <th id="email"><fmt:message key="email"/></th>
                <th id="access-level"><fmt:message key="access-level"/></th>
                <th id="actions" colspan="3"><fmt:message key="actions"/></th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.accessLevel}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/edit-user?id=${user.id}"><fmt:message
                                key="edit"/></a>
                    </td>
                    <c:choose>
                        <c:when test="${user.accessLevel == 'banned'}">
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/unblock-user?id=${user.id}"><fmt:message
                                        key="unblock"/></a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/block-user?id=${user.id}"><fmt:message
                                        key="block"/></a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/delete-user?id=${user.id}"><fmt:message
                                key="delete"/></a>
                    </td>
                </tr>
            </c:forEach>
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
                        <a href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">&laquo;</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link disabled" href="#">&laquo;</a>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="${startPage}" end="${requestScope.endPage}" var="i">
                    <c:choose>
                        <c:when test="${currentPage == i}">
                            <a class="active" href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${currentPage != numberOfPages}">
                        <a href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">&raquo;</a>
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