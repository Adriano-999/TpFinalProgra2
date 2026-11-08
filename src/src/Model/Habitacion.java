package Model;
import Enum.HabitacionEstado;
public class Habitacion {
    private int numero;
    private HabitacionEstado habitacionEstado;
    private double precio_por_dia;

    public Habitacion(int numero, HabitacionEstado habitacionEstado, double precio_por_dia) {
        this.numero = numero;
        this.habitacionEstado = habitacionEstado;
        this.precio_por_dia = precio_por_dia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public HabitacionEstado getHabitacionEstado() {
        return habitacionEstado;
    }

    public void setHabitacionEstado(HabitacionEstado habitacionEstado) {
        this.habitacionEstado = habitacionEstado;
    }

    public double getPrecio_por_dia() {
        return precio_por_dia;
    }

    public void setPrecio_por_dia(double precio_por_dia) {
        this.precio_por_dia = precio_por_dia;
    }
}
