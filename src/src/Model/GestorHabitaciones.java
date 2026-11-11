package Model;

import Interface.IGestor;
import Model.BDD.ConexionHabitacion;
import Exception.*;
import Enum.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class GestorHabitaciones implements IGestor<Habitacion, Integer> {
    private Map<Integer, Habitacion> cache;

    public GestorHabitaciones() {
        cache = new HashMap<>();
    }

    @Override
    public boolean guardar(Habitacion item) throws PersistenciaException, LibreriaException, DatosInvalidosException {
        if(item == null){
            throw new DatosInvalidosException();
        }
        item.validarHabitacion(item);
        if(item.getIdHabitacion() <= 0 && buscarHabitacionPorNumero(item.getNumeroHabitacion()) != null){
            throw new DatosInvalidosException();
        }
        boolean guardada = ConexionHabitacion.guardarHabitacion(item);
        if(guardada && item.getIdHabitacion() > 0){
            cache.put(item.getIdHabitacion(), item);
        }
        return guardada;
    }

    @Override
    public Habitacion buscarPorId(Integer integer) throws PersistenciaException, LibreriaException, NoEncontradoException {
        return null;
    }

    @Override
    public ArrayList<Habitacion> listarTodos() {
        return null;
    }

    @Override
    public boolean eliminar(Integer integer) throws PersistenciaException, NoEncontradoException, LibreriaException {
        return false;
    }

    public Habitacion buscarHabitacionPorNumero(int numero){
     Habitacion found = null;

     /*for(Habitacion habitacion : habitaciones){
         if(habitacion.getNumeroHabitacion() == numero){
             found = habitacion;
         }
     }*/
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

    /*public boolean borrarHabitacion(int id) throws LibreriaException, PersistenciaException{
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
    }*/
}
