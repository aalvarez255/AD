/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
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
@WebServlet(urlPatterns = {"/altaHotel"})
public class altaHotel extends HttpServlet {

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
        Class.forName("org.sqlite.JDBC");
        request.setCharacterEncoding("UTF-8");
                
        String nombre = request.getParameter("nombre");
        String cadena = request.getParameter("cadena");
        int num_hab = Integer.parseInt(request.getParameter("num_hab"));
        String calle = request.getParameter("calle");
        int numero = Integer.parseInt(request.getParameter("numero"));
        String cp = request.getParameter("cp");
        String ciudad = request.getParameter("ciudad");
        String provincia = request.getParameter("provincia");
        String pais = request.getParameter("pais");
        
        Connection connection = null;
        try {          
            // create a database connection
            //if the database doesn't exists, it will be created
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Lab2\\web\\WEB-INF\\database.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            
            statement.executeUpdate("create table if not exists hoteles (id_hotel integer primary key autoincrement, nom_hotel string,cadena string, num_hab integer, calle string, numero integer, codigo_postal string, ciudad string, provincia string, pais string)");
            statement.executeUpdate("insert into hoteles (nom_hotel,cadena,num_hab,calle,numero,codigo_postal,ciudad,provincia,pais) values('"+nombre+"','"+cadena+"',"+num_hab+",'"+calle+"',"+numero+",'"+cp+"','"+ciudad+"','"+provincia+"','"+pais+"')");
           
        }catch(SQLException e){
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
        response.sendRedirect("menu.html");
     
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
