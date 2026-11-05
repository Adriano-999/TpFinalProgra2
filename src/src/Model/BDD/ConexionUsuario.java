package Model.BDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Exception.LibreriaException;
import Exception.PersistenciaException;

public class ConexionUsuario {

    public static boolean ingresar(String usuario, String contraseña) throws PersistenciaException, LibreriaException
    {
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
        try(Connection con = Conexion.abrir())
        {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e)
        {
            throw new PersistenciaException(4);
        }
    }
}
