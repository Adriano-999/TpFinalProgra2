package Model;

public class Pasajero {
    private int id_pasajero;
    private String dni;
    private String nombre;
    private String origen;
    private String domicilio;

    public Pasajero(int id_pasajero, String dni, String nombre, String origen, String domicilio) {
        this.id_pasajero = id_pasajero;
        this.dni = dni;
        this.nombre = nombre;
        this.origen = origen;
        this.domicilio = domicilio;
    }

    public int getId_pasajero() {
        return id_pasajero;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
