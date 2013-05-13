/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.applications;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ApplicationInterface;
import model.Employee;
import view.XMLGenerator;

/**
 * Aplikacja służąca do edycji tabel słownikowych.
 * @author Łukasz Fornalczyk
 */
public class EdycjaTabelSlownikowych implements ApplicationInterface
{
    /**
     * Konstruktor bezargumentowy.
     */
    public EdycjaTabelSlownikowych()
    {
        
    }

    /**
     * Metoda pochodząca z interfejsu ApplicationInterface.
     * @param employee Pracownik który chce to wykonać.
     * @return Zwraca tytuł aplikacji wyświetlany na pasku aplikacji
     */
    @Override
    public String getTitle(Employee employee) 
    {
        return "Edycja tabel słownikowych";
    }

    /**
     * 
     * @param employee Pracownik który chce wykonać żądaną akcję.
     * @param httpServletResponse Umożliwia wygenerowanie odpowiedzi przez servlet. 
     * @param xmlGenerator Referencja do XMLGenerator dzięki, któremu 
     * generowany jest formularz.
     * @param parameterMap Parametry wprowadzone przez użytkownika w formularz.
     * @param httpSession Sesja użytkownika.
     */
    @Override
    public void printApplication(Employee employee, HttpServletResponse httpServletResponse, XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession) 
    {
        xmlGenerator.println("Narazie to jest cegłą.");
    }
    
}
