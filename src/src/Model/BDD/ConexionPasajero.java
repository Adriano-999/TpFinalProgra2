package Model.BDD;

import Model.Pasajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import Exception.PersistenciaException;
import Exception.LibreriaException;

public class ConexionPasajero {

    public boolean guardarPasajero(Pasajero pasajero) throws PersistenciaException, LibreriaException
    {
        String query = "INSERT INTO pasajeros values(?,?,?,?,?)";
        try(Connection con = Conexion.abrir()) {
            checkTabla(con);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, pasajero.getId_usuario());
            ps.setString(2, pasajero.getDni());
            ps.setString(3, pasajero.getNombre());
            ps.setString(4, pasajero.getOrigen());
            ps.setString(5, pasajero.getDomicilio());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }

    private static void checkTabla(Connection connection) throws PersistenciaException {
        try {
            Statement st = connection.createStatement();
            st.execute("create table if not exists pasajeros ( " +
                    "id_pasajero int auto_increment primary key not null , " +
                    "id_usuario int not null, " +
                    "dni varchar(10) not null unique, " +
                    "nombre varchar(20) not null, " +
                    "origen varchar(20) not null, " +
                    "domicilio varchar(20) not null" +
                    "FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario);");
            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }
}
