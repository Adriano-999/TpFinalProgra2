package Model;

import Interface.IGestor;
import Model.BDD.ConexionPasajero;
import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.NoEncontradoException;
import java.util.ArrayList;
import java.util.Map;

public class GestorPasajeros implements IGestor<Pasajero,String> {
    Map<String,Pasajero> cache;

    @Override
    public boolean guardar(Pasajero item) throws PersistenciaException, LibreriaException {
        if (cache.containsKey(item.getDni())){
            cache.replace(item.getDni(),item);
        } else {
            cache.put(item.getDni(),item);
        }
        ConexionPasajero.guardarPasajero(item);
        return true;
    }

    @Override
    public Pasajero buscarPorId(String s) throws PersistenciaException, LibreriaException, NoEncontradoException {
        if (cache.containsKey(s)){
            return cache.get(s);
        }
        Pasajero pasajero = ConexionPasajero.buscarPasajero(s);
        cache.put(pasajero.getDni(),pasajero);
        return pasajero;
    }

    @Override
    public ArrayList<Pasajero> listarTodos() { // devuelve t0do lo que esta en el cache actualmente
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        for (Map.Entry<String, Pasajero> entry : cache.entrySet()){
            pasajeros.add(entry.getValue());
        }
        return pasajeros;
    }

    @Override
    public boolean eliminar(String s) throws PersistenciaException, NoEncontradoException, LibreriaException {
        if (cache.containsKey(s)){
            cache.remove(s);
        }
        ConexionPasajero.eliminarPasajero(s);
        return true;
    }

    public ArrayList<Pasajero> buscarPorNombre(String nombre) throws PersistenciaException, LibreriaException, NoEncontradoException {
        ArrayList<Pasajero> pasajeros = ConexionPasajero.buscarPasajerosPorNombre(nombre);
        for(Pasajero pasajero : pasajeros){
            if (cache.containsKey(pasajero.getDni())){
                cache.replace(pasajero.getDni(),pasajero);
            }else {
                cache.put(pasajero.getDni(),pasajero);
            }
        }
        return pasajeros;
    }

}
