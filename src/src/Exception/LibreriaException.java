package Exception;

public class LibreriaException extends Exception{
    public LibreriaException () {
        super("Libreria JDBC no encontrada.");
    }
}