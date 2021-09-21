<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="application_lang">
    <!DOCTYPE html>
    <html>
    <head>
        <link href="${pageContext.request.contextPath}/styles/auth.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title><fmt:message key="register"/></title>

        <script>
            function validPass() {
                if (document.getElementById("p1").value.length !== 0 && document.getElementById("p2").value.length !== 0
                    && document.getElementById("p1").value !== document.getElementById("p2").value) {
                    document.getElementById("passHint1").innerHTML = "Passwords do not match";
                    document.getElementById("passHint2").innerHTML = "Passwords do not match";
                    document.getElementById("btn").disabled = true;
                } else if (document.getElementById("p1").value === document.getElementById("p2").value) {
                    document.getElementById("passHint1").innerHTML = "";
                    document.getElementById("passHint2").innerHTML = "";
                    document.getElementById("btn").disabled = false;
                }
            }

            function validLogin(str) {
                if (str.length < 2 || str.length > 30) {
                    document.getElementById("logHint").innerHTML = "Login must be from 2 to 30 characters";
                    document.getElementById("btn").disabled = true;
                } else {
                    document.getElementById("logHint").innerHTML = "";
                    document.getElementById("btn").disabled = false;
                }
            }
        </script>
    </head>
    <body>
    <div class="main reg">
        <p class="sign"><fmt:message key="register"/></p>
        <form name="form1" class="form1" action="${pageContext.request.contextPath}/registration" method="POST">
            <c:if test="${not empty param.error}">
                <p class="incr"><fmt:message key="error.register.input"/></p>
            </c:if>
            <p><span class="incr" id="emailHint"></span></p>
            <input class="un " type="email" placeholder="Email" name="email" id="email">
            <p><span class="incr" id="logHint"></span></p>
            <input class="un " type="text" placeholder="Username" name="login" id="login"
                   onkeyup="validLogin(this.value)">
            <p><span id="passHint1" class="incr"></span></p>
            <input id="p1" class="pass" type="password" placeholder="Password" name="password"
                   onkeyup="validPass()">
            <p><span id="passHint2" class="incr"></span></p>
            <input id="p2" class="pass" type="password" placeholder="Password" name="password_confirm"
                   onkeyup="validPass()">
            <button id="btn" class="submit" type="submit"><fmt:message key="register"/></button>
            <p class="forgot">
                <a href="${pageContext.request.contextPath}/login"><fmt:message key="have-account"/></a>
            </p>
            <div class="language">
                <a class="par" href="${pageContext.request.contextPath}?lang=en">
                    <i>EN</i>
                </a>
                <a class="par" href="${pageContext.request.contextPath}?lang=ua">
                    <i>UA</i>
                </a>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>