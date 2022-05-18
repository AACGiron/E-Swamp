<%-- 
    Document   : test2
    Created on : May 17, 2022, 11:16:29 PM
    Author     : acer
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1" align="center">
            <tr>
                <th>product</th>
                <th>price</th>
                <th>username</th>
                <th>description</th>
            </tr>
        <%
                    ResultSet list = (ResultSet) request.getAttribute("list");
                    while (list.next()) {%>
            <tr>
                <td><%=list.getString("item")%></td>
                <td><%=list.getDouble("price")%></td>
                <td><%=list.getString("username")%></td>
                <td><%=list.getString("description")%></td>  
            </tr>	
            <%	}
            %>
        </table>
    </body>
</html>
