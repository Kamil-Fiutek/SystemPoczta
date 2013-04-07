package model;

/**
 * Interfejs na wszystkie aplikacje systemu.
 *
 * @author Adrian Scheit
 * @version 0.1
 */
public interface ApplicationInterface {

    /**
     * Zwraca tytuł aplikacji. Najpierw konieczne jest sprawdenie uprawnień.
     *
     * @return
     */
    public String getTitle();

    /**
     * Prosi o wydrukowanie aplikacji.
     *
     * @param xmlGenerator "papier" do drukowania.
     */
    public void printApplication(view.XMLGenerator xmlGenerator);

    /**
     * Formularz przesłany spowrotem do aplikacji.
     *
     * @param xmlGenerator Możliwość druku odpowiedzi.
     * @return Czy aperacja się powiodła.
     */
    public boolean respose(view.XMLGenerator xmlGenerator);
}
