<%-- 
    Document   : buscarHotel
    Created on : 25-sep-2014, 11:45:57
    Author     : Toni
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
        <title>Buscar Hotel</title>
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
            
            ArrayList<String> nombre_hotel = new ArrayList();
            ArrayList<String> cad = new ArrayList();
            ArrayList<String> ciu = new ArrayList();
            
            Connection connection = null;    
            
            try {         
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Toni\\Documents\\NetBeansProjects\\AD\\web\\WEB-INF\\database.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.executeUpdate("create table if not exists hoteles (id_hotel integer primary key autoincrement, nom_hotel string, cadena string, num_hab string, calle string, numero string, codigo_postal string, ciudad string, provincia string, pais string)");

                ResultSet rs = statement.executeQuery("select distinct nom_hotel from hoteles");                   
                while(rs.next()) {
                    nombre_hotel.add(rs.getString("nom_hotel"));
                }               
                rs = statement.executeQuery("select distinct cadena from hoteles");                 
                while(rs.next()) {
                    cad.add(rs.getString("cadena"));
                }                
                rs = statement.executeQuery("select distinct ciudad from hoteles");                
                while(rs.next()) {
                    ciu.add(rs.getString("ciudad"));
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
        <h1> Buscar Hotel </h1>
        <form action="buscarHotel" method="post">
            <table>
                <tr><td>Nombre del hotel: </td><td><input type="text" name="nombre"/></td></tr>
                <tr><td>Cadena: </td><td><select name="cad_hoteles">      
                        <option value="" disabled selected> Seleccione una opción </option>
                        <% 
                        for(String item : cad) {
                            out.println("<option value='"+item+"'>"+item+"</option>");
                        }
                        %>
                        </select></td></tr>
                <tr><td>Ciudad: </td><td><select name="ciu_hotel">
                        <option value="" disabled selected> Seleccione una opción </option>
                        <% 
                        for(String item : ciu) {
                            out.println("<option value='"+item+"'>"+item+"</option>");
                        }
                        %>
                        </select></td></tr>
            </table>  
                        
            <input type="submit" value="Buscar"/>
        </form>
    </body>
</html>
