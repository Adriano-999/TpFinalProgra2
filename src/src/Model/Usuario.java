package Model;
import Enum.TipoUsuario;
public class Usuario {
    private String usuario;
    private String contrasenia;
    private TipoUsuario tipo_usuario;

    public Usuario(String usuario, String contrasenia, TipoUsuario tipo_usuario) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.tipo_usuario = tipo_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contrasenia;
    }

    public void setContraseña(String contraseña) {
        this.contrasenia = contraseña;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }
}
