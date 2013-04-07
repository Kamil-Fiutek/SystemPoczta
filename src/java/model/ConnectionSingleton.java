package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletConfig;

/**
 * Klasa implementująca kreacyjny i obiektowy wzorzec projektrowy singleton. ,
 * (z wikipedii) którego celem jest ograniczenie możliwości tworzenia obiektów
 * danej klasy do jednej instancji oraz zapewnienie globalnego dostępu do
 * stworzonego obiektu. Instancja tej klasy to obiekt Connection stworzony na
 * podstawie parametrów inicjalizacyjnych servletu. TODO Impelemtacja ta jest
 * implementacją leniwą. Jeżeli instancja jest już podana to
 * <code>servletConfig</code> nie musi (aczkolwiek może) być podawane.
 *
 * @author Adrian Scheit
 * @version 1.2
 */
public class ConnectionSingleton {

    private static String driverName = "org.apache.derby.jdbc.ClientDriver";
    /**
     * Prywatna statyczna referencja na jedyną instancję klasy
     * <code>java.sql.Connection</code>
     */
    private static Connection connection = null;

    /**
     * Konstruktor bezargumentowy. Nie inicjalizuje żadnych pól. Został on
     * napisany w celu zapewnienia dokumentacji do konstruktora.
     */
    public ConnectionSingleton() {
    }

    /**
     * Synchronizowana metoda zwracająca jedyną instancję klasy
     * <code>java.sql.Connection</code>
     *
     * @param servletConfig Konfiguracja serwletu umożliwiająca odczytanie
     * odpowienich parametrów inicjalizacyjnych.
     * @return Jedyną instancję klasy Connection. Jedyną nie globalnie, ale
     * jedyną pobieraną za pomocą tej klasy. Jeśli nie da się stworzyć instancji
     * to zwracany jest null.
     *
     * @throws SQLException An exception that provides information on a database
     * access error or other errors.<br/> Each SQLException provides several
     * kinds of information:<br/> a string describing the error. This is used as
     * the Java Exception message, available via the method getMesasge.<br/> a
     * "SQLstate" string, which follows either the XOPEN SQLstate conventions or
     * the SQL:2003 conventions. The values of the SQLState string are described
     * in the appropriate spec. The DatabaseMetaData method getSQLStateType can
     * be used to discover whether the driver returns the XOPEN type or the
     * SQL:2003 type.<br/> an integer error code that is specific to each
     * vendor. Normally this will be the actual error code returned by the
     * underlying database.<br/> a chain to a next Exception. This can be used
     * to provide additional error information.<br/> the causal relationship, if
     * any for this SQLException.
     *
     * @throws ClassNotFoundException Thrown when an application tries to load
     * in a class through its string name using:<br/> The forName method in
     * class Class.<br/> The findSystemClass method in class ClassLoader .<br/>
     * The loadClass method in class ClassLoader.<br/> but no definition for the
     * class with the specified name could be found.<br/> As of release 1.4,
     * this exception has been retrofitted to conform to the general purpose
     * exception-chaining mechanism. The "optional exception that was raised
     * while loading the class" that may be provided at construction time and
     * accessed via the ClassNotFoundException.getException() method is now
     * known as the cause, and may be accessed via the Throwable.getCause()
     * method, as well as the aforementioned "legacy method." <br/>
     */
    public static synchronized Connection getConnection(ServletConfig servletConfig) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            if (servletConfig == null) {
                //return null;
            } else {
                Class.forName(driverName);
                connection = DriverManager.getConnection(
                        servletConfig.getInitParameter("url"),
                        servletConfig.getInitParameter("user"),
                        servletConfig.getInitParameter("password"));
            }
        }

        return connection;
    }

    /**
     * Tworzy jedyną instancję klasy
     * <code>java.sql.Connection</code>. Jeśli instancja już istniała to metoda
     * nie daje efektu.
     *
     * @param servletConfig Konfiguracja serwletu umożliwiająca odczytanie
     * odpowienich parametrów inicjalizacyjnych.
     * @throws SQLException An exception that provides information on a database
     * access error or other errors.<br/> Each SQLException provides several
     * kinds of information:<br/> a string describing the error. This is used as
     * the Java Exception message, available via the method getMesasge.<br/> a
     * "SQLstate" string, which follows either the XOPEN SQLstate conventions or
     * the SQL:2003 conventions. The values of the SQLState string are described
     * in the appropriate spec. The DatabaseMetaData method getSQLStateType can
     * be used to discover whether the driver returns the XOPEN type or the
     * SQL:2003 type.<br/> an integer error code that is specific to each
     * vendor. Normally this will be the actual error code returned by the
     * underlying database.<br/> a chain to a next Exception. This can be used
     * to provide additional error information.<br/> the causal relationship, if
     * any for this SQLException.
     *
     * @throws ClassNotFoundException Thrown when an application tries to load
     * in a class through its string name using:<br/> The forName method in
     * class Class.<br/> The findSystemClass method in class ClassLoader .<br/>
     * The loadClass method in class ClassLoader.<br/> but no definition for the
     * class with the specified name could be found.<br/> As of release 1.4,
     * this exception has been retrofitted to conform to the general purpose
     * exception-chaining mechanism. The "optional exception that was raised
     * while loading the class" that may be provided at construction time and
     * accessed via the ClassNotFoundException.getException() method is now
     * known as the cause, and may be accessed via the Throwable.getCause()
     * method, as well as the aforementioned "legacy method." <br/>
     */
    public static synchronized void createConnection(ServletConfig servletConfig) throws ClassNotFoundException, SQLException {
        if (connection == null) {

            Class.forName(driverName);
            connection = DriverManager.getConnection(
                    servletConfig.getInitParameter("url"),
                    servletConfig.getInitParameter("user"),
                    servletConfig.getInitParameter("password"));
        }
    }

    /**
     * Zamyka połączenie jedynej instancji kalsy
     * <code>java.sql.Connection</code>. Jeśli instancja nie istniała to metoda
     * nie daje efektu.
     *
     * @throws SQLException An exception that provides information on a database
     * access error or other errors.<br/> Each SQLException provides several
     * kinds of information:<br/> a string describing the error. This is used as
     * the Java Exception message, available via the method getMesasge.<br/> a
     * "SQLstate" string, which follows either the XOPEN SQLstate conventions or
     * the SQL:2003 conventions. The values of the SQLState string are described
     * in the appropriate spec. The DatabaseMetaData method getSQLStateType can
     * be used to discover whether the driver returns the XOPEN type or the
     * SQL:2003 type.<br/> an integer error code that is specific to each
     * vendor. Normally this will be the actual error code returned by the
     * underlying database.<br/> a chain to a next Exception. This can be used
     * to provide additional error information.<br/> the causal relationship, if
     * any for this SQLException.
     */
    public static synchronized void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
