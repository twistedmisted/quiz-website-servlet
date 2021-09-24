<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="edit-quiz"/></title>

        <style>
            * {
                box-sizing: border-box;
            }

            body {
                margin: 0;
                display: block;
                padding: 0;
                text-align: left;
                font: 16px Arial, Helvetica, sans-serif;
                color: white;
                background-color: #21232e;
            }

            .container {
                margin: 300px auto;
                width: 500px;
                border: 2px solid black;
                background-color: #3b3f50;
            }

            .edit-form {
                display: flex;
                justify-content: center;
                width: 500px;
                height: 250px;
            }

            .btns {
                display: flex;
                justify-content: center;
            }

            .btn {
                margin: 10px;
                width: 500px;
                background-color: #4CAF50;
                border: none;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
            }

            .element {
                padding: 10px
            }

            .element input {
                margin: 0 auto;
            }

            .incr {
                display: flex;
                justify-content: center;
                font-size: 16px;
                color: red;
            }

            .language {
                display: flex;
                justify-content: center;
                width: 100px;
            }

            .par {
                padding: 0 10px 0 10px;
                font-size: 19px;
                color: white;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
    <div class="container">
        <c:if test="${param.error == 'true'}">
            <p class="incr"><fmt:message key="error.validation.quiz"/></p>
        </c:if>
        <form name="edit-quiz" action="${pageContext.request.contextPath}/admin/edit-quiz?id=${id}&page=<%=request.getParameter("page")%>"
              method="post">
            <div class="element">
                <label for="name"><fmt:message key="name"/></label>
                <input type="text" name="name" id="name" value="${quiz.name}">
            </div>

            <div class="element">
                <label for="time"><fmt:message key="time"/></label>
                <input type="text" name="time" id="time" value="${quiz.time}">
            </div>

            <div class="element">
                <label for="difficulty"><fmt:message key="difficulty"/></label>
                <select name="difficulty" id="difficulty">
                    <c:choose>
                        <c:when test="${quiz.difficulty == 'easy'}">
                            <option value="easy" selected><fmt:message key="easy"/></option>
                            <option value="normal"><fmt:message key="normal"/></option>
                            <option value="hard"><fmt:message key="hard"/></option>
                        </c:when>
                        <c:when test="${quiz.difficulty == 'normal'}">
                            <option value="easy"><fmt:message key="easy"/></option>
                            <option value="normal" selected><fmt:message key="normal"/></option>
                            <option value="hard"><fmt:message key="hard"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="easy"><fmt:message key="easy"/></option>
                            <option value="normal"><fmt:message key="normal"/></option>
                            <option value="hard" selected><fmt:message key="hard"/></option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="element">
                <label for="subject"><fmt:message key="subject"/></label>
                <input type="text" name="subject" id="subject" value="${quiz.subject}">
            </div>
            <div class="btns">
                <button class="btn" type="submit"><fmt:message key="confirm"/></button>
            </div>
            <div class="btns">
                <a class="btn" type="submit" href="${pageContext.request.contextPath}/admin/quizzes"><fmt:message key="back"/></a>
            </div>
        </form>
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