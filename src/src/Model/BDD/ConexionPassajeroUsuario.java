package Model.BDD;

import Model.PasajeroUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Exception.PersistenciaException;
import Exception.LibreriaException;

public class ConexionPassajeroUsuario {

    public static boolean ingresar(String usuario, String contraseña) throws PersistenciaException, LibreriaException
    {
        String query = "SELECT * FROM pasajero_usuarios WHERE usuario = ? AND contraseña = ?";
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
