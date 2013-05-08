package model.applications;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
* Panel logowania użytkowników.
*
* @author Adrian Scheit
* @version 0.1
*/
public class Logowanie implements model.ApplicationInterface {

    /**
* Zwraca tytuł w zależności od stanu zalogowania.
*
* @param employee Referencja na pracownika. Sprawdzane jest zalogowanie na
* jej podstawie.
* @return Tytuł, nigdy null.
*/
    @Override
    public String getTitle(Employee employee) {
        if (employee.isEmployee()) {
            return "Wyloguj " + employee.getFullName();
        } else {
            return "Zaloguj";
        }
    }

    /**
* Generuje formularz: do logowania, lub do wylogowania.
*
* @param employee
* @param httpServletResponse
* @param xmlGenerator
* @param parameterMap
* @param httpSession
*/
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {

        if (employee.isEmployee()) { // Wylogowywanie

            if (parameterMap.get("submit") != null && parameterMap.get("submit")[0].equals("Wyloguj")) {
                Integer sessionId = (Integer) httpSession.getAttribute("id");
                sessionId = 0;
                httpSession.setAttribute("id", sessionId);

                xmlGenerator.println("Wylogowanao!");
                try {
                    httpServletResponse.sendRedirect("");
                } catch (IOException e) {
                }
            } else {

                xmlGenerator.println("Witaj " + employee.getFullName() + "!");
                xmlGenerator.printEmptyElement("br");


                xmlGenerator.printStartTag("form", "action", "", "method", "POST");
                xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Wyloguj", "name", "submit");
                xmlGenerator.printEndTag(); // form

            }

        } else { // Logowanie

            if (parameterMap.get("username") != null && parameterMap.get("password") != null) {
                xmlGenerator.println("Trwa logowanie do systemu...");
                xmlGenerator.printEmptyElement("br");

                int username = 0;
                try {
                    username = Integer.parseInt(parameterMap.get("username")[0]);

                    Statement statement = model.ConnectionSingleton.getConnection(null).createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from pracownicy where idPracownika=" + username + " and haslo=SHA1('" + parameterMap.get("password")[0] + "')"); // TODO: tutaj można sobie zrobić SQL injection

                    if (resultSet.next()) {
                        employee = new Employee(username);

                        Integer sessionId = (Integer) httpSession.getAttribute("id");
                        sessionId = username;
                        httpSession.setAttribute("id", sessionId);

                        xmlGenerator.println("Zalogowano do systemu!");

                        httpServletResponse.sendRedirect("");
                    }

                } catch (NumberFormatException e) {
                    xmlGenerator.println(e.getMessage()); // TODO: do zakomentowania wszystkie wyświetlanie wyjątków
                } catch (ClassNotFoundException e) {
                    xmlGenerator.println(e.getMessage());
                } catch (SQLException e) {
                    xmlGenerator.println(e.getMessage());
                } catch (IOException e) {
                    xmlGenerator.println(e.getMessage());
                }
                if (!employee.isEmployee()) {
                    xmlGenerator.println("Błąd logowania!");
                }

            }
            if (!employee.isEmployee()) {

                xmlGenerator.printStartTag("form", "action", "", "method", "POST");

                xmlGenerator.printElement("label", "Numer identyfikacyjny pracownika:", "id", "username");
                xmlGenerator.printEmptyElement("input", "type", "text", "id", "username", "name", "username");
                xmlGenerator.printEmptyElement("br");
                xmlGenerator.printElement("label", "Hasło:", "id", "password");
                xmlGenerator.printEmptyElement("input", "type", "password", "id", "password", "name", "password");
                xmlGenerator.printEmptyElement("br");
                xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Zaloguj", "name", "submit");

                xmlGenerator.printEndTag(); // form
            }

        }
    }
}