package Model.BDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            Usuario u = new Usuario(rs.getString("usuario"), rs.getString("contrasenia"), TipoUsuario.valueOf(rs.getString("tipo_usuario")));
            ps.close();
            rs.close();
            return u;
        }catch(SQLException e)
        {
            throw new PersistenciaException(4);
        }
    }
}
