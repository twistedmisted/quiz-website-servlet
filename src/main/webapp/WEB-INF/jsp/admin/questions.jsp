<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="list-questions"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin.css">
    </head>
    <body>
    <div class="questions-list">
        <table class="iksweb">
            <caption class="title-list"><fmt:message key="list-questions"/></caption>
            <tbody>
            <tr>
                <th id="prompt"><fmt:message key="prompt"/></th>
                <th id="answers"><fmt:message key="answers"/></th>
                <th id="variants"><fmt:message key="variants"/></th>
                <th id="actions"><fmt:message key="actions"/></th>
            </tr>
            <c:forEach var="question" items="${questions}">
                <tr>
                    <td><c:out value="${question.getPrompt()}"/></td>
                    <td><c:out value="${question.getAnswers()}"/></td>
                    <td><c:out value="${question.showVariants()}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/delete-question?id=${question.id}&quiz_id=<%=request.getParameter("id")%>&page=<%=request.getParameter("page")%>"><fmt:message key="delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="4" style="text-align: center">
                    <a href="${pageContext.request.contextPath}/admin/add-question?id=<%=request.getParameter("id")%>">
                        <fmt:message key="add-question"/>
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="btns">
            <a class="btn" href="${pageContext.request.contextPath}/admin/quizzes"><fmt:message key="back"/></a>
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