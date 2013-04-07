package model;

import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * Interfejs na wszystkie aplikacje systemu.
 *
 * @author Adrian Scheit
 * @version 0.2
 */
public interface ApplicationInterface {

    /**
     * Zwraca tytuł aplikacji. Najpierw konieczne jest sprawdenie uprawnień.
     *
     * @param employee Pracownik który chce to wykonać.
     * @return Zwraca tytuł aplikacji wyświetlany na pasku aplikacji
     */
    public String getTitle(Employee employee);

    /**
     * Prosi o wydrukowanie aplikacji.
     *
     * @param employee Pracownik który chce to wykonać.
     * @param xmlGenerator "papier" do drukowania.
     * @param parameterMap Parametry przekazane od pracownika/użytkownika.
     * @param httpSession Sesja użytkownika.
     */
    public void printApplication(Employee employee, view.XMLGenerator xmlGenerator, Map<String, String[]> parameterMap, HttpSession httpSession);
}
