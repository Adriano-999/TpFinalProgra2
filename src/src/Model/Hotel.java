package Model;

import Model.BDD.ConexionHabitacion;
import Exception.*;
import Enum.*;
import java.security.Guard;
import java.util.HashSet;
import java.util.ArrayList;

public class Hotel {
    private HashSet<Habitacion> habitaciones;
    private HashSet<Reserva> reservas;

    public Hotel() {
        habitaciones = new HashSet<>();
        reservas = new HashSet<>();
    }

    public void validarHabitacion(Habitacion habitacion) throws DatosInvalidosException{
        if(habitacion == null){
            throw new DatosInvalidosException();
        }
        if(habitacion.getNumeroHabitacion() <= 0){
            throw new DatosInvalidosException();
        }
        if(habitacion.getPrecioPorDia() <= 0){
            throw new DatosInvalidosException();
        }
    }

    public boolean registrarHabitacion(Habitacion habitacion) throws LibreriaException, PersistenciaException, DatosInvalidosException {
        validarHabitacion(habitacion);
        if(buscarHabitacionPorNumero(habitacion.getNumeroHabitacion()) != null){
            throw new DatosInvalidosException();
        }
        boolean guardada = ConexionHabitacion.guardarHabitacion(habitacion);
        if(guardada){
            habitaciones.add(habitacion);
        }
        return guardada;
    }

    public Habitacion buscarHabitacion(int id) throws LibreriaException, PersistenciaException, NoEncontradoException{
        Habitacion habitacion = ConexionHabitacion.buscarHabitacion(id);
        habitaciones.add(habitacion);
        return habitacion;
    }

    public Habitacion buscarHabitacionPorNumero(int numero){
     Habitacion found = null;

     for(Habitacion habitacion : habitaciones){
         if(habitacion.getNumeroHabitacion() == numero){
             found = habitacion;
         }
     }
     return found;
    }


    public ArrayList<Habitacion> listarDisponibles() throws LibreriaException, PersistenciaException{
        try{
            return ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.DISPONIBLE);
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }

    public ArrayList<Habitacion> listarOcupadas() throws LibreriaException, PersistenciaException{
        try{
            return ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.OCUPADA);
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }

    public ArrayList<Habitacion> listarFueraDeServicio() throws LibreriaException, PersistenciaException{
        try{
            return ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.FUERA_DE_SERVICIO);
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }

    public boolean borrarHabitacion(int id) throws LibreriaException, PersistenciaException{
        boolean borrada = ConexionHabitacion.borrarHabitacion(id);

        Habitacion found = null;
        for(Habitacion habitacion : habitaciones){
            if(habitacion.getIdHabitacion() == id){
                found = habitacion;
            }
        }
        if(found != null){
            habitaciones.remove(found);
        }
        return borrada;
    }
}
