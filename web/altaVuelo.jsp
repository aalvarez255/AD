<%-- 
    Document   : altaVuelo
    Created on : 04-oct-2014, 0:19:24
    Author     : Adrian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Alta Vuelo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style type="text/css">
            table td input {
                float: right;
            }

            body {
                background-image: url("images/background.jpg");
                background-repeat: no-repeat;
                background-attachment: fixed;
                font-family: Arial, Helvetica, sans-serif;
            }

            #container {
                padding: 20px;
                width: 330px;
                height: 340px;
                bottom: 0; left: 0; top: 0; right: 0;
                margin: auto;
                position: absolute;

                background-color: #f2f0e9;
                border-radius: 15px 15px 15px 15px;
                border-style: solid;
                border-color: #000;
                border-width: 2px;
            }
            #red {
                margin-top:3px;
                color: red;
                padding-bottom:5px;
            }
            
            #green {
                margin-top:3px;
                color: green;
                padding-bottom:5px;
            }
            #atras {
                float:right;
                margin-right:8px;
            }
            
            input[type=submit] {
                width:80px;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <h1>Alta Vuelo</h1>
            <form action="altaVuelo" method="post">
                <table>
                    <tr><td>Número de vuelo: </td><td><input type="text" name="num"/></td></tr>
                    <tr><td>Compañía: </td><td><input type="text" name="compania"/></td></tr>
                    <tr><td>Ciudad de origen: </td><td><input type="text" name="ciudad_origen"/></td></tr>
                    <tr><td>Hora salida: </td><td><input type="time" name="salida"/></td></tr>
                    <tr><td>Ciudad de destino: </td><td><input type="text" name="ciudad_destino"/></td></tr>
                    <tr><td>Hora llegada: </td><td><input type="time" name="llegada"/></td></tr>
                    <tr><td colspan="2"><input type="submit" value="Enviar"></td></tr>
                </table>
            </form>
            <form action='menu.html'>
                <table id="atras">
                    <tr><td colspan="2"><input type='submit' value='Atrás'></td></tr>
                </table>
            </form>
             <% 
                    String code = (String)request.getAttribute("msg");
                    String color = "";
                    String msg = "";

                    if(code != null) {                            
                        if (code.equals("0")) {                                
                            color = "red";
                            msg = "Todos los campos son obligatorios.";
                        }
                        else if (code.equals("1")) {
                            color = "green";
                            msg = "Vuelo registrado correctamente.";
                        }    
                    }
                    
                %>
                
            <p id="<%=color%>"><%=msg%></p>
        </div>
    </body>
</html>

