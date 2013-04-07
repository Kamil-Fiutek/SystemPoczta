package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Główny servlet systemu. Odpowiada za połączenie z bazą danych i udostępnienie
 * wszystkich aplikacji systemu.
 *
 * @author Adrian Scheit
 * @version 1.0
 */
public class Servlet extends HttpServlet {

    /**
     * Tworzy połączenie z bazą danych. Jeśli napotkano wyjatek
     * ClassNotFoundException czy też SQLException to wyrzucany jest
     * ServletException.
     *
     * @throws ServletException Defines a general exception a servlet can throw
     * when it encounters difficulty.
     *
     */
    @Override
    public void init() throws ServletException {
        try {
            model.ConnectionSingleton.createConnection(this.getServletConfig());
        } catch (ClassNotFoundException e) {
            throw new ServletException(e.getMessage());
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    /**
     * Zamyka połączenie z bazą danych.
     */
    @Override
    public void destroy() {
        try {
            model.ConnectionSingleton.closeConnection();
        } catch (SQLException e) {
            // Ignorowanie ponieważ i tak nie można tego obsłużyć
        }
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        view.XMLGenerator xmlGenerator = new view.XMLGenerator(response.getWriter());
        view.HTMLGenerator htmlGenerator = new view.HTMLGenerator(xmlGenerator);
        
        htmlGenerator.printHTMLBegin("System Poczta");

        // Ciało strony:
        xmlGenerator.printStartTag("div", "id", "mainDiv");
        xmlGenerator.printStartTag("div", "id", "header");
        xmlGenerator.printEmptyElement("img", "alt", "banner", "src", "banner.png");
        xmlGenerator.printEndTag();// div header
        xmlGenerator.printStartTag("div", "id", "leftDiv");// div leftDiv
        htmlGenerator.printHTMLHeader(4, "Aplikacje:");

        // Lista aplikacji:
        
        xmlGenerator.printEndTag();// leftDiv
        xmlGenerator.printStartTag("div", "id", "centerDiv");// div centerDiv
        
        xmlGenerator.println("Zawartość główna. Aplikacja.");
        
        xmlGenerator.printEndTag();// centerDiv
        xmlGenerator.printElement("div", "", "style", "clear:both;");
        xmlGenerator.printStartTag("div", "id", "footer");
        xmlGenerator.printStartTag("center");
        xmlGenerator.println("2013");
        xmlGenerator.printEndTag();// center
        xmlGenerator.printEndTag();// div footer
        xmlGenerator.printEndTag();// mainDiv

        htmlGenerator.printHTMLEnd();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
