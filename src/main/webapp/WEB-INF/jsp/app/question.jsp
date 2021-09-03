<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.Duration" %>
ы
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <html>
    <head>
        <title><fmt:message key="question"/> <%=request.getParameter("question")%>
        </title>

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
                border: 2px solid black;
                background-color: #3b3f50;
            }

            .prompt {
                padding-top: 20px;
                font-size: 21px;
                font-weight: bold;
                color: white;
                text-align: center;
            }

            .checkbox {
                margin: 25px 15px 0 15px;
            }

            .variants {
                font-size: 17px;
                color: white;
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

            .timer {
                display: flex;
                justify-content: center;
                margin-top: 10px;
                width: 100px;
                height: 25px;
                color: white;
                text-align: center;
                font-size: 20px;
                resize: none;
                background: #3b3f50;
                border: none;
            }
        </style>

        <script>
            <%
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime end = ((Date) session.getAttribute("quiz_finish"))
                                                                                .toInstant()
                                                                                .atZone(ZoneId.systemDefault())
                                                                                .toLocalDateTime();
                long seconds = Duration.between(now, end).getSeconds();
                int minutes = (int) (seconds / 60);
                seconds = seconds % 60;
            %>

            var min = <%=minutes%>;
            var sec = <%=seconds%>;
            var timeoutID;

            function timer() {
                sec--;
                document.question_form.timer.value = min + ':' + sec;

                if (sec === -1) {
                    sec = 59;
                    min--;
                    document.question_form.timer.value = min + ':' + sec;
                }
                timeoutID = window.setTimeout("timer()", 1000);

                if (sec === 0 && min === 0) {
                    document.question_form.timer.value = "0:0";
                    window.clearTimeout(timeoutID);
                }
            }
        </script>
    </head>
    <body>
    <div class="container">
        <form name="question_form"
              action="${pageContext.request.contextPath}/app/start?quiz_id=${quiz_id}&question=<%=Integer.parseInt(request.getParameter("question")) + 1%>"
              method="post">
            <textarea name="timer" class="timer"></textarea>
            <p class="prompt">
                <c:out value="${question.prompt}"/>
            </p>
            <%int i = 0;%>
            <c:forEach var="variant" items="${question.variants}">
                <%char letter = (char) ('a' + i++);%>
                <div>
                    <input class="checkbox" type="checkbox" name="<%=letter%>" value="<%=letter%>">
                    <label for="<%=letter%>" class="variants"><c:out value="${variant}"/></label>
                </div>
            </c:forEach>
            <div class="btns">
                <button class="btn" type="submit"><fmt:message key="next"/></button>
            </div>
        </form>
        <script>
            timer();
        </script>
    </div>
    </body>
    </html>
</fmt:bundle>