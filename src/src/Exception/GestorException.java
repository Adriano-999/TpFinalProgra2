package Exception;

public abstract class GestorException extends Exception {
    public GestorException(String message) {
        super(message);
    }
    public GestorException() {
        super("Error de sistema");
    }
}
