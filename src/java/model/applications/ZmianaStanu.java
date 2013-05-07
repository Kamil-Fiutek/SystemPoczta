package model.applications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Lukasz
 *
 * Klasa sluzaca do zmiany statusu przesylki
 */
public class ZmianaStanu implements model.ApplicationInterface {

    private ResultSet resultSet;

    public void ZmianaStatusu() {
    }

    /**
     * Metoda wyswietlajaca nazwe w oknie aplikacji
     *
     * @param employee
     * @return
     */
    @Override
    public String getTitle(Employee employee) {

        return "Zmiana Statusu przesylki";
    }

    /**
     *
     * @param employee
     * @param httpServletResponse
     * @param xmlGenerator
     * @param parameterMap
     * @param httpSession
     */
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {

        xmlGenerator.printStartTag("form", "actoin", "", "method", "POST");

        xmlGenerator.println("Numer Przesyłki");
        xmlGenerator.printEmptyElement("input", "name", "kod_nadawcy", "type", "text");
        xmlGenerator.printEmptyElement("br");


        xmlGenerator.printStartTag("label", "id", "nadawca_nazwa_kraju");
        xmlGenerator.println("Obecny Status Przesyłki:");
        xmlGenerator.printEndTag();
        getDataFromDB("select idStatusuPrzesylki, statusPrzesylki from statusyPrzesylek");
        printResultSetContent(xmlGenerator, "status_przesylki");////;;
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");


        xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Zatwierdź");
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printEndTag();
    }

    private void printResultSetContent(XMLGenerator xmlGenerator, String fieldName) {
        xmlGenerator.printStartTag("select", "name", fieldName);
        try {
            while (resultSet.next() == true) {
                xmlGenerator.printStartTag("option", "value", String.valueOf(resultSet.getObject(1)));
                xmlGenerator.println((String) resultSet.getObject(2));
                xmlGenerator.printEndTag(); // zakonczenie znacznika option
            }
        } catch (SQLException ex) {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        xmlGenerator.printEndTag(); // zakonczenie znacznika select
        try {
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getDataFromDB(String query) {
        Statement statement;
        try {
            statement = model.ConnectionSingleton.getConnection(null).createStatement();
            resultSet = statement.executeQuery(query);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
