package Model.BDD;

import Model.Pasajero;

import java.sql.*;
import java.util.HashSet;

import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.NoEncontradoException;
import Model.Usuario;

public class ConexionPasajero {

    public static boolean guardarPasajero(Pasajero pasajero) throws PersistenciaException, LibreriaException {
        String query = "INSERT INTO pasajeros values(?,?,?,?)";
        try (Connection con = Conexion.abrir()) {
            checkTabla(con);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pasajero.getDni());
            ps.setString(2, pasajero.getNombre());
            ps.setString(3, pasajero.getOrigen());
            ps.setString(4, pasajero.getDomicilio());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }

    public static void eliminarPasajero(String dni) throws PersistenciaException, LibreriaException, NoEncontradoException {
        String query = "DELETE FROM pasajeros WHERE dni = ?";
        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(query)) {

            checkTabla(con);

            ps.setString(1, dni);
            ps.executeUpdate();
            ps.close();
            Conexion.cerrar();

        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }

    public static Pasajero buscarPasajero(String dni)
            throws PersistenciaException, LibreriaException, NoEncontradoException {

        String query = "SELECT * FROM pasajeros WHERE dni = ?";

        try (Connection con = Conexion.abrir()) {

            checkTabla(con);

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, dni);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        Model.Pasajero p = new Pasajero(
                                rs.getString("dni"),
                                rs.getString("nombre"),
                                rs.getString("origen"),
                                rs.getString("domicilio")
                        );

                        return p;
                    }
                }
            }
        }
 catch(
    SQLException e)

    {
        throw new PersistenciaException(4);
    }
        return null;
}

    public static HashSet<Pasajero> listarPasajeros()
            throws PersistenciaException, LibreriaException, NoEncontradoException {

        HashSet<Pasajero> pasajeros = new HashSet<>();

        String query = "SELECT * FROM pasajeros";

        try (Connection con = Conexion.abrir()) {

            checkTabla(con);

            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Pasajero p = new Pasajero(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("origen"),
                            rs.getString("domicilio")
                    );

                    pasajeros.add(p);
                }
                rs.close();
                ps.close();
                Conexion.cerrar();
            }

            return pasajeros;

        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }
    }

    public static int obtenerId(String dni) throws PersistenciaException, LibreriaException, NoEncontradoException {
        String query = "SELECT id_pasajero FROM pasajeros where dni = ?";
        try (Connection con = Conexion.abrir()) {
            checkTabla(con);
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, dni);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_pasajero");
                    } else {
                        throw new NoEncontradoException();
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new PersistenciaException(3);
        }
    }

    private static void checkTabla(Connection connection) throws PersistenciaException {
        try {
            Statement st = connection.createStatement();
            st.execute("create table if not exists pasajeros ( " +
                    "id_pasajero int auto_increment primary key not null , " +
                    "dni varchar(10) not null unique, " +
                    "nombre varchar(20) not null, " +
                    "origen varchar(20) not null, " +
                    "domicilio varchar(20) not null;");
            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }
}
