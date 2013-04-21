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
        
        xmlGenerator.println(parameterMap.keySet().toString());
        
        xmlGenerator.printStartTag("form", "actoin","","method","POST");
        
        xmlGenerator.println("Kod pocztowy nadawcy:");
        xmlGenerator.printEmptyElement("input", "neme","kodpocztowy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.println("Kod pocztowy odbiorcy:");
        xmlGenerator.printEmptyElement("input", "neme","kododbiorcy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        
        xmlGenerator.printEmptyElement("input", "neme","submit","type","submit","value","Dalej >");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEndTag(); // form
        
    }
    
}
