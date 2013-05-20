package model.applications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Adrian Scheit
 */
public class stanPrzesylki implements model.ApplicationInterface {
    //zapisujemy wyniki z bazy danych do resulset
    private ResultSet resultSet;
    //konstruktor klasy stanu przesylki
    public stanPrzesylki() {
        this.resultSet = null;
    }
    //pochodzi z interfejsu ApplicationInterface
    @Override
    public String getTitle(Employee employee) {
        return "Sprawdź stan przesyłki";
    }
    //pochodzi z interfejsu ApplicationInterface
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {
        generateShipmentStatusForm(xmlGenerator);
        
        if (parameterMap.get("Sprawdz") != null) {
            if (validateShipmentID(parameterMap, xmlGenerator) == true) {
                checkShipmentStatus();
                printShipmentStatus(xmlGenerator);
            }
        }
    }
    //metoda generujaca formularz
    private void generateShipmentStatusForm(XMLGenerator xmlGenerator) {
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("table", "");
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Poniżej proszę wprowadzić numer przesyłki");
        xmlGenerator.printEndTag();     // font
        xmlGenerator.printEndTag();     // td
        xmlGenerator.printEndTag();     // tr
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "text", "name", "idPrzesylki");
        xmlGenerator.printEndTag();     // td
        xmlGenerator.printEndTag();     // tr
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Sprawdź", "name", "Sprawdz");
        xmlGenerator.printEndTag();     // td
        xmlGenerator.printEndTag();     // tr
        
        xmlGenerator.printEndTag();     // table
        xmlGenerator.printEndTag();     // form
    }
    
    private boolean validateShipmentID(Map<String, String[]> parameterMap, XMLGenerator xmlGenerator) {
        String shipmentID = parameterMap.get("idPrzesylki")[0];
        
        if (!shipmentID.matches("[0-9]{10}")) {
            xmlGenerator.println("Nie ma takiego numeru w bazie");
            return false;
        }
        else {
            return true;
        }
    }
    //sprawdzenie statusu przesylki
    private void checkShipmentStatus() {
        resultSet = model.ConnectionSingleton.executeQuery("select h.czas, h.opis, s.statusPrzesylki from historiaPrzesylek h, statusyPrzesylek s where s.idStatusuPrzesylki = h.idStatusu");
    }
    
        // historia przesylki
        // plus sprawdzenie jak to paczka i jej cechy
    private void printShipmentStatus(XMLGenerator xmlGenerator) {
        try
        {
            resultSet.next();
            xmlGenerator.println("Data nadania: " + resultSet.getObject(1).toString());
            xmlGenerator.printEmptyElement("br");
            xmlGenerator.println("Opis: " + resultSet.getObject(2).toString());
            xmlGenerator.printEmptyElement("br");
            xmlGenerator.println("Status przesyłki: " + resultSet.getObject(3).toString());
           
            //póki co brak sprawdzenia typu przesyłki a z nim cech szczególnych 
            // bo nie ma jak polaczyc sensownie tabel
            //xmlGenerator.println("Typ przesylki: " + resultSet.getObject(1).toString());
            
        }
        catch (SQLException ex)
        {
            Logger.getLogger(stanPrzesylki.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

