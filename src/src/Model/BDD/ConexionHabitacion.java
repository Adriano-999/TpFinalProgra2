package Model.BDD;

import Exception.LibreriaException;
import Exception.NoEncontradoException;
import Exception.PersistenciaException;
import Enum.HabitacionEstado;
import Model.Habitacion;
import java.sql.*;
import java.util.ArrayList;

public class ConexionHabitacion {

    public static Habitacion buscarHabitacion(int id) throws LibreriaException,PersistenciaException, NoEncontradoException {
        try {
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("Select * from habitaciones where id_habitacion="+id+";");
            if (resultSet.next()){
                Habitacion habitacion = new Habitacion(
                        resultSet.getInt("id_habitacion"),
                        resultSet.getInt("numero_habitacion"),
                        resultSet.getDouble("precio_por_dia"),
                        HabitacionEstado.valueOf(resultSet.getString("habitacion_estado")),
                        resultSet.getString("motivo")
                );
                resultSet.close();
                st.close();
                Conexion.cerrar();
                return habitacion;
            } else {
                resultSet.close();
                st.close();
                Conexion.cerrar();
                throw new NoEncontradoException();
            }
        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }
    }


    public static ArrayList<Habitacion> buscarHabitacionesPorEstado(HabitacionEstado habitacionEstado) throws LibreriaException,PersistenciaException, NoEncontradoException {
        try {
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("Select * from habitaciones where habitacion_estado="+habitacionEstado+";");
            ArrayList<Habitacion> habitaciones = new ArrayList<>();
            while (resultSet.next()){
                Habitacion habitacion = new Habitacion(
                        resultSet.getInt("id_habitacion"),
                        resultSet.getInt("numero_habitacion"),
                        resultSet.getDouble("precio_por_dia"),
                        HabitacionEstado.valueOf(resultSet.getString("habitacion_estado")),
                        resultSet.getString("motivo")
                );
                habitaciones.add(habitacion);
            }
            resultSet.close();
            st.close();
            Conexion.cerrar();
            if (habitaciones.isEmpty()){
                throw new NoEncontradoException();
            } else {
                return habitaciones;
            }
        } catch (SQLException e) {
            throw new PersistenciaException(4);
        }
    }



    public static boolean guardarHabitacion(Habitacion habitacion) throws LibreriaException,PersistenciaException{
        try{
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            boolean update = false;
            if (habitacion.getIdHabitacion() >  0){ // habitaciones con id -1 automaticamente se aniaden a como nuevas a la tabla (se les asigna un nuevo id)
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select * from habitaciones where id_habitacion="+habitacion.getIdHabitacion()+";");
                update = resultSet.next();
                resultSet.close();
                st.close();
            }
            if(update){
                PreparedStatement prepSt = connection.prepareStatement(
                        "update habitaciones " +
                                "set numero_habitacion = ? , " +
                                "precio_por_dia = ? " +
                                "habitacion_estado = '?' " +
                                "motivo = '?' " +
                                "where id_habitacion=?;");
                prepSt.setInt(1,habitacion.getNumeroHabitacion());
                prepSt.setDouble(2,habitacion.getPrecioPorDia());
                prepSt.setString(3,habitacion.getHabitacionEstado().name());
                prepSt.setString(4,habitacion.getMotivo());
                prepSt.setInt(5,habitacion.getIdHabitacion());
                prepSt.execute();
                prepSt.close();
            }else{
                Statement st = connection.createStatement();
                st.execute(
                        "INSERT INTO habitaciones (numero_habitacion, precio_por_dia, habitacion_estado, motivo) VALUES ("+
                                habitacion.getNumeroHabitacion()+","+
                                habitacion.getPrecioPorDia()+","+
                                habitacion.getHabitacionEstado().name()+","+
                                habitacion.getMotivo()+");"
                );
                st.close();
            }
            Conexion.cerrar();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }




    public static boolean borrarHabitacion(int id) throws LibreriaException,PersistenciaException {
        try {
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            Statement st = connection.createStatement();
            st.execute("DELETE FROM habitaciones WHERE id_habitacion = "+id+";");
            st.close();
            Conexion.cerrar();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }


    private static void checkTabla(Connection connection) throws PersistenciaException {
        try {
            Statement st = connection.createStatement();
            st.execute("create table if not exists habitaciones ( " +
                    "id_habitacion int auto_increment primary key not null , " +
                    "numero_habitacion int, " +
                    "precio_por_dia double, " +
                    "habitacion_estado varchar(20)), " +
                    "motivo varchar(50);");
            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }

}
