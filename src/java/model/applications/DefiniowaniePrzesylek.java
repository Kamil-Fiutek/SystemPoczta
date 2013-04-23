/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class DefiniowaniePrzesylek implements model.ApplicationInterface{

    private ResultSet resultSet;
    
    public DefiniowaniePrzesylek()
    {
        this.resultSet = null;
    }
    
    @Override
    public String getTitle(Employee employee) {
        return "Definiowanie przesyłek";
    }

    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {      
        
        xmlGenerator.printStartTag("form", "actoin","","method","POST");
        
        xmlGenerator.println("Kod pocztowy nadawcy:");
        xmlGenerator.printEmptyElement("input", "name","kod_nadawcy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.println("Kod pocztowy odbiorcy:");
        xmlGenerator.printEmptyElement("input", "name","kod_odbiorcy","type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.println("Nazwa kraju nadawcy:");
        getDataFromDB("select idKraju, kraj from kraje");
        printResultSetContent(xmlGenerator, "kraj_nadawcy");
        
        xmlGenerator.println("Nazwa kraju odbiorcy:");
        getDataFromDB("select idKraju, kraj from kraje");
        printResultSetContent(xmlGenerator, "kraj_odbiorcy");
        
        xmlGenerator.println("Typ przesyłki:");
        getDataFromDB("select idTypu, typ from typyPrzesylek");
        printResultSetContent(xmlGenerator, "typ_przesylki");
        
        xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Dalej >");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("Testowe formularze");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("List priorytetowy:");
        xmlGenerator.printStartTag("select", "name", "list_priorytet");
	xmlGenerator.printStartTag("option", "value", "Tak");
        xmlGenerator.println("Tak");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");
        xmlGenerator.println("Nie");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
	xmlGenerator.println("Masa listu:");
        xmlGenerator.printEmptyElement("input", "name", "masa_listu", "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Wyślij list");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("Paczka priorytetowa:");
        xmlGenerator.printStartTag("select", "name", "paczka_priorytet");
	xmlGenerator.printStartTag("option", "value", "Tak");
        xmlGenerator.println("Tak");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");
        xmlGenerator.println("Nie");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("Gabaryt paczki:");
        xmlGenerator.printStartTag("select", "name", "paczka_gabaryt");
	xmlGenerator.printStartTag("option", "value", "");
        xmlGenerator.println("Do pobrania z BD");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "");
        xmlGenerator.println("Do pobrania z BD");   // wywołać getDataFromDB a potem wyswietlic resultset
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
	xmlGenerator.println("Masa paczki:");
        xmlGenerator.printEmptyElement("input", "name", "masa_listu", "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Wyślij list");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("Kwota przekazu:");
        xmlGenerator.printEmptyElement("input", "name", "masa_listu", "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "submit", "type", "submit", "value", "Wyślij przekaz");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEndTag(); // form
    }

    /**
     * Pobiera dane z bazy danych określone zapytaniem przekazanym 
     * w argumencie metody.
     * W pierwszej kolejności pobieramy połączenie do bazy danych, a następnie
     * tworzymy zapytanie. Wynik zapytania zostaje zapisany do prywatnej referencji ResultSet.
     * @param query Zapytanie SQL kierowane do bazy danych.
     */
    private void getDataFromDB(String query)
    {
        Statement statement;
        try 
        {
            statement = model.ConnectionSingleton.getConnection(null).createStatement();
            resultSet = statement.executeQuery(query);
        } 
        catch (ClassNotFoundException ex) 
        { 
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Drukuje w liście rozwijanej zawartość ResultSet'u.
     * Metoda drukuje zawartość ResultSet'u, który został wcześniej pobrany z 
     * bazy danych.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu 
     * generowane są odpowiedzi dla klienta.
     * @param fieldName Nazwa listy rozwijanej
     */
    private void printResultSetContent(XMLGenerator xmlGenerator, String fieldName)
    {
        xmlGenerator.printStartTag("select", "name", fieldName);
        try 
        {
            while (resultSet.next() == true)
            {
                xmlGenerator.printStartTag("option", "value", String.valueOf(resultSet.getObject(1)));
                xmlGenerator.println((String)resultSet.getObject(2));
                xmlGenerator.printEndTag(); // zakonczenie znacznika option
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        xmlGenerator.printEndTag(); // zakonczenie znacznika select
        xmlGenerator.printEmptyElement("br");
        try 
        {
            resultSet.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
