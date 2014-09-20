<%-- 
    Document   : index
    Created on : 17-sep-2014, 10:34:13
    Author     : Adrian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>       
        <% out.println("<h2>Primera práctica aplicaciones web</h2>"); %>
        <% for(int i=0; i < 5; ++i) { %> <h3>Mensaje <% out.println(i); %> </h3> <% } %>
        <h1>Hello World!</h1>
    </body>
</html>
