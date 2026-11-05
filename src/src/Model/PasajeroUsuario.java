package Model;

public class PasajeroUsuario extends Usuario{
    private Pasajero pasajero;

    public PasajeroUsuario(String usuario, String contraseña, Pasajero pasajero) {
        super(usuario, contraseña);
        this.pasajero = pasajero;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
}
