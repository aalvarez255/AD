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
@WebServlet(urlPatterns = {"/buscarVuelo"})
public class buscarVuelo extends HttpServlet {

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

        String num = request.getParameter("num");
        String compania = request.getParameter("compania");
        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");

        if (num.equals("") && compania == null && origen == null && destino == null) {
            request.setAttribute("msg","0");

            RequestDispatcher rd = request.getRequestDispatcher("buscarVuelo.jsp");
            rd.forward(request,response); 
        }

        else {
            Connection connection = null;
            try {
                // create a database connection
                //if the database doesn't exists, it will be created
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Adrian\\Documents\\NetBeansProjects\\AD\\web\\WEB-INF\\database.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                //create users table if not exists (otherwise the select query crashes)
                statement.executeUpdate("create table if not exists vuelos (id_vuelo integer primary key autoincrement, num_vuelo string,companyia string, origen string, hora_salida string, destino string, hora_llegada string)");

                ResultSet rs;

                if (!num.equals("") && (compania == null) && (origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "'");
                } else if (num.equals("") && !(compania == null) && (origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where companyia='" + compania + "'");
                } else if (num.equals("") && (compania == null) && !(origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where origen='" + origen + "'");
                } else if (num.equals("") && (compania == null) && (origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where destino='" + destino + "'");
                } else if (num.equals("") && (compania == null) && !(origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where origen='" + origen + "' and destino='" + destino + "'");
                } else if (num.equals("") && !(compania == null) && (origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where companyia='" + compania + "' and destino='" + destino + "'");
                } else if (num.equals("") && !(compania == null) && !(origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where companyia='" + compania + "' and origen='" + origen + "'");
                } else if (num.equals("") && !(compania == null) && !(origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where companyia='" + compania + "' and origen='" + origen + "' and destino='" + destino + "'");
                } else if (!num.equals("") && (compania == null) && (origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and destino='" + destino + "'");
                } else if (!num.equals("") && (compania == null) && !(origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and origen='" + origen + "'");
                } else if (!num.equals("") && (compania == null) && !(origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and origen='" + origen + "' and destino='" + destino + "'");
                } else if (!num.equals("") && !(compania == null) && (origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and companyia='" + compania + "'");
                } else if (!num.equals("") && !(compania == null) && (origen == null) && !(destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and companyia='" + compania + "' and destino='" + destino + "'");
                } else if (!num.equals("") && !(compania == null) && !(origen == null) && (destino == null)) {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and companyia='" + compania + "' and origen='" + origen + "'");
                } else {
                    rs = statement.executeQuery("select * from vuelos where num_vuelo='" + num + "' and companyia='" + compania + "' and origen='" + origen + "' and destino='" + destino + "'");
                }

                Boolean empty = true;
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Resultado</title>");
                    out.println("<link rel='stylesheet' type='text/css' href='css/buscarVuelo.css'>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div id='container'>");
                    out.println("<h1>Resultado de la búsqueda</h1>");
                    out.println("<table cellpadding='5'>");
                    out.println("<tr><th>Número de vuelo</th><th>Compañía</th><th>Ciudad de origen</th><th>Hora salida</th><th>Ciudad de destino</th><th>Hora llegada</th></tr>");
                    while (rs.next()) {
                        empty = false;
                        out.println("<tr><td>" + rs.getString("num_vuelo") + "</td><td>" + rs.getString("companyia") + "</td><td>" + rs.getString("origen") + "</td><td>" + rs.getString("hora_salida") + "</td><td>" + rs.getString("destino") + "</td><td>" + rs.getString("hora_llegada") + "</td></tr>");
                    }
                    out.println("</table>");
                    if (empty) {
                        out.println("<p>No se han encontrado resultados</p>");
                    }
                    out.println("<form action='buscarVuelo.jsp'>");
                    out.println("<input type='submit' value='Atrás'>");
                   
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                request.setAttribute("errorType", "database");
                request.setAttribute("goto", "menu");

                RequestDispatcher rd = request.getRequestDispatcher("error");
                rd.forward(request, response);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // connection close failed.
                    System.err.println(e.getMessage());
                    request.setAttribute("errorType", "database");
                    request.setAttribute("goto", "menu");

                    RequestDispatcher rd = request.getRequestDispatcher("error");
                    rd.forward(request, response);
                }
            }
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
        } catch (java.lang.ClassNotFoundException c) {
            System.err.println(c.getMessage());
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
        } catch (java.lang.ClassNotFoundException c) {
            System.err.println(c.getMessage());
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
