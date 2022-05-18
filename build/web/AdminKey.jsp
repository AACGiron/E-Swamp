
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Key</title>
    </head>
    <body>
        <form method="post" action="LoginRegister">
            <input type="hidden" name="action" value="<%=request.getAttribute("direct")%>">
            <input type="hidden" name="PassAdmin" value="true">
            <input type="text" size="20" name="key1" placeholder="Admin Key 1" required/>
            <input type="text" size="20" name="key2" placeholder="Admin Key 2" required/>
            <div id="error">
                <%
                    if (request.getAttribute("error") != null) {
                        out.println(request.getAttribute("error"));
                            }%>
            </div>
            <input type="submit" value="<%out.println(request.getAttribute("direct"));%>"/>
        </form>
    </body>
</html>
