/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.applications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;
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
    private String charactersUsedToGenerateShipmentID;
    
    public DefiniowaniePrzesylek()
    {
        this.resultSet = null;
        this.charactersUsedToGenerateShipmentID = "0123456789";
    }
    
    @Override
    public String getTitle(Employee employee) {
        return "Definiowanie przesyłek";
    }

    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) {      
        
        xmlGenerator.printStartTag("script",
                "type", "text/javascript",
                "src", "../js/pages/formularz.js");
        xmlGenerator.printEndTag();
       
        xmlGenerator.printStartTag("script", 
                "type", "text/javascript",
                "src", "../js/pages/walidacja.formularza.przesylek.js");
        xmlGenerator.printEndTag();
        
        xmlGenerator.printStartTag("form", "action","","method","POST");
        
        xmlGenerator.printStartTag("div", "id", /*"tab tabActive"*/ "mainForm");
        
        xmlGenerator.printStartTag("label", "id", "nadawca_imie_nazwisko");
        xmlGenerator.println("Imię i nazwisko nadawcy (np. Jan Kowalski):");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "imie_nazwisko_nadawcy", 
                "id", "imie_nazwisko_nadawcy", 
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "nadawca_adres");
        xmlGenerator.println("Adres nadawcy - ulica oraz miasto (np. ul. Śliska 5a Szczecin):");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "adres_nadawcy", 
                "id", "adres_nadawcy",
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "nadawca_kod_pocztowy");
        xmlGenerator.println("Kod pocztowy nadawcy:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "kod_pocztowy_nadawcy",
                "id", "kod_pocztowy_nadawcy",
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "nadawca_nazwa_kraju");
        xmlGenerator.println( "Nazwa kraju nadawcy:");
        xmlGenerator.printEndTag();
        getDataFromDB("select idKraju, kraj from kraje");
        printResultSetContent(xmlGenerator, "nazwa_kraju_nadawcy");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "odbiorca_imie_nazwisko");
        xmlGenerator.println("Imię i nazwisko odbiorcy (np. Jan Kowalski):");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name","imie_nazwisko_odbiorcy",
                "id", "imie_nazwisko_odbiorcy",
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "odbiorca_adres");
        xmlGenerator.println("Adres odbiorcy - ulica oraz miasto (np. ul. Śliska 5a Szczecin):");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name","adres_odbiorcy",
                "id", "adres_odbiorcy",
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "odbiorca_kod_pocztowy");
        xmlGenerator.println("Kod pocztowy odbiorcy:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name","kod_pocztowy_odbiorcy",
                "id", "kod_pocztowy_odbiorcy",
                "type","text");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "odbiorca_nazwa_kraju");
        xmlGenerator.println("Nazwa kraju odbiorcy:");
        xmlGenerator.printEndTag();
        getDataFromDB("select idKraju, kraj from kraje");
        printResultSetContent(xmlGenerator, "nazwa_kraju_odbiorcy");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "przesylki_typ");
        xmlGenerator.println("Typ przesyłki:");
        xmlGenerator.printEndTag();
        getDataFromDB("select idTypu, typ from typyPrzesylek");
        printResultSetContent(xmlGenerator, "typ_przesylki");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printEmptyElement("input", "name", "dalej >>", "type", "submit", "value", "Dalej >>", "class", "tabEvaluator");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("div", "class","tab tabList");
        xmlGenerator.printStartTag("label", "id", "priorytet_list");
        xmlGenerator.println("List priorytetowy:");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("select", "name", "list_priorytet");
	xmlGenerator.printStartTag("option", "value", "Tak");
        xmlGenerator.println("Tak");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");
        xmlGenerator.println("Nie");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printStartTag("label", "id", "list_masa");
	xmlGenerator.println("Masa listu:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "masa_listu", 
                "id", "masa_listu",
                "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "wyslij_list", "type", "submit", "value", "Wyślij list");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEndTag(); // div class="tab tabList"

        xmlGenerator.printStartTag("div", "class","tab tabPaczka");
        xmlGenerator.printStartTag("label", "id", "priorytet_paczka");
        xmlGenerator.println("Paczka priorytetowa:");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("select", "name", "paczka_priorytet");
	xmlGenerator.printStartTag("option", "value", "Tak");
        xmlGenerator.println("Tak");
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("option", "value", "Nie");
        xmlGenerator.println("Nie");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("br");
        
        xmlGenerator.printStartTag("label", "id", "gabaryt_paczka");
	xmlGenerator.println("Gabaryt paczki:");
        xmlGenerator.printEndTag();
        getDataFromDB("select idGabarytu, opisGabarytu from gabaryty");
        printResultSetContent(xmlGenerator, "paczka_gabaryt");
        xmlGenerator.printEmptyElement("br");

        xmlGenerator.printStartTag("label", "id", "masa_paczka");
	xmlGenerator.println("Masa paczki:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "masa_paczki", 
                "id", "masa_paczki",
                "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "wyslij_paczke", "type", "submit", "value", "Wyślij paczkę");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEndTag();     //div class="tab tabPaczka"

        xmlGenerator.printStartTag("div", "class","tab tabPrzekaz");
        xmlGenerator.printStartTag("label", "id", "przekaz_kwota");
        xmlGenerator.println("Kwota przekazu:");
        xmlGenerator.printEndTag();
        xmlGenerator.printEmptyElement("input", "name", "kwota_przekazu", 
                "id", "kwota_przekazu",
                "type", "text");
	xmlGenerator.printEmptyElement("br");
	xmlGenerator.printEmptyElement("input", "name", "wyslij_przekaz", "type", "submit", "value", "Wyślij przekaz");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.printEndTag();     //div class="tab tabPrzekaz"
        
        xmlGenerator.printEndTag();     // form
        
        if (parameterMap.get("wyslij_list") != null)
        {
            getDataFromDB("select idPrzesylki from przesylki");
            for(Map.Entry<String, String[]> entry : parameterMap.entrySet()) 
             {
                 String[] wartosc = entry.getValue();
                 for (String i : wartosc)
                 {
                     System.out.println(i);
                 }
             }
        }
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
        try 
        {
            resultSet.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String generateShipmentID()
    { 
        Random random = new Random();
        int length = this.charactersUsedToGenerateShipmentID.length();
        char text[] = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = this.charactersUsedToGenerateShipmentID.charAt(random.nextInt(length));
        }
        return new String(text);
    }
    /*
    public boolean checkGeneratedShipmentID()
    {
        try 
        {
            while(resultSet.next() == true)
            {
                return true;
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
}
