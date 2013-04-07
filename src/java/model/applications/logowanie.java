/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.applications;

import java.util.Map;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Adrian Scheit
 */
public class logowanie implements model.ApplicationInterface {

    @Override
    public String getTitle(Employee employee) {
        if (employee.isEmployee()) {
            return "Wyloguj!";
        } else {
            return "Zaloguj";
        }
    }

    @Override
    public void printApplication(Employee employee, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {
        xmlGenerator.println("Narazie to jest cagła.");

    }
}
