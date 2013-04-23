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
 */

public class ZmianaStanu implements model.ApplicationInterface {
    
    
     private ResultSet resultSet;
    
    public void ZmianaStatusu(){
        
        this.resultSet = null;
    }
 
@Override
    public String getTitle(Employee employee) {
        
    return "Zmiana Statusu przesylki";
    }

    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {
       
        xmlGenerator.printStartTag("form", "actoin","","method","POST");
        
        xmlGenerator.println("Numer Przesyłki");
        xmlGenerator.printEmptyElement("input", "name","kod_nadawcy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.println("Obecny Status Przesyłki");
        xmlGenerator.printEmptyElement("input", "name","kod_odbiorcy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Zatwierdź");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEndTag();
    }
    
    
}


