<%-- 
    Document   : altaHotel
    Created on : 03-oct-2014, 23:59:26
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
        <title>Alta Hotel</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style type="text/css">
            select{
                width: 173px;
                margin-left: 2px;
            }

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
                height: 415px;
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
            #tabla_atras {
                float:right;
                margin-right:6px;
            }
            
            input[type=submit] {
                width:80px;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <h1>Alta Hotel</h1>
            <form action="altaHotel" method="post">
                <table>
                    <tr><td>Nombre del hotel: </td><td><input type="text" name="nombre"/></td></tr>
                    <tr><td>Cadena hotelera: </td><td><input type="text" name="cadena"/></td></tr>
                    <tr><td>Num. habitaciones: </td><td><input type="number" min="1" max="999" name="num_hab"/></td></tr>
                    <tr><td>Calle: </td><td><input type="text" name="calle"/></td></tr>
                    <tr><td>Número: </td><td><input type="number" min="1" max="999" name="numero"/></td></tr>
                    <tr><td>Código postal: </td><td><input type="text" name="cp"/></td></tr>
                    <tr><td>Ciudad: </td><td><input type="text" name="ciudad"/></td></tr>
                    <tr><td>Provincia: </td><td><input type="text" name="provincia"/></td></tr>
                    <tr><td>País: </td><td><input type="text" name="pais"/></td></tr>
                    <tr><td colspan="2"><input type="submit" value="Enviar"></td></tr>
                </table>
            </form>
            <form action='menu.html'>
                <table id="tabla_atras">
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
                            msg = "Hotel registrado correctamente.";
                        }    
                    }
                %>
                
            <p id="<%=color%>"><%=msg%></p>
        </div>
    </body>
</html>

