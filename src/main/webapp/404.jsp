<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>
<h1>404 Page Not Found.</h1>
<br/>
<p><strong>Error code:</strong> ${pageContext.errorData.statusCode}</p>
<p><strong>Request URI:</strong> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
<br/>
</body>
</html>