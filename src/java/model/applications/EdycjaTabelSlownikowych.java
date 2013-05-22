/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.applications;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ApplicationInterface;
import model.Employee;
import view.XMLGenerator;

/**
 * Aplikacja służąca do edycji tabel słownikowych.
 *
 * @author Łukasz Fornalczyk
 */
public class EdycjaTabelSlownikowych implements ApplicationInterface
{
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private ArrayList<String> formErrorsList;
    private String[] dictionaryTables = {"Gabaryty", "Kraje", "Statusy przesylek"};
    private String[] dictionaryTablesOperations = {"Dodaj", "Edytuj", "Kasuj"};
    private String selectedTableName;
    private String[] splittedData;
    private boolean udane;
    
    /**
     * Konstruktor bezargumentowy.
     */
    public EdycjaTabelSlownikowych()
    {
        resultSet = null;
        resultSetMetaData = null;
        formErrorsList = new ArrayList<String>();
        selectedTableName = null;
        splittedData = null;
    }

    /**
     * Metoda pochodząca z interfejsu ApplicationInterface.
     *
     * @param employee Pracownik który chce to wykonać.
     * @return Zwraca tytuł aplikacji wyświetlany na pasku aplikacji
     */
    @Override
    public String getTitle(Employee employee)
    {
        return "Edycja tabel słownikowych";
    }

