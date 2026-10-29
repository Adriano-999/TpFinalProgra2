package Model.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Exception.LibreriaException;
import Exception.PersistenciaException;

public class Conexion {
    private static Connection connection;
    public static Connection abrir() throws LibreriaException, PersistenciaException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String schema = "2026_C7_Anabel_Raimondo_TP_Final_Progra";
            connection = DriverManager.getConnection("jdbc:mysql://172.16.1.64:3306/"+schema,"bdd1","bdd1");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new LibreriaException();
        } catch (SQLException e) {
            throw new PersistenciaException(1);
        }
    }

    public static void cerrar() throws PersistenciaException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new PersistenciaException(0);
        }
    }
}