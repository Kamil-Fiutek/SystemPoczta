package view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Klasa odpowiadająca za widok użytkownika, czyli generowany dokument
 * <code>HTML</code>. Klasa pracuje na bazie klasy XMLGenerator która dostarcza
 * wszystkich potrzebnych i uniwersalnych metod do generowania dokumentu
 * <code>HTML</code>. Ta klasa jest ukierunkowana pod generowanie niektórych
 * charakterystycznych części dokumentu
 * <code>HTML</code>.
 *
 * @author Adrian Scheit
 * @version 1.1
 */
public class HTMLGenerator {

    /**
     * Prywatna referencja na obiekt klasy XMLGenerator, która stanowi podstawę
     * do generowania dokumentu
     * <code>HTML</code> przez metody tej klasy.
     */
    private XMLGenerator xmlGenerator = null;

    /**
     * Publiczny konstruktor jednoargumentowy. Inicjalizuje referencję prywatną
     * przez referencję podaną w argumęcie.
     *
     * @param lowLevelView Inicjalizuje referencję prywatną przez referencję
     * podaną w tym argumęcie.
     */
    public HTMLGenerator(XMLGenerator lowLevelView) {
        this.xmlGenerator = lowLevelView;
    }

    /**
     * Wydrukowywuje linie tekstu w dokumencie
     * <code>HTML</code>. Kończy ją znacznikiem nowej lini: br
     *
     * @param line Linia do wydrukowania
     */
    public void println(String line) {
        xmlGenerator.println(line);
        xmlGenerator.printEmptyElement("br");
    }

    /**
     * Generuje początek dokumentu
     * <code>HTML</code>. Poszątek oznacza: <br/> - informacje DOCTYPE <br/> -
     * znacznik otwierający dokument
     * <code>HTML</code> <br/> - nagłówek dokumentu wraz z odpowiednią
     * zawartością <br/> - znacznik otwierający ciało dokumentu
     *
     * @param title Tytuł strony HTML. Jest on wstawiany pomiędzy odpowiednie
     * znaczniki.
     */
    public void printHTMLBegin(String title) {
        xmlGenerator.println("<!DOCTYPE html>");
        xmlGenerator.printStartTag("html");
        xmlGenerator.printStartTag("head");

        xmlGenerator.printEmptyElement("meta",
                "http-equiv", "Content-Type",
                "content", "text/html; charset=UTF-8");
        xmlGenerator.printEmptyElement("link",
                "rel", "stylesheet",
                "type", "text/css",
                "href", "style.css");
        xmlGenerator.printElement("title", title);

        xmlGenerator.printEndTag();// head
        xmlGenerator.printStartTag("body");
    }

    /**
     * Generuje zakończenie dokumentu, czyli: <br/> - znacznik zamykający ciała
     * strony <br/> - znacznik zamykający dokument
     * <code>HTML</code> <br/> - zamyka PrintWriter do którego były zapisywane
     * informacje
     */
    public void printHTMLEnd() {

        xmlGenerator.closeAll();
    }

    /**
     * Generuje fragment ciała dokumentu
     * <code>HTML</code>. Wyświetla informacje o wyjątku z podanym typem i
     * wiadomością.
     *
     * @param e Z referencji na wyjątek pobierane są dane:<br/> Typ wyjątku.
     * Może być null - wtedy typ nie zostanie wyświetlony.<br/>Wiadomość w
     * wyjątku. Może być null - wtedy wiadomość nie zostanie wyświetlony.
     */
    public void printError(Exception e) {
        String message = e.getMessage();

        xmlGenerator.printStartTag("b");
        xmlGenerator.printStartTag("font", "color", "red");
        xmlGenerator.println("Złapano wyjątek typu: " + e.getClass().getName()
                + (message == null ? " bez przekazanej wiadomości!" : " zawierający wiadomość: " + message));
        //lowLevelView.addElement("br");
        //e.printStackTrace(lowLevelView.printWriter);
        xmlGenerator.printEndTag();// font
        xmlGenerator.printEndTag();// b
    }