    /**
     * @param employee Pracownik który chce wykonać żądaną akcję.
     * @param httpServletResponse Umożliwia wygenerowanie odpowiedzi przez
     * servlet.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowany
     * jest formularz.
     * @param parameterMap Parametry wprowadzone przez użytkownika w formularz.
     * @param httpSession Sesja użytkownika.
     */
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession)
    {
        udane = false;
        String selectedOperation = null;
        if (parameterMap.get("Dalej") != null)
        {
            selectedTableName = parameterMap.get("tabliceSlownikowe")[0];
            selectedOperation = parameterMap.get("operacje")[0];
            
            if (selectedOperation.equals(dictionaryTablesOperations[0]))
            {
                generateAddDataForm(xmlGenerator);
            }
            else if (selectedOperation.equals(dictionaryTablesOperations[1]))
            {
                generateEditDataForm(xmlGenerator);
            }
            else if (selectedOperation.equals(dictionaryTablesOperations[2]))
            {
                generateDeleteDataForm(xmlGenerator);
            }
        }

        if (parameterMap.get("Dodaj") != null)
        {
            validateAddDataForm(parameterMap);
            if (formErrorsList.isEmpty())
            {
                addData();
            }
            else
            {
                printFormErrors(xmlGenerator);
            }
        }
        else if (parameterMap.get("Edytuj") != null)
        {
            editData();
        }

        if (parameterMap.get("Kasuj") != null)
        {
            deleteData(parameterMap);
        }

        if (!udane)
        {
            generateDictionaryTablesForm(xmlGenerator);
        }
    }

    /**
     * Metoda drukuje na ekranie formularz, w którym wybieramy interesującą nas
     * tabelę.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowany
     * jest formularz.
     */
    private void generateDictionaryTablesForm(XMLGenerator xmlGenerator)
    {
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("table", "");
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Proszę wybrać tabelę oraz żądaną operację");
        xmlGenerator.printEndTag(); // font
        xmlGenerator.printEndTag(); // td
        xmlGenerator.printEndTag(); // tr
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        printDataInCombobox(xmlGenerator, dictionaryTables, "tabliceSlownikowe");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        printDataInCombobox(xmlGenerator, dictionaryTablesOperations, "operacje");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Dalej", "name", "Dalej");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printEndTag(); // table
        xmlGenerator.printEndTag(); // form
    }
    
    /**
     * Metoda drukuje na ekranie w postaci listy rozwijanej przekazaną tablicę.
     * @param table Tablica z danymi.
     * @param httpServletResponse Umożliwia wygenerowanie odpowiedzi przez
     * servlet.
     */
    private void printDataInCombobox(XMLGenerator xmlGenerator, String[] table, String comboboxName)
    {
        xmlGenerator.printStartTag("select", "name", comboboxName);

        for (String i: table)
        {
            xmlGenerator.printStartTag("option", "value", i);
            xmlGenerator.println(i);
            xmlGenerator.printEndTag();
        }

        xmlGenerator.printEndTag();
    }
    
    /**
     * Metoda wyświetla na ekranie nazwy kolumn w analizowanej tabeli.
     * Pobranie nazw kolumn z tabeli odbybwa się dzięki obiektowi 
     * ResultSetMetaData.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowane
     * są odpowiedzi dla klienta.
     * @param columnsNumber Ilość kolumn 
     */
    private void printTableHeaders(XMLGenerator xmlGenerator, int columnsNumber)
    {
        xmlGenerator.printStartTag("tr");
        for (int i = 1; i <= columnsNumber; i++)
        {
            xmlGenerator.printStartTag("th");
            try
            {
                xmlGenerator.println(resultSetMetaData.getColumnName(i));
            }
            catch (SQLException ex)
            {
                Logger.getLogger(EdycjaTabelSlownikowych.class.getName()).log(Level.SEVERE, null, ex);
            }
            xmlGenerator.printEndTag();
        }
        xmlGenerator.printEndTag();
    }

    /**
     * Metoda odpowiada za wygenerowanie formularza umożliwiającego
     * dodanie rekordów do wybranej tabeli.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowane
     * są odpowiedzi dla klienta. 
     */
    private void generateAddDataForm(XMLGenerator xmlGenerator)         // dodawanie
    {
        resultSet = model.ConnectionSingleton.executeQuery("select * from " 
                + selectedTableName.toLowerCase().replace(" ", ""));
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Zawartość tabeli " + selectedTableName);
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("table");
        
        try
        {
            resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            printTableHeaders(xmlGenerator, columnsNumber);
            
            while (resultSet.next())
            {
                xmlGenerator.printStartTag("tr", "");
                for (int i = 1; i <= columnsNumber; i++)
                {
                    xmlGenerator.printStartTag("td");
                    xmlGenerator.printStartTag("font", "color", "black", "size", "3");
                    xmlGenerator.println(resultSet.getObject(i).toString());
                    xmlGenerator.printEndTag();
                    xmlGenerator.printEndTag();
                }
                xmlGenerator.printEndTag();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EdycjaTabelSlownikowych.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Poniżej proszę wpisać kolejne wartości oddzielone znakiem nowej linii");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printStartTag("textarea", "name", "dane", "rows", "5", "cols", "50");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Dodaj", "name", "Dodaj");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        
        xmlGenerator.printEndTag(); // table
        xmlGenerator.printEndTag(); // form
        udane = true;
    }

    /**
     * Metoda odpowiada za wygenerowanie formularza umożliwiającego
     * edycję rekordów z wybranej tabeli.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowane
     * są odpowiedzi dla klienta. 
     */
    private void generateEditDataForm(XMLGenerator xmlGenerator)	// edycja
    {
        resultSet = model.ConnectionSingleton.executeQuery("select * from " 
                + selectedTableName.toLowerCase().replace(" ", ""));
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Zawartość tabeli " + selectedTableName);
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("table", "width", "75%");
        
        try
        {
            resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            printTableHeaders(xmlGenerator, columnsNumber);
            
            while (resultSet.next())
            {
                xmlGenerator.printStartTag("tr", "");
                for (int i = 1; i <= columnsNumber; i++)
                {
                    xmlGenerator.printStartTag("td");
                    xmlGenerator.printStartTag("font", "color", "black", "size", "3");
                    xmlGenerator.println(resultSet.getObject(i).toString());
                    xmlGenerator.printEndTag();
                    xmlGenerator.printEndTag();
                    
                    if (i == columnsNumber)
                    {
                        xmlGenerator.printStartTag("td");
                        xmlGenerator.printEmptyElement("input", "type", "text",
                                "size", "25",
                                "name", resultSet.getObject(1).toString());
                        xmlGenerator.printEndTag();
                    }
                }
                xmlGenerator.printEndTag();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            resultSet.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Edytuj", "name", "Edytuj");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();

        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        udane = true;
    }

    /**
     * Metoda odpowiada za wygenerowanie formularza umożliwiającego
     * kasowanie rekordów z wybranej tabeli.
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowane
     * są odpowiedzi dla klienta. 
     */
    private void generateDeleteDataForm(XMLGenerator xmlGenerator)	// usuwanie
    {
        resultSet = model.ConnectionSingleton.executeQuery("select * from " 
                + selectedTableName.toLowerCase().replace(" ", ""));
        xmlGenerator.printStartTag("form", "action", "", "method", "POST");
        xmlGenerator.printStartTag("font", "color", "black", "size", "4");
        xmlGenerator.println("Zawartość tabeli " + selectedTableName);
        xmlGenerator.printEndTag();
        xmlGenerator.printStartTag("table", "class", "tableSelectable");
        
        try
        {
            resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            printTableHeaders(xmlGenerator, columnsNumber);
            
            while (resultSet.next())
            {
                xmlGenerator.printStartTag("tr");                
                for (int i = 1; i <= columnsNumber; i++)
                {
                    xmlGenerator.printStartTag("td");
                    if (i == 1)
                    {
                        xmlGenerator.printEmptyElement("input", "type", "checkbox",
                                "name", ( (Integer)(resultSet.getInt(1)) ).toString(),
                                "id", resultSet.getObject(1).toString(),
                                "value", resultSet.getObject(2).toString());
                    }
                    xmlGenerator.printStartTag("label", "for", resultSet.getObject(1).toString());
                    xmlGenerator.printStartTag("font", "color", "black", "size", "3");
                    xmlGenerator.println(resultSet.getObject(i).toString());
                    xmlGenerator.printEndTag();
                    xmlGenerator.printEndTag();
                    xmlGenerator.printEndTag();
                }
                xmlGenerator.printEndTag();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            resultSet.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DefiniowaniePrzesylek.class.getName()).log(Level.SEVERE, null, ex);
        }

        xmlGenerator.printStartTag("tr", "");
        xmlGenerator.printStartTag("td", "");
        xmlGenerator.printEmptyElement("input", "type", "submit", "value", "Kasuj", "name", "Kasuj");
        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();

        xmlGenerator.printEndTag();
        xmlGenerator.printEndTag();
        udane = true;
    }
    
    /**
     * Dodaje rekordy do tabeli.
     * Metoda sprawdza czy dane wprowadzone przez użytkonika zawierają ",".
     * Jeżeli tak jest dzieli je względem "," i dodaje do bazy danych. W przypadku,
     * gdy wprowadzone dane nie zawierają "," są one od razu dodawane do bazy danych.
     */
    private void addData()
    {
        for (String i: splittedData)
        {
            if(i.contains(","))
            {
                String[] data = i.split(",");
                for(int j = 0; j < data.length - 1; j++)
                {
                    model.ConnectionSingleton.executeUpdate("insert into " + selectedTableName 
                            + " values (null, " + "'" + data[j] + "', " + data[j + 1] + ")");
                }
            }
            else
            {
                model.ConnectionSingleton.executeUpdate("insert into " + selectedTableName 
                        + " values (null, " + "'" + i + "')");
            }
        }
    }

    /**
     * Edytuje rekordy w tabeli.
     */
    private void editData()
    {
        
    }
    
    /**
     * Usuwa rekordy z tabeli.
     * Najpierw w metodzie zostają sprawdzone kolejne checkboxy 
     * (ich nazwy to kolejne numery id z tabeli powiększone o 1). Jeżeli jakiś
     * jest zaznaczony to pobieramy jego id i rekord o takim id zostaje usunięty
     * z tabeli.
     * @param parameterMap Parametry wprowadzone przez użytkownika w formularz.
     */
    private void deleteData(Map<String, String[]> parameterMap)
    {
        try
        {
            resultSet = model.ConnectionSingleton.executeQuery("select * from "
                    + selectedTableName.toLowerCase().replace(" ", ""));
            resultSetMetaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                Integer id = resultSet.getInt(1);   // pobranie id bieżącego rekordu z tabeli
                
                if ( parameterMap.get(id.toString()) != null)
                {
                    model.ConnectionSingleton.executeUpdate("delete from " 
                            + selectedTableName.toLowerCase().replace(" ", "")
                            + " where " + resultSetMetaData.getColumnName(1) 
                            + " = " + id);
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EdycjaTabelSlownikowych.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param parameterMap 
     */
    private void validateAddDataForm(Map<String, String[]> parameterMap)
    {
//        String data = parameterMap.get("dane")[0];
//        
//        if ((data == null) || (data.isEmpty()))
//        {
//            formErrorsList.add("Proszę wprowadzić dane");
//        }
//        else
//        {
//            splittedData = data.split("\n");
//        }
    }
    
     /**
     * Wyświetla na ekranie błędy, które pojawiły sie w formularzu. Metoda
     * wyświetla na ekranie błędy, które zostały spowodowane błędnym
     * wypełnieniem pól formularza. Po ich wyświetleniu lista zawierajaca błędy
     * zostaje wyczyszczona. Błędy przechowywane są w formErrorsList (lista
     * Stringów).
     *
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu generowane
     * są odpowiedzi dla klienta.
     */
    private void printFormErrors(XMLGenerator xmlGenerator)
    {
        for (String i : this.formErrorsList)
        {
            xmlGenerator.printStartTag("font", "color", "red");
            xmlGenerator.printStartTag("b", "");
            xmlGenerator.println("* " + i + " <br>");
            xmlGenerator.printEndTag();
            xmlGenerator.printEndTag();
        }
        this.formErrorsList.clear();
    }
}