<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="add-quiz"/></title>

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
        </style>
    </head>
    <body>
    <div class="container">
        <form name="edit-user" action="${pageContext.request.contextPath}/admin/add-quiz" method="post">
            <div class="element">
                <label for="name"><fmt:message key="name"/></label>
                <input type="text" name="name" id="name">
            </div>
            <div class="element">
                <label for="time"><fmt:message key="time"/> (<fmt:message key="time-measure"/>)</label>
                <input type="text" name="time" id="time">
            </div>
            <div class="element">
                <label for="difficulty"><fmt:message key="difficulty"/></label>
                <select name="difficulty" id="difficulty">
                    <option value="easy"><fmt:message key="easy"/></option>
                    <option value="medium"><fmt:message key="normal"/></option>
                    <option value="hard"><fmt:message key="hard"/></option>
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
                <a class="btn" type="submit" href="<%=request.getHeader("referer")%>"><fmt:message key="back"/></a>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>