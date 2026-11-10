package Model.BDD;

import java.sql.*;
import java.util.HashSet;

import Exception.LibreriaException;
import Exception.PersistenciaException;
import Model.Usuario;
import Enum.TipoUsuario;

public class ConexionUsuario {

    public static Usuario ingresar(String usuario, String contrasenia) throws PersistenciaException, LibreriaException
    {
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasenia = ?";
        try(Connection con = Conexion.abrir())
        {
            checkTabla(con);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Usuario u = new Usuario(rs.getInt("id_usuario"), rs.getString("usuario"), rs.getString("contrasenia"), TipoUsuario.valueOf(rs.getString("tipo_usuario")));
            ps.close();
            rs.close();
            Conexion.cerrar();
            return u;}
            return null;
        }catch(SQLException e)
        {
            throw new PersistenciaException(4);
        }
    }

    public boolean crearUsuario(String usuario, String contrasenia, TipoUsuario tipoUsuario) throws LibreriaException, PersistenciaException
    {
        String query = "INSERT INTO usuarios (usuario, contrasenia, tipo_usuario) values (?,?,?)";
        try(Connection con = Conexion.abrir())
        {
            checkTabla(con);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ps.setString(3, tipoUsuario.name());
            ps.executeUpdate();
                ps.close();
                Conexion.cerrar();
                return true;
        }catch(SQLException e)
        {
            throw new PersistenciaException(3);
        }
    }

    public void eliminarUsuario(int id_usuario) throws PersistenciaException, LibreriaException
    {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(query)) {

            checkTabla(con);

            ps.setInt(1, id_usuario);
            ps.executeUpdate();
            ps.close();
            Conexion.cerrar();

        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }

    public HashSet<Usuario> listarUsuarios()
            throws PersistenciaException, LibreriaException {

        HashSet<Usuario> usuarios = new HashSet<>();

        String query = "SELECT * FROM usuarios";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            checkTabla(con);

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        TipoUsuario.valueOf(rs.getString("tipo_usuario"))
                );

                usuarios.add(u);
            }
            rs.close();
            ps.close();
            Conexion.cerrar();

            return usuarios;

        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }
    }

    private static void checkTabla(Connection connection) throws PersistenciaException {
        try {
            Statement st = connection.createStatement();
            st.execute("create table if not exists usuarios ( " +
                    "id_usuario int autoincrement primary key not null , " +
                    "usuario varchar(20) unique not null, " +
                    "contrasenia varchar(20) not null, " +
                    "tipo_usuario varchar(20) not null;");

            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }
}
