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
                <th id="state"><fmt:message key="start"/></th>
                <th id="actions" colspan="3"><fmt:message key="actions"/></th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.accessLevel}"/></td>
                    <td><c:out value="${user.state}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/edit-user?id=${user.id}&page=${currentPage}"><fmt:message key="edit"/></a>
                    </td>
                    <c:choose>
                        <c:when test="${user.state == 'banned'}">
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/unblock-user?page=${currentPage}&id=${user.id}"><fmt:message key="unblock"/></a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/block-user?page=${currentPage}&id=${user.id}"><fmt:message key="block"/></a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/delete-user?page=${currentPage}&id=${user.id}"><fmt:message key="delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="btns">
            <c:choose>
                <c:when test="${currentPage gt 1}">
                    <a class="element btn"
                       href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}"><fmt:message key="back"/></a>
                </c:when>
                <c:otherwise>
                    <a class="element btn disabled"><fmt:message key="back"/></a>
                </c:otherwise>
            </c:choose>
            <p class="element"><c:out value="${currentPage}"/></p>
            <c:choose>
                <c:when test="${currentPage lt numberOfPages}">
                    <a class="element btn"
                       href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}"><fmt:message key="next"/></a>
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