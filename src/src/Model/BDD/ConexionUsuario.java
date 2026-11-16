package Model.BDD;

import java.sql.*;
import java.util.ArrayList;

import Exception.LibreriaException;
import Exception.PersistenciaException;
import Exception.NoEncontradoException;
import Model.Usuario;
import Enum.TipoUsuario;

public class ConexionUsuario {

    public static Usuario ingresar(String usuario, String contrasenia) throws PersistenciaException, LibreriaException, NoEncontradoException
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

    public static boolean crearUsuario(String usuario, String contrasenia, TipoUsuario tipoUsuario) throws LibreriaException, PersistenciaException
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

    public static boolean modificarUsuario(Usuario usuario, String nombreUsuario) throws PersistenciaException, LibreriaException, NoEncontradoException
    {
        String query = "UPDATE usuarios SET usuario = ?, contrasenia = ?, tipo_usuario = ? WHERE usuario = ?";
        try(Connection con = Conexion.abrir())
        {
            checkTabla(con);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getTipo_usuario().name());
            ps.setString(4, nombreUsuario);
            if(ps.executeUpdate() == 0)
            {
                throw new NoEncontradoException();
            }
            ps.close();
            Conexion.cerrar();
            return true;
        }catch(SQLException e)
        {
            throw new PersistenciaException(3);
        }
    }

    public static Usuario buscarUsuario(String usuario)
            throws PersistenciaException, LibreriaException, NoEncontradoException {

        String query = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection con = Conexion.abrir()) {

            checkTabla(con);

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, usuario);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        Usuario u = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("usuario"),
                                rs.getString("contrasenia"),
                                TipoUsuario.valueOf(rs.getString("tipo_usuario"))
                        );

                        return u;
                    }
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }

        return null;
    }

    public static void eliminarUsuario(int id_usuario) throws PersistenciaException, LibreriaException, NoEncontradoException
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

    public static ArrayList<Usuario> buscarUsuarios(String nombre)
            throws PersistenciaException, LibreriaException, NoEncontradoException {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuarios where usuario like '%"+nombre+"%'";

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
            if (usuarios.isEmpty()){
                throw new NoEncontradoException();
            }
            return usuarios;

        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }
    }

    private static void checkTabla(Connection connection) throws PersistenciaException {
        try {
            Statement st = connection.createStatement();
            st.execute("create table if not exists usuarios ( " +
                    "id_usuario int auto_increment primary key not null , " +
                    "usuario varchar(20) unique not null, " +
                    "contrasenia varchar(20) not null, " +
                    "tipo_usuario varchar(20) not null);");
            st.execute("INSERT INTO usuarios (usuario,contrasenia,tipo_usuario) " +
                            "SELECT 'admin', 'admin', 'ADMINISTRADOR'  " +
                            "WHERE NOT EXISTS (SELECT * FROM usuarios);");
            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }
}
