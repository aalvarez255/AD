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
 * @author ToniD
 */
@WebServlet(urlPatterns = {"/buscarHotel"})
public class buscarHotel extends HttpServlet {

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

        String name = request.getParameter("nombre"); // en la DB: nom_hotel
        String chain = request.getParameter("cad_hoteles"); // en la DB: cadena
        String city = request.getParameter("ciu_hotel"); // en la DB: ciudad

        Boolean search = true;
        if (name.equals("") && chain == null && city == null) {
            search = false;
        }

        if (search) {
            Connection connection = null;
            try {
                // create a database connection
                //if the database doesn't exists, it will be created
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\adrian\\Documents\\NetBeansProjects\\AD\\web\\WEB-INF\\database.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                //create users table if not exists (otherwise the select query crashes)
                statement.executeUpdate("create table if not exists hoteles (id_hotel integer primary key autoincrement, nom_hotel string,cadena string, num_hab integer, calle string, numero integer, codigo_postal string, ciudad string, provincia string, pais string)");
                ResultSet rs;

                if (!name.equals("") && chain == null && city == null) {
                    rs = statement.executeQuery("select * from hoteles where nom_hotel='" + name + "'");
                } else if (name.equals("") && !(chain == null) && city == null) {
                    rs = statement.executeQuery("select * from hoteles where cadena='" + chain + "'");
                } else if (name.equals("") && chain == null && !(city == null)) {
                    rs = statement.executeQuery("select * from hoteles where ciudad='" + city + "'");
                } else if (name.equals("") && !(chain == null) && !(city == null)) {
                    rs = statement.executeQuery("select * from hoteles where ciudad='" + city + "' and cadena='" + chain + "'");
                } else if (!name.equals("") && !(chain == null) && city == null) {
                    rs = statement.executeQuery("select * from hoteles where nom_hotel='" + name + "' and cadena='" + chain + "'");
                } else if (!name.equals("") && chain == null && !(city == null)) {
                    rs = statement.executeQuery("select * from hoteles where nom_hotel='" + name + "' and ciudad='" + city + "'");
                } else {
                    rs = statement.executeQuery("select * from hoteles where nom_hotel='" + name + "' and cadena='" + chain + "' and ciudad='" + city + "'");
                }

                Boolean empty = true;
               
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Resultado</title>");
                    out.println("<link rel='stylesheet' type='text/css' href='css/buscarhotel.css'>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div id='container'>");
                    out.println("<h1>Resultado de la búsqueda</h1>");
                    out.println("<table cellpadding='5'>");
                    out.println("<tr><th>Nombre del hotel</th><th>Cadena</th><th>Número de habitaciones</th><th>Calle</th><th>Número</th><th>Código postal</th><th>Ciudad</th><th>Provincia</th><th>País</th></tr>");
                    while (rs.next()) {
                        empty = false;
                        out.println("<tr><td>" + rs.getString("nom_hotel") + "</td><td>" + rs.getString("cadena") + "</td><td>" + rs.getString("num_hab") + "</td><td>" + rs.getString("calle") + "</td><td>" + rs.getString("numero") + "</td><td>" + rs.getString("codigo_postal") + "</td><td>" + rs.getString("ciudad") + "</td><td>" + rs.getString("provincia") + "</td><td>" + rs.getString("pais") + "</td></tr>");
                    }
                    out.println("</table>");
                    if (empty) out.println("<p>No se han encontrado resultados</p>");
                    out.println("<form action='buscarHotel.jsp'>");
                    out.println("<input type='submit' value='Atrás'>");
                    out.println("</div>");
                    out.println("</form>");
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
        } else {
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Error</h1>");
                out.println("<p>No se han introducido datos de búsqueda</p>");
                out.println("<form action='buscarHotel.jsp'>");
                out.println("<input type='submit' value='Atrás'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
                System.err.println(e.getMessage());
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
