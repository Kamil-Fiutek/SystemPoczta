package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;

/**
 * Główny servlet systemu. Odpowiada za połączenie z bazą danych i udostępnienie
 * wszystkich aplikacji systemu.
 *
 * @author Adrian Scheit
 * @version 1.0
 */
public class Servlet extends HttpServlet {
    
    model.ApplicationInterface[] apps = {
        new model.applications.logowanie(),
        new model.applications.stanPrzesylki()};

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

        // Identyfikacja użytkownika:

        HttpSession httpSession = request.getSession();
        
        Integer id = (Integer) httpSession.getAttribute("id");
        if (id == null) {
            id = new Integer(0);
            httpSession.setAttribute("id", id);
        }
        model.Employee employee = new Employee(id);

        // Wybór aplikacji:

        Integer wI = (Integer) httpSession.getAttribute("app");
        if (wI == null) {
            wI = new Integer(0);// Default application!
            httpSession.setAttribute("app", wI);
        }
        
        try {
            wI = Integer.parseInt(request.getParameter("app")); // Jeśli się uda to nadpisuje. Jesto ok.
        } catch (NumberFormatException e) {
        } catch (NullPointerException e) {
        }
        if (wI < 0 || wI >= apps.length || apps[wI] == null) {
            wI = 0; // Default application - musi być prawidłowy
        }

        // Generowanie odpowiedzi:

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
        xmlGenerator.printStartTag("ul");
        for (int i = 0; i < apps.length; ++i) {
            if (apps[i] != null && apps[i].getTitle(employee) != null) {
                if (i == wI) {
                    xmlGenerator.printStartTag("b");
                }
                xmlGenerator.printStartTag("ul");
                xmlGenerator.printElement("a", apps[i].getTitle(employee), "href","?app="+i);
                xmlGenerator.printEndTag();
                if (i == wI) {
                    xmlGenerator.printEndTag();
                }
            }
        }
        xmlGenerator.printEndTag();// ul

        xmlGenerator.printEndTag();// leftDiv
        xmlGenerator.printStartTag("div", "id", "centerDiv");// div centerDiv

        if (employee.getRight(wI)) {
            apps[wI.intValue()].printApplication(employee, xmlGenerator, request.getParameterMap(), httpSession);
        } else {
            xmlGenerator.println("Brak uprawnień do wykonania tej aplikacji!");
        }
        
        xmlGenerator.printEndTag();// centerDiv
        xmlGenerator.printElement("div", "", "style", "clear:both;");
        xmlGenerator.printStartTag("div", "id", "footer");
        xmlGenerator.printStartTag("center");
        xmlGenerator.println("Strona wyprodukowana w roku 2013");
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
