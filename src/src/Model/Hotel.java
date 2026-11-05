package Model;

import java.util.HashSet;

public class Hotel {
    private HashSet<Habitacion> habitaciones;
    private HashSet<Reserva> reservas;

    public Hotel() {
        habitaciones = new HashSet<>();
        reservas = new HashSet<>();
    }

    public boolean registrarHabitacion(Habitacion habitacion){
        habitaciones.add(habitacion);
        // ConexionHabitaciones añadir
        return true;
    }

    public Habitacion buscarHabitacion(int id){
        // habitacion = ConexionHabitaciones añadir
        // habitaciones.add(habitacion);
        // return habitacion;
        return null;
    }



}
