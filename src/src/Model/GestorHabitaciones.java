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
    public Habitacion buscarPorId(Integer id) throws PersistenciaException, LibreriaException, NoEncontradoException {
        if (cache.containsKey(id)){
            return cache.get(id);
        }
        Habitacion habitacion = ConexionHabitacion.buscarHabitacion(id);
        cache.put(habitacion.getIdHabitacion(), habitacion);
        return habitacion;
    }

    public ArrayList<Habitacion> listarTodos() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        for(Habitacion habitacion : cache.values()){
            habitaciones.add(habitacion);
        }
        return habitaciones;
    }

    @Override
    public boolean eliminar(Integer id) throws PersistenciaException, NoEncontradoException, LibreriaException {
        boolean eliminada = ConexionHabitacion.borrarHabitacion(id);
        if(eliminada){
            cache.remove(id);
        }
        return eliminada;
    }

    public Habitacion buscarHabitacionPorNumero(int numero){
     Habitacion found = null;
     for(Habitacion habitacion : cache.values()){
         if(habitacion.getNumeroHabitacion() == numero){
             found = habitacion;
         }
     }
     return found;
    }

    public ArrayList<Habitacion> listarDisponibles() throws LibreriaException, PersistenciaException{
        try{
            ArrayList<Habitacion> habitaciones = ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.DISPONIBLE);
            for(Habitacion habitacion : habitaciones){
                cache.put(habitacion.getIdHabitacion(), habitacion);
            }
            return habitaciones;
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }

    public ArrayList<Habitacion> listarOcupadas() throws LibreriaException, PersistenciaException{
        try{
            ArrayList<Habitacion> habitaciones = ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.OCUPADA);
            for(Habitacion habitacion : habitaciones){
                cache.put(habitacion.getIdHabitacion(), habitacion);
            }
            return habitaciones;
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }

    public ArrayList<Habitacion> listarFueraDeServicio() throws LibreriaException, PersistenciaException{
        try{
            ArrayList<Habitacion> habitaciones = ConexionHabitacion.buscarHabitacionesPorEstado(HabitacionEstado.FUERA_DE_SERVICIO);
            for(Habitacion habitacion : habitaciones){
                cache.put(habitacion.getIdHabitacion(), habitacion);
            }
            return habitaciones;
        } catch (NoEncontradoException e) {
            return new ArrayList<Habitacion>();
        }
    }
}
