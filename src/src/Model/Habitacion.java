package Model;
import Enum.HabitacionEstado;

import java.util.Objects;

public class Habitacion {

    private int idHabitacion;
    private int numeroHabitacion;
    private double precioPorDia;
    private HabitacionEstado habitacionEstado;
    private String motivo;

    public Habitacion(int numeroHabitacion, double precioPorDia) {
        this.idHabitacion = 0;
        this.numeroHabitacion = numeroHabitacion;
        this.precioPorDia = precioPorDia;
        this.habitacionEstado = HabitacionEstado.DISPONIBLE;
        this.motivo = null;
    }

    public Habitacion(int idHabitacion, int numeroHabitacion, double precioPorDia, HabitacionEstado habitacionEstado, String motivo) {
        this.idHabitacion = idHabitacion;
        this.numeroHabitacion = numeroHabitacion;
        this.precioPorDia = precioPorDia;
        this.habitacionEstado = habitacionEstado;
        this.motivo = motivo;
    }

    public int getIdHabitacion() { return idHabitacion; }

    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion;}

    public int getNumeroHabitacion() {return numeroHabitacion; }

    public void setNumeroHabitacion(int numeroHabitacion) {this.numeroHabitacion = numeroHabitacion;}

    public double getPrecioPorDia() {return precioPorDia;}

    public void setPrecioPorDia(double precioPorDia) {this.precioPorDia = precioPorDia;}

    public HabitacionEstado getHabitacionEstado() {return habitacionEstado;}

    public void setHabitacionEstado(HabitacionEstado habitacionEstado) {this.habitacionEstado = habitacionEstado;}

    public void setMotivo(String motivo) {this.motivo = motivo;}

    public String getMotivo() {return motivo;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Habitacion that)) return false;
        return numeroHabitacion == that.numeroHabitacion;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroHabitacion);
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", numeroHabitacion=" + numeroHabitacion +
                ", precioPorDia=" + precioPorDia +
                ", habitacionEstado=" + habitacionEstado +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
