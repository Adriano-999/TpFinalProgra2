package Exception;

public class DatosInvalidosException extends GestorException {
    public DatosInvalidosException() {
        super("Dato ingresado invalido");
    }
}