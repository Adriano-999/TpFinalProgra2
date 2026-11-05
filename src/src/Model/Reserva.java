package Model;

import java.sql.Date;

public class Reserva {
    private int pasajero_id;
    private int habitacion_id;
    private Date fechaInicio;
    private int id;

    public Reserva(int pasajero_id, int habitacion_id, Date fechaInicio, int id) {
        this.pasajero_id = pasajero_id;
        this.habitacion_id = habitacion_id;
        this.fechaInicio = fechaInicio;
        this.id = id;
    }

    public int getPasajero_id() {
        return pasajero_id;
    }

    public int getHabitacion_id() {
        return habitacion_id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "pasajero=" + pasajero_id +
                ", habitacion=" + habitacion_id +
                ", fechaInicio=" + fechaInicio +
                ", id=" + id +
                '}';
    }
}
