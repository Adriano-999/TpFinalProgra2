package Model;

import Interface.IGestor;
import Exception.NoEncontradoException;
import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.DatosInvalidosException;
import Model.BDD.ConexionPasajero;
import Model.BDD.ConexionReserva;

import java.util.ArrayList;
import java.util.Map;

public class GestorReservas implements IGestor<Reserva, Integer> {
    private Map<Integer, Reserva> cache;

    @Override
    public boolean guardar(Reserva item) throws PersistenciaException, LibreriaException, DatosInvalidosException {
        if (cache.containsKey(item.getId())){
            cache.replace(item.getId(),item);
        } else {
            cache.put(item.getId(),item);
        }
        ConexionReserva.guardarReserva(item);
        return true;
    }

    @Override
    public Reserva buscarPorId(Integer integer) throws PersistenciaException, LibreriaException, NoEncontradoException {
        if (cache.containsKey(integer)){
            return cache.get(integer);
        }
        Reserva reserva = ConexionReserva.buscarReserva(integer);
        cache.put(reserva.getId(),reserva);
        return reserva;
    }

    @Override
    public ArrayList<Reserva> listarTodos() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        for (Map.Entry<Integer, Reserva> entry : cache.entrySet()){
            reservas.add(entry.getValue());
        }
        return reservas;
    }

    @Override
    public boolean eliminar(Integer integer) throws PersistenciaException, NoEncontradoException, LibreriaException {
        if (cache.containsKey(integer)){
            cache.remove(integer);
        }
        ConexionReserva.borrarReserva(integer);
        return true;
    }

    public ArrayList<Reserva> buscarReservasPorPasajero(Pasajero pasajero) throws PersistenciaException, LibreriaException, NoEncontradoException
    {
        int id_pasajero = ConexionPasajero.obtenerId(pasajero.getDni());
        ArrayList<Reserva> reservasAlCache = ConexionReserva.buscarReservasPorPasajero(id_pasajero);
        for (Reserva r : reservasAlCache)
        {
            cache.put(r.getId(), r);
        }
        ArrayList<Reserva> reservas = new ArrayList<>();
        for (Reserva r : cache.values())
        {
            if(r.getPasajero_id()==id_pasajero)
            {
                reservas.add(r);
            }
        }
        return reservas;
        }
}
