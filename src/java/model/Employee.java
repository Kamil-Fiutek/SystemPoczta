package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reprezentuje pracownika poczty. Jeśli id równe jest 0 to znaczy że to nie
 * jest pracownik.
 *
 * @author Adrian Scheit
 * @version 0.9
 */
public class Employee {

    private int idPracownika;
    private String fullName;
    private boolean[] rights;
    private static final int freeApplication = 2;

    public Employee(int idPracownika) {

        if (idPracownika != 0 && idPracownika > 0) {
            this.idPracownika = idPracownika;
            try {
                ResultSet resultSet =
                        ConnectionSingleton.getConnection(null).createStatement().
                        executeQuery("SELECT * FROM pracownicy WHERE idPracownika=" + idPracownika);
                if (resultSet.next()) {
                    fullName = resultSet.getString("imie") + " " + resultSet.getString("nazwisko");
                    String stringRights = resultSet.getString("uprawnienia");
                    rights = new boolean[stringRights.length() + freeApplication];
                    for (int i = 0; i < freeApplication; i++) {
                        rights[i] = true;
                    }
                    for (int i = 0; i < stringRights.length(); ++i) {
                        rights[i + freeApplication] = stringRights.charAt(i) != '0';
                    }
                }

            } catch (ClassNotFoundException e) {
                this.idPracownika = 0;
            } catch (SQLException e) {
                this.idPracownika = 0;
            }
        } else {
            this.idPracownika = 0;
        }
        if (this.idPracownika == 0) {
            rights = new boolean[freeApplication];
            for (int i = 0; i < freeApplication; i++) {
                rights[i] = true;
            }
        }
    }

    public String getFullName() {
        return fullName;
    }

    public boolean getRight(int rightNumber) {
        if (rightNumber < 0 || rightNumber >= rights.length) {
            return false;
        } else {
            return rights[rightNumber];
        }
    }

    public boolean isEmployee() {
        return idPracownika != 0;
    }

    public int getId() {
        return idPracownika;
    }
}
