<%-- 
    Document   : buscarVuelo
    Created on : 24-sep-2014, 16:29:58
    Author     : Adrian
--%>

<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Vuelo</title>
        <style type="text/css">
            select {
                margin-left: 2px;
                width: 174px;
            }
        </style>
    </head>
    <body>
         <% 
            Class.forName("org.sqlite.JDBC");
            
            ArrayList<String> companys = new ArrayList();
            ArrayList<String> ciudad_origen = new ArrayList();
            ArrayList<String> ciudad_destino = new ArrayList();
            
            Connection connection = null;    
            
            try {         
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Lab2\\web\\WEB-INF\\database.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.executeUpdate("create table if not exists vuelos (id_vuelo integer primary key autoincrement, num_vuelo string,companyia string, origen string, hora_salida string, destino string, hora_llegada string)");

                ResultSet rs = statement.executeQuery("select distinct companyia from vuelos");                   
                while(rs.next()) {
                    companys.add(rs.getString("companyia"));
                }               
                rs = statement.executeQuery("select distinct origen from vuelos");                 
                while(rs.next()) {
                    ciudad_origen.add(rs.getString("origen"));
                }                
                rs = statement.executeQuery("select distinct destino from vuelos");                
                while(rs.next()) {
                    ciudad_destino.add(rs.getString("destino"));
                }
            }catch(Exception e) {
                System.err.println(e.getMessage());
                
                request.setAttribute("errorType","database");
                request.setAttribute("goto","menu");
               
                RequestDispatcher rd = request.getRequestDispatcher("error");
                rd.forward(request,response);
            }
            finally {
                connection.close();
            }
            

        %>                    
        <h1> Buscar Vuelo </h1>
        <form action="buscarVuelo" method="post">
            <table>
                <tr><td>Número de vuelo: </td><td><input type="text" name="num"/></td></tr>
                <tr><td>Compañía: </td><td><select name="compania" placeholder="Introduzca compañia">
                        <option value="" disabled selected> Seleccione una opción </option>
                        <% 
                        for(String item : companys) {
                            out.println("<option value='"+item+"'>"+item+"</option>");
                        }
                        %>
                        </select></td></tr>
                <tr><td>Ciudad de origen: </td><td><select name="origen">      
                        <option value="" disabled selected> Seleccione una opción </option>
                        <% 
                        for(String item : ciudad_origen) {
                            out.println("<option value='"+item+"'>"+item+"</option>");
                        }
                        %>
                        </select></td></tr>
                <tr><td>Ciudad de destino: </td><td><select name="destino">
                        <option value="" disabled selected> Seleccione una opción </option>
                        <% 
                        for(String item : ciudad_destino) {
                            out.println("<option value='"+item+"'>"+item+"</option>");
                        }
                        %>
                        </select></td></tr>
            </table>  
                        
            <input type="submit" value="Buscar"/>
        </form>
    </body>
</html>
