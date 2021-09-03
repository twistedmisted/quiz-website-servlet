<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<html>
<head>
    <title>GmOwl</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet" type="text/css">
    <style>
        table.iksweb {
            margin-top: 100px;
            width: 960px;
            border-collapse: collapse;
            border-spacing: 0;
            height: auto;
        }

        table.iksweb, table.iksweb td, table.iksweb th {
            border: 1px solid #595959;
        }

        table.iksweb td, table.iksweb th {
            padding: 3px;
            width: 30px;
            height: 35px;
        }

        table.iksweb th {
            background: #347c99;
            color: #fff;
            font-weight: normal;
        }

        table.iksweb td {
            background: #3b3f50;
            color: white;
            font-size: 16px;
        }

        .personal-info {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            font-size: 26px;
            color: white;
            font-weight: bold;
        }

        .title-list {
            padding-bottom: 20px;
            font-size: 30px;
            font-weight: bold;
            text-align: center;
            color: white;
        }

        .btns {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .btn {
            background-color: #f44336;
            border: none;
            border-radius: 12px;
            color: white;
            padding: 10px 26px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }

        .btn:hover {
            box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
        }
    </style>
</head>
<body>
<fmt:bundle basename="application_lang">
    <div class="personal-info">
        <p class="login"><fmt:message key="login"/>: <c:out value="${user.login}"/></p>
        <p class="email"><fmt:message key="email"/>: <c:out value="${user.email}"/></p>
    </div>

    <div class="quizzes">
        <table class="iksweb">
            <caption class="title-list"><fmt:message key="list-quizzes"/></caption>
            <tbody>
            <tr>
                <th id="quiz-name"><fmt:message key="name"/></th>
                <th id="quiz-difficulty"><fmt:message key="difficulty"/></th>
                <th id="quiz-time"><fmt:message key="time"/></th>
                <th id="quiz-score"><fmt:message key="score"/></th>
            </tr>
            <c:forEach var="quiz" items="${quizzesWithScore}">
                <td><c:out value="${quiz.key.name}"/></td>
                <td><c:out value="${quiz.key.difficulty}"/></td>
                <td><c:out value="${quiz.key.time}"/></td>
                <td><c:out value="${quiz.value}"/></td>
            </c:forEach>
            </tbody>
        </table>
        <div class="btns">
            <a class="btn" href="${pageContext.request.contextPath}/app/home"><fmt:message key="home"/></a>
        </div>
    </div>
</fmt:bundle>
</body>
</html>
