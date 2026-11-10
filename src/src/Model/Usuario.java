package Model;
import Enum.TipoUsuario;
public class Usuario {
    private int id_usuario;
    private String usuario;
    private String contrasenia;
    private TipoUsuario tipo_usuario;

    public Usuario(int id_usuario, String usuario, String contrasenia, TipoUsuario tipo_usuario) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.tipo_usuario = tipo_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    @Override
    public String toString() {
        return "Id Usuario: " + id_usuario + ", usuario: " + usuario + ", contrasenia: " + contrasenia
                + ", tipo usuario: " + tipo_usuario;
    }
}
