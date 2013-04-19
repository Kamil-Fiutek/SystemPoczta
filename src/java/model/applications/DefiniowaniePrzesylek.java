/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.applications;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Lukasz
 */
public class DefiniowaniePrzesylek implements model.ApplicationInterface{

    @Override
    public String getTitle(Employee employee) {
        return "Definiowanie przesyłek";
    }

    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {
        xmlGenerator.printElement("p", "Cegła", "nazwa","wartosc");
    }
    
}
