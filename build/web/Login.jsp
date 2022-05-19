<%-- 
    Document   : welcome
    Created on : May 9, 2022, 6:03:12 PM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form method="post" action="LoginRegister">
            <input type="hidden" name="action" value="Login">
            <input type="hidden" name="PassAdmin" value="false">
            <input name="username" type="text" size="20" placeholder="Username" required/>
            <br>
            <input name="pass" type="password" size="20" placeholder="Password" required/>
            <br>
            <div id="error">
                <%
                    if (request.getAttribute("error") != null) {
                        out.println(request.getAttribute("error"));
                    }%>
            </div>
            <img alt="captcha" src="<%= request.getContextPath()%>/SimpleCaptcha"><br>
            <input type="text" name="captcha" required><br>
            <input type="submit" value="Login"/>
        </form>
        <a href="Register.jsp">Don't have an account?</a>
    </body>
</html>
