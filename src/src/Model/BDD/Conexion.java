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
            String schema = "2026_C7_Feliziani_Benitez_Raimondo_TP_Final_Progra";
            String IP = "172.16.1.64";
            String port = "3306";
            String usuario = "bdd1";
            String contrasenia = "bdd1";
            connection = DriverManager.getConnection("jdbc:mysql://"+IP+":"+port+"/"+schema,usuario,contrasenia);
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