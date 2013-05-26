
package model.applications;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Employee;
import view.XMLGenerator;

/**
 *
 * @author Kamil Fiutek
 * @version 0.01
 */
public class DodawaniePracownikow implements model.ApplicationInterface{

    private String idPracownika;
    private String idPlacowki;
    private String idPrzelozonego;
    private String imie;
    private String nazwisko;
    private String haslo;
    private String uprawanienia; 
 /**
  * 
  */
public void DodawaniePracownikow(){
}
/**
 * 
 * @param employee
 * @return 
 */
    @Override
    public String getTitle(Employee employee) {
        
        return "Dodanie Pracownika";
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
        createForm(xmlGenerator);
    }
    
    private void createForm (XMLGenerator xmlGenerator){
        
         xmlGenerator.printStartTag("form", "actoin", "", "method", "POST");
         
         
         xmlGenerator.println("idPracownika: ");
         xmlGenerator.printEmptyElement("input","name", "idPracownika", "type", "text", "value", "tu będzie id", "disabled", "disabled" );
         xmlGenerator.printEmptyElement("br");
         
         
         xmlGenerator.println("Wybierz Placowke: ");
         xmlGenerator.printStartTag("select", "name", "idPlacowki", "size", "1");
         xmlGenerator.printStartTag("option");
         xmlGenerator.println("Tu będą opcje pobrane z BD");
         xmlGenerator.printEndTag();
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");

         
         xmlGenerator.println("Wybierz Przelozonego: ");
         xmlGenerator.printStartTag("select", "name", "idPrzelozonego", "size", "1");
         xmlGenerator.printStartTag("option");
         xmlGenerator.println("Tu będą opcje pobrane z BD");
         xmlGenerator.printEndTag();
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");

         
         xmlGenerator.println("Imie: ");
         xmlGenerator.printEmptyElement("input","name", "imie_pracownika", "type", "text" );
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         
         xmlGenerator.println("Nazwisko: ");
         xmlGenerator.printEmptyElement("input","name", "nazwisko_pracownika", "type", "text" );
         xmlGenerator.printEmptyElement("br");
         
         
         xmlGenerator.println("Haslo uzytkownika: ");
         xmlGenerator.printEmptyElement("input","name", "haslo_pracownika", "type", "text" );
         xmlGenerator.printEmptyElement("br");

         xmlGenerator.printStartTag("fieldset");
         xmlGenerator.printStartTag("legent");
         xmlGenerator.println("Wybierz uprawnienia do przyznania pracownikowi");
         xmlGenerator.printEndTag(); // koniec legent
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check2", "value", "Edycja Tabel Słownikowych");
         xmlGenerator.printStartTag("label", "for", "check2");
         xmlGenerator.println("Edycja Tabel Słownikowych");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check3", "value", "Dodawanie Pracowników");
         xmlGenerator.printStartTag("label", "for", "check3");
         xmlGenerator.println("Dodawanie Pracowników");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check4", "value", "Modyfikacja Pracowników");
         xmlGenerator.printStartTag("label", "for", "check4");
         xmlGenerator.println("Modyfikacja Pracowników");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check5", "value", "Zwolnienia");
         xmlGenerator.printStartTag("label", "for", "check5");
         xmlGenerator.println("Zwolnienia");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check6", "value", "Dodawanie Placówek");
         xmlGenerator.printStartTag("label", "for", "check6");
         xmlGenerator.println("Dodawanie Placówek");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check7", "value", "Modyfikacja Placówek");
         xmlGenerator.printStartTag("label", "for", "check7");
         xmlGenerator.println("Modyfikacja Placówek");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check8", "value", "Likwidacja Placówek");
         xmlGenerator.printStartTag("label", "for", "check8");
         xmlGenerator.println("Likwidacja Placówek");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEmptyElement("input", "type", "checkbox", "name", "uprawnienia","id", "check1", "value", "Zmiana Statusu Paczki");
         xmlGenerator.printStartTag("label", "for", "check1");
         xmlGenerator.println("Zmiana Satusu Paczki");
         xmlGenerator.printEndTag();
         xmlGenerator.printEmptyElement("br");
         
         xmlGenerator.printEndTag();//koniec fieldset
         
         xmlGenerator.printStartTag("input", "type", "submit", "name", "wyslij", "value", "Dodaj Pracownika");
         xmlGenerator.printEmptyElement("br");
         
         
         xmlGenerator.printEndTag(); // "Koniec form"
    }
}
