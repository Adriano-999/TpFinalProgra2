package Model.BDD;

import java.sql.*;

import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.NoEncontradoException;
import Model.Reserva;

public class ConexionReserva {

    public static Reserva buscarReserva(int id) throws LibreriaException,PersistenciaException, NoEncontradoException {
        try {
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("Select * from reservas where id_reserva="+id+";");
            if (resultSet.next()){
                Reserva reserva = new Reserva(
                        resultSet.getInt("id_pasajero"),
                        resultSet.getInt("id_habitacion"),
                        resultSet.getDate("fecha_inicio"),
                        resultSet.getInt("id_reserva")
                );
                resultSet.close();
                st.close();
                Conexion.cerrar();
                return reserva;
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


    public static boolean guardarReserva(Reserva reserva) throws LibreriaException,PersistenciaException{
        try{
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            boolean update = false;
            if (reserva.getId() >  0){
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select * from reservas where id_reserva="+reserva.getId()+";");
                update = resultSet.next();
                resultSet.close();
                st.close();
            }
            if(update){
                PreparedStatement prepSt = connection.prepareStatement(
                        "update reservas " +
                                "set id_pasajero = ? , " +
                                "id_habitacion = ? , " +
                                "fecha_inicio = ? " +
                                "where id_reserva=?;");
                prepSt.setInt(1,reserva.getPasajero_id());
                prepSt.setInt(2,reserva.getHabitacion_id());
                prepSt.setDate(3,reserva.getFechaInicio());
                prepSt.setInt(4,reserva.getId());
                prepSt.execute();
                prepSt.close();
            }else{
                Statement st = connection.createStatement();
                st.execute(
                        "INSERT INTO reservas (id_pasajero, id_habitacion, fecha_inicio) VALUES ("+
                                reserva.getPasajero_id()+","+
                                reserva.getHabitacion_id()+","+
                                reserva.getFechaInicio()+","+");"
                );
                st.close();
            }
            Conexion.cerrar();
            return true;
        } catch (SQLException e) {
            throw new PersistenciaException(3);
        }
    }




    public static boolean borrarReserva(int id) throws LibreriaException,PersistenciaException {
        try {
            Connection connection = Conexion.abrir();
            checkTabla(connection);
            Statement st = connection.createStatement();
            st.execute("DELETE FROM reservas WHERE id_reserva = "+id+";");
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
            st.execute("create table if not exists reservas ( " +
                    "id_reserva int auto_increment primary key not null , " +
                    "fecha_inicio date, " +
                    "id_pasajero int, " +
                    "id_habitacion int, " +
                    "FOREIGN KEY (id_pasajero) REFERENCES pasajeros(id_pasajero), " +
                    "FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion);");
            st.close();
        } catch (SQLException e) {
            throw new PersistenciaException(2);
        }
    }

}
