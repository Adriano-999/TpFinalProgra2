package Model;
import Enum.TipoUsuario;
public class Usuario {
    private String usuario;
    private String contraseña;
    private TipoUsuario tipo_usuario;

    public Usuario(String usuario, String contraseña, TipoUsuario tipo_usuario) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.tipo_usuario = tipo_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }
}
