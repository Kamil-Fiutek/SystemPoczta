
package model.applications;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;
/**
 *
 * @author Kamil Fiutek
 * @version  0.01
 */
public class DodawaniePlacowek implements model.ApplicationInterface{
    /**
 * 
 * @param employee
 * @return 
 */
    @Override
    public String getTitle(Employee employee) {
        
        return "Dodanie Placówki";
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
        xmlGenerator.println("Jeszcze cegła");
    }
}
