package Model.BDD;

import java.sql.*;

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
                Usuario u = new Usuario(rs.getString("usuario"), rs.getString("contrasenia"), TipoUsuario.valueOf(rs.getString("tipo_usuario")));
            ps.close();
            rs.close();
            return u;}
            return null;
        }catch(SQLException e)
        {
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