    /**
     * Wyświetla podany ResultSet. Wyświetla wszystkie dane oraz nazwy kolumn w
     * tabeli
     * <code>HTML</code>. Dodaje informacje o ilości wierszy i kolumn w tabeli.
     *
     * @param resultSet Referencja na ResultSet do wyświetlenia.
     * @throws SQLException An exception that provides information on a database
     * access error or other errors. Each SQLException provides several kinds of
     * information:<br/> a string describing the error. This is used as the Java
     * Exception message, available via the method getMesasge.<br/> a "SQLstate"
     * string, which follows either the XOPEN SQLstate conventions or the
     * SQL:2003 conventions. The values of the SQLState string are described in
     * the appropriate spec. The DatabaseMetaData method getSQLStateType can be
     * used to discover whether the driver returns the XOPEN type or the
     * SQL:2003 type.<br/> an integer error code that is specific to each
     * vendor. Normally this will be the actual error code returned by the
     * underlying database.<br/> a chain to a next Exception. This can be used
     * to provide additional error information.<br/> the causal relationship, if
     * any for this SQLException.
     */
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int cols = resultSetMetaData.getColumnCount();
        int rows = 0;
        //int cos=resultSet.getStatement().getFetchSize()

        xmlGenerator.printStartTag("table", "border", "1px");
        xmlGenerator.printStartTag("tr");
        for (int i = 0; i < cols; ++i) {
            xmlGenerator.printStartTag("th");
            xmlGenerator.println(resultSetMetaData.getColumnName(i + 1));
            xmlGenerator.printEndTag(); //th
        }
        xmlGenerator.printEndTag(); //tr

        while (resultSet.next()) {
            ++rows;
            xmlGenerator.printStartTag("tr");
            for (int i = 1; i <= cols; ++i) {
                xmlGenerator.printElement("td", resultSet.getString(i));
            }
            xmlGenerator.printEndTag(); //tr
        }
        xmlGenerator.printEndTag(); //table

        xmlGenerator.println("Wierszy: " + rows + " kolumn: " + cols + "<br/>");
    }

    /**
     * Wyświetla formularz do wysyłania uniwersalnych zapytań SQL przez serwlet
     * Main. Formularz wysyłany jest do skrytu który go wyświtla metodą POST.
     * Przed formularzem wyświetlonych jest kilka przykładowych zapytań SQL.
     *
     * @param query Zapytanie SQL wyświetlone w textarea - najlepiej jeśli
     * będzie to jakiś przykład, lub ostatnio wprowadzone zapytanie.
     */
    @Deprecated
    public void printSQLQueryForm(String query) {

        xmlGenerator.println("Przykłady: ");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("insert into klienci values (2,'Firma Pana Kowalskiego')");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("insert into produkty values (2,19.99,'Miod')");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("insert into zakupy values (2,2,1)");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("insert into zakupy values (2,1,10000)");

        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("update zakupy set ilosc=ilosc/2 where id_klienta=1");

        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("select p.nazwa,k.nazwa,z.ilosc from klienci k,produkty p,zakupy z where k.id_klienta = z.id_klienta and z.id_produktu=p.id_produktu");
        xmlGenerator.printEmptyElement("br");
        xmlGenerator.println("select k.id_klienta, sum(z.ilosc*p.cena) from klienci k left outer join zakupy z on k.id_klienta=z.id_klienta left outer join produkty p on p.id_produktu=z.id_produktu group by k.id_klienta");


        xmlGenerator.printStartTag("form", "action", "/raport6/main", "method", "POST");
        xmlGenerator.printStartTag("label");
        xmlGenerator.println("Treść zapytania SQL:");
        xmlGenerator.printEndTag();// label
        xmlGenerator.printElement("textarea", query, "name", "query", "cols", "50");
        xmlGenerator.printEmptyElement("input", "type", "submit");
        xmlGenerator.printEndTag();// form
    }

    /**
     * Wyświetla nagłówek
     * <code>HTML</code> o podanym poziomie i treści.
     *
     * @param level Poziom nagłówka: powinien być między 1 a 6.
     * @param header Treść nagłówka.
     */
    public void printHTMLHeader(int level, String header) {
        xmlGenerator.printElement("h" + level, header);
    }

    /**
     * Wyświetla informacje o powodzeniu się czegoś.
     *
     * @param text Treść informacji.
     */
    public void printSuccess(String text) {
        xmlGenerator.printStartTag("b");
        xmlGenerator.printStartTag("font", "color", "green");
        xmlGenerator.println("Sukces! "
                + (text == null ? "" : text));
        xmlGenerator.printEndTag();// font
        xmlGenerator.printEndTag();// b
    }

    /**
     * Wyświetla nową linie w
     * <code>HTML</code> - czyli wyświetla znacznik nowej lini w nowej lini z
     * wcięciem.
     */
    public void printNewline() {
        xmlGenerator.printEmptyElement("br");
    }
}
