<%-- 
    Document   : Shop
    Created on : May 18, 2022, 9:35:33 PM
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
        <form name="page" method="post" action="MainStore">
            <select name="PageNum" onchange="document.page.submit()">
                <%
                    int pages = ((Number) request.getAttribute("pages")).intValue();
                    int current = ((Number) request.getAttribute("current")).intValue();
                    int i = 1;
                %>
                <option>Current: <%=current%></option>    
                <%
                    while (i - 1 < pages) {
                %>

                <option><%=i%></option>    
                <%
                        i++;
                    }
                %>
            </select>
        </form>
    </body>
</html>
