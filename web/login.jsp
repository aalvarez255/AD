<%-- 
    Document   : login
    Created on : 02-oct-2014, 17:29:50
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
        <title>Login</title>
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

            #containerlogin {
                float:left;

                width: 300px;
                height: 175px;

                padding: 20px;
            }

            #container {
                display: inline-block;
                position:absolute;
                left: 35%;
                top: 35%;                

                background-color: #f2f0e9;
                border-radius: 15px 15px 15px 15px;
                border-style: solid;
                border-color: #000;
                border-width: 2px;
            }

            #containerReg {
                float:left;

                width: 300px;
                height: 175px;

                padding: 20px;

            }
            
            #red {
                color: red;
                padding-bottom:5px;
            }
            
            #green {
                color: green;
                padding-bottom:5px;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <div id="containerlogin">
                <h1>Login</h1>
                <form action="login" method="post">
                    <table>
                        <tr><td>Usuario: </td><td><input type="text" name="usuario"/></td></tr>
                        <tr><td>Contraseña: </td><td><input type="password" name="password"/></td></tr>
                        <tr><td colspan="2"><input type="submit" value="Entrar"/></td></tr>
                    </table>
                </form>
            </div>
            <div id="containerReg">
                <h1>Registro</h1>
                <form action="registro" method="post">
                    <table>
                        <tr><td>Usuario: </td><td><input type="text" name="usuario_reg"/></td></tr>
                        <tr><td>Contraseña: </td><td><input type="password" name="password_reg"/></td></tr>
                        <tr><td colspan="2"><input type="submit" value="Registrar"/></td></tr>
                        <% 
                            String code = (String)request.getAttribute("register");
                            String color = "";
                            String msg = "";
                            
                            if(code != null) {                            
                                if (code.equals("0")) {                                
                                    color = "red";
                                    msg = "El usuario ya existe.";
                                }
                                else if (code.equals("1")) {
                                    color = "green";
                                    msg = "Registrado correctamente.";
                                }    
                            }
                            %>
                        <tr><td colspan="2" id='<%= color %>'><%= msg %> </td></tr>
                    </table>
                </form>   
            </div>
        </div>
    </body>
</html>

