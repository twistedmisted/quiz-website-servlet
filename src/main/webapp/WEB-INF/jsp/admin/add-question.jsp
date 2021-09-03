<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.final_project.dao.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="add-question"/></title>

        <style>
            * {
                box-sizing: border-box;
            }

            body {
                display: inline-block;
                margin: 0;
            }

            .add-question {
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
                background-color: #4CAF50; /* Green */
                border: none;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin-left: 65px;
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
    <div>
        <form name="add-question"
              action="${pageContext.request.contextPath}/admin/add-question?id=<%=request.getParameter("id")%>"
              method="post">

            <div class="element">
                <label for="prompt"><fmt:message key="prompt"/></label>
                <input type="text" name="prompt" id="prompt">
            </div>

            <p><fmt:message key="enter-variants"/></p>

            <div class="element">
                <input type="checkbox" id="a" name="a" value="a">
                <input type="text" name="a-input" id="a-input">
            </div>

            <div class="element">
                <input type="checkbox" id="b" name="b" value="b">
                <input type="text" name="b-input" id="b-input">
            </div>

            <div class="element">
                <input type="checkbox" id="c" name="c" value="c">
                <input type="text" name="c-input" id="c-input">
            </div>

            <div class="element">
                <input type="checkbox" id="d" name="d" value="d">
                <input type="text" name="d-input" id="d-input">
            </div>

            <button class="btn" type="submit"><fmt:message key="confirm"/></button>
        </form>
        <div class="btns">
            <a class="btn" type="submit" href="${pageContext.request.contextPath}/admin/quiz?id=<%=request.getParameter("id")%>"><fmt:message key="back"/></a>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>