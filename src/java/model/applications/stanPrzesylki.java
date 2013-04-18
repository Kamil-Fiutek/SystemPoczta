package model.applications;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Adrian Scheit
 */
public class stanPrzesylki implements model.ApplicationInterface {
    
    @Override
    public String getTitle(Employee employee) {
        return "Sprawdź stan przesyłki";
    }
    
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {
        xmlGenerator.println("Narazie to jest cegłą.");
    }
}
