
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <form method="post" action="LoginRegister">
            <input type="hidden" name="action" value="Register">
            <input type="hidden" name="PassAdmin" value="false">
            <input name="username" type="text" placeholder="Username" maxlength="20" required>
            <br>
            <input name="pass" type="password" placeholder="Password" maxlength="20" required>
            <br>
            <input name="cpass" type="password" placeholder="Confirm Password" maxlength="20" required>
            <br>
            <div id="error">
                <%
                    if (request.getAttribute("error") != null) {
                        out.println(request.getAttribute("error"));
                    }%>
            </div>
            Role:
            <select name="role">
                <option>Admin</option>  
                <option>User</option>
            </select>
            <br>
            <img alt="captcha" src="<%= request.getContextPath()%>/SimpleCaptcha"><br>
            <input type="text" name="captcha" required><br>
            <input type="submit" value="Register">
        </form>
        <a href="Login.jsp">Already have an account?</a>

    </body>
</html>
