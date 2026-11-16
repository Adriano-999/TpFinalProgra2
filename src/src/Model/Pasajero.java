package Model;
import Exception.DatosInvalidosException;

public class Pasajero {
    private String dni;
    private String nombre;
    private String origen;
    private String domicilio;

    public Pasajero(String dni, String nombre, String origen, String domicilio) {
        this.dni = dni;
        this.nombre = nombre;
        this.origen = origen;
        this.domicilio = domicilio;
    }

    public static boolean validarPasajero(Pasajero pasajero) throws DatosInvalidosException {
        StringBuilder stringBuilder = new StringBuilder(pasajero.getDni());

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (!(stringBuilder.charAt(i) == '0' || stringBuilder.charAt(i) == '1' || stringBuilder.charAt(i) == '2' || stringBuilder.charAt(i) == '3' || stringBuilder.charAt(i) == '4' || stringBuilder.charAt(i) == '5' || stringBuilder.charAt(i) == '6' ||stringBuilder.charAt(i) == '7' || stringBuilder.charAt(i) == '8' || stringBuilder.charAt(i) == '9')){
              throw new DatosInvalidosException();
            }
        }
        return true;
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

    @Override
    public String toString() {
        return "Dni: " + dni + ", nombre: " + nombre + ", origen: "
                + origen + ", domicilio: " + domicilio;
    }
}
