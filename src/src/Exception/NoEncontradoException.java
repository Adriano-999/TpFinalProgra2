package Exception;

public class NoEncontradoException extends GestorException {
    public NoEncontradoException() {
        super("Elemento no encontrado");
    }
}