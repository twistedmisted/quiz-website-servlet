<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<html>
<head>
    <title>${quiz.name}</title>
    <style>
        * {
            margin: 0 auto 0 auto;
            text-align: left;
            box-sizing: border-box;
        }

        body {
            display: block;
            margin: 0;
            padding: 0;
            text-align: left;
            font: 12px Arial, Helvetica, sans-serif;
            font-size: 13px;
            color: #061C37;
            background-color: #21232e;
        }

        .container {
            margin: 300px auto;
            width: 500px;
            height: 260px;
            border: 2px solid black;
            background-color: #3b3f50;
        }

        .quiz-name {
            padding-top: 45px;
            font-size: 40px;
            font-weight: bold;
            color: white;
            text-align: center;
        }

        .quiz-properties {
            padding-top: 15px;
            font-size: 20px;
            color: white;
            text-align: center;
        }

        .btns {
            padding: 30px;
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

        .result {
            font-size: 20px;
            color: white;
            padding-top: 20px;
            text-align: center;
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
<fmt:bundle basename="application_lang">
    <div class="container">
        <p class="quiz-name">
            <c:out value="${quiz.name}"/>
        </p>
        <p class="quiz-properties">
            <fmt:message key="difficulty"/>: <c:out value="${quiz.difficulty}"/> | <fmt:message key="time"/>: <c:out value="${quiz.time}"/> min
        </p>
        <c:choose>
            <c:when test="${score >= 0}">
                <p class="result"><fmt:message key="your-score"/> <c:out value="${score}"/>%</p>
                <div class="btns">
                    <a class="btn" href="${pageContext.request.contextPath}/app/home">Back</a>
                </div>
            </c:when>
            <c:when test="${isEmpty == true}">
                <p class="result"><fmt:message key="quiz-empty"/></p>
                <div class="btns">
                    <a class="btn" href="${pageContext.request.contextPath}/app/home">Back</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="btns">
                    <a class="btn" href="${pageContext.request.contextPath}/app/home"><fmt:message key="back"/></a>
                    <a class="btn" href="${pageContext.request.contextPath}/app/start?quiz_id=${quiz.id}&question=1"><fmt:message key="start"/></a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="language">
        <a class="par" href="${pageContext.request.contextPath}?lang=en">
            <i><fmt:message key="language.en"/></i>
        </a>
        <a class="par" href="${pageContext.request.contextPath}?lang=ua">
            <i><fmt:message key="language.ua"/></i>
        </a>
    </div>
</fmt:bundle>
</body>
</html>
