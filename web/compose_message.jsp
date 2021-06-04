<%--
  Created by IntelliJ IDEA.
  User: HitHellHound
  Date: 04.05.2021
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="/chat/send_message.do" method="post">
    Текст сообщения:
    <input type="text" name="message" style="width: 50%">
    <input type="submit" value="Отправить">
    <a href="/chat/logout.do" target="_top">Выйти из чата</a>
</form>
</body>
</html>
