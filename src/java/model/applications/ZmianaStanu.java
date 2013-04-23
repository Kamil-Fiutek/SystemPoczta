package model.applications;

import java.sql.ResultSet;
import java.util.Map;
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

        xmlGenerator.println("Obecny Status Przesyłki");
        xmlGenerator.printEmptyElement("input", "name", "kod_odbiorcy", "type", "text");
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Zatwierdź");
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printEndTag();
    }
}
