/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrian
 */
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");   
        
                
        String user = request.getParameter("usuario");
        String password = request.getParameter("password");        
        
        boolean found = false;
        
        Connection connection = null;
        try {          
            // create a database connection
            //if the database doesn't exists, it will be created
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Lab2\\web\\WEB-INF\\database.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //create users table if not exists (otherwise the select query crashes)
            statement.executeUpdate("create table if not exists usuarios (id_usuario string primary key, password string)");
            ResultSet rs = statement.executeQuery("select * from usuarios where id_usuario='"+user+"' and password='"+password+"'");

            while(rs.next())
            {
              found = true;           
            }          
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            request.setAttribute("errorType","database");
            RequestDispatcher rd = request.getRequestDispatcher("error");
            rd.forward(request,response);
        }   
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                request.setAttribute("errorType","database");
                RequestDispatcher rd = request.getRequestDispatcher("error");
                rd.forward(request,response);
            }
        }  
        if (found) {
            response.sendRedirect("menu.html");
        }
        else {
            request.setAttribute("errorType","login");
            RequestDispatcher rd = request.getRequestDispatcher("error");
            rd.forward(request,response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        }
        catch (java.lang.ClassNotFoundException c) {
            System.err.println (c.getMessage());
        } 
    }
        
        

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        }
        catch (java.lang.ClassNotFoundException c) {
            System.err.println (c.getMessage());
        }  
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
