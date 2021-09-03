<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="edit-user"/></title>

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
        <form name="edit-user" action="${pageContext.request.contextPath}/admin/edit-user?id=${id}&page=${page}"
              method="post">
            <div class="element">
                <label for="email"><fmt:message key="email"/></label>
                <input type="text" name="email" id="email" value="${user.email}">
            </div>

            <div class="element">
                <label for="login"><fmt:message key="login"/></label>
                <input type="text" name="login" id="login" value="${user.login}">
            </div>

            <div class="element">
                <label for="access-level"><fmt:message key="access-level"/></label>
                <input type="text" name="access-level" id="access-level" value="${user.accessLevel}">
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