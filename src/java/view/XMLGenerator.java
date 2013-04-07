package view;

import java.io.PrintWriter;
import java.util.Stack;

/**
 * Klasa pozwalająca na generowanie odpowiedzi dla klienta. Cały dokument
 * <code>XML</code> który otrzyma i zobaczy klient jest generowany za pomocą
 * metod tej klasy. Jeżeli servlet chce wysłać dokument
 * <code>XML</code> do klienta to powinien utworzyć instancję tej klasy i za
 * pomocą jej metod to zrobić. Ponadto klasa "ładnie" formatuje dokument - robi
 * automatycznie wcięcia w dokumencie
 * <code>XML</code>. Wcięcia są generowane w standardowej kownecji: na każdy
 * poziom znaczników tworzony jest jeden tabulator w dokumencie. Ta klasa
 * stanowi podstawę i implementuje uniwersalne sposoby generowania dokumentu
 * <code>XML</code> (a nie koniecznie
 * <code>HTML</code>).
 *
 * @author Adrian Scheit
 * @version 1.1
 */
public class XMLGenerator {

    /**
     * Referensja prywatna do PrintWriter który umożliwia pisanie dokumentu
     * <code>XML</code> do strumienia wysłanego do klienta.
     */
    private PrintWriter printWriter;
    /**
     * Referencja na stos stringów do przechowywania kolejnych elementów
     * dokumentu
     * <code>XML</code>.
     */
    private Stack<String> elementStack = new Stack<String>();

    /**
     * Publiczny konstruktor klasy, który inicjalizuje prywatną zmienną
     * printWriter. Umożliwia to wpisywania generowanego dokumentu
     * <code>XML</code> bezpośrednio na strumień wysyłąny do klienta.
     *
     * @param printWriter Referencja PrintWriter która stanowi strumień wysyłany
     * do klienta servletu.
     */
    public XMLGenerator(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    /**
     * Drukuje podaną ilość znaków tabulacji.
     *
     * @param tabsQuantity Ilość znaków tabulacji.
     */
    private void printTabs(int tabsQuantity) {
        for (int i = 0; i < tabsQuantity; ++i) {
            printWriter.print("\t");
        }
    }

    /**
     * Drukuje podany tekst na strumień do klienta. Dodaje do końca znak nowej
     * lini. Dba o odpowiednie wcięcie przed dodaną treścią.
     *
     * @param text Treść do wdrukowania do strumienia wyjściowego.
     */
    public void println(String text) {
        this.printTabs(elementStack.size());
        printWriter.print(text);
        printWriter.print("\n");
    }

    /**
     * Otwiera tag otwierający dokumentu
     * <code>XML</code>. Wydrukowuje go wraz z podanymi attrybutami, odpowiednio
     * formatując te elementy. Zapamiętuje kolejny dodany element aby umożliwiść
     * jego zamknięcie i obliczenie potrzebnego wcięcia do następnych elementów
     * dokumentu. Poprawoność budowy podanych argumetntów w dokumencie
     * <code>XML</code>/
     * <code>HTML</code>/
     * <code>XHTML</code> nie jest spradzana.
     *
     * @param element Nazwa dodanego elementu.
     * @param attributes Atrybuty dodawanego elementu. Ilość atrybutów powinna
     * być pażysta. Każda para (dwa kolejne stringi podane w argumentach)
     * atrybutów stanowi kolejno nazwę i wartość atrybutu elementu
     * <code>XML</code>. Wartość nie powinna być w cudzysłowiu, ponieważ jest on
     * dodawany automatycznie.
     */
    public void printStartTag(String element, String... attributes) {
        this.printTabs(elementStack.size());

        //element=element.toLowerCase();

        printWriter.print("<");
        printWriter.print(element);
        boolean first = true;
        for (String attribute : attributes) {
            if (first) {
                printWriter.print(" ");
                printWriter.print(attribute/*.toLowerCase()*/);
            } else {
                printWriter.print("=\"");
                printWriter.print(attribute);
                printWriter.print("\"");
            }
            first = !first;
        }
        printWriter.print(">\n");

        elementStack.push(element);
    }

    /**
     * Otwiera tag elementu dokumentu
     * <code>XML</code>, wydrukowuje jego treść i zamyka go odpowiednim tag'iem.
     * Wydrukowuje element wraz z podanymi attrybutami, odpowiednio formatując
     * te elementy. Poprawoność budowy podanych argumetntów w dokumencie
     * <code>XML</code>/
     * <code>HTML</code>/
     * <code>XHTML</code> nie jest spradzana. Metoda ta różni się efektem od
     * zastosowania metod: printStartTag, println, printEndTag, tym że między
     * tag'ami otwierającym a zamykającym nie są dodawane automatycznie
     * tabulatory i znaki nowej lini.
     *
     * @param element Nazwa dodanego elementu.
     * @param inside Tekst który powinien się pojawić między znacznikiem
     * otwierającym a zamykającym nowego elementu.
     * @param attributes Atrybuty dodawanego elementu. Ilość atrybutów powinna
     * być pażysta. Każda para (dwa kolejne stringi podane w argumentach)
     * atrybutów stanowi kolejno nazwę i wartość atrybutu elementu
     * <code>XML</code>. Wartość nie powinna być w cudzysłowiu, ponieważ jest on
     * dodawany automatycznie.
     */
    public void printElement(String element, String inside, String... attributes) {
        this.printTabs(elementStack.size());

        //element=element.toLowerCase();

        printWriter.print("<");
        printWriter.print(element);
        boolean first = true;
        for (String attribute : attributes) {
            if (first) {
                printWriter.print(" ");
                printWriter.print(attribute/*.toLowerCase()*/);
            } else {
                printWriter.print("=\"");
                printWriter.print(attribute);
                printWriter.print("\"");
            }
            first = !first;
        }
        printWriter.print(">" + inside + "</" + element + ">\n");

    }

    /**
     * Dodaje tagu dokumentu dla elementu pustego. Wydrukowuje go wraz z
     * podanymi attrybutami, odpowiednio formatując te elementy. Poprawoność
     * budowy podanych argumetntów w dokumencie
     * <code>XML</code>/
     * <code>HTML </code>/
     * <code>XHTML </code> nie jest spradzana.
     *
     * @param element Nazwa dodanego elementu.
     * @param attributes Atrybuty dodawanego elementu. Ilość atrybutów powinna
     * być pażysta. Każda para (dwa kolejne stringi podane w argumentach)
     * atrybutów stanowi kolejno nazwę i wartość atrybutu * * * *
     * elementu <code>XML</code>. Wartość nie powinna być w cudzysłowiu,
     * ponieważ jest on dodawany automatycznie.
     */
    public void printEmptyElement(String element, String... attributes) {
        this.printTabs(elementStack.size());

        printWriter.print("<");
        printWriter.print(element);
        boolean first = true;
        for (String attribute : attributes) {
            if (first) {
                printWriter.print(" ");
                printWriter.print(attribute/*.toLowerCase()*/);
            } else {
                printWriter.print("=\"");
                printWriter.print(attribute);
                printWriter.print("\"");
            }
            first = !first;
        }
        printWriter.print(" />\n");
    }

    /**
     * Zamyka ostatnio otwarty, ale jeszcze nie zamknięty tag.
     * <code>XML</code>.
     */
    public void printEndTag() {
        String element = elementStack.pop();

        this.printTabs(elementStack.size());

        printWriter.print("</");
        printWriter.print(element);
        printWriter.print(">\n");
    }

    /**
     * Zamyka wszystkie otwarte tagi
     * <code>XML</code> w kolejności odwrotnej do tej w której były otwierane.
     * Zamyka również strumień wysyłany do klienta - kończąc tymsamym generowany
     * dokument.
     */
    public void closeAll() {
        while (!elementStack.isEmpty()) {
            this.printEndTag();
        }
        printWriter.close();
    }
}
