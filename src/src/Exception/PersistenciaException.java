package Exception;

public class PersistenciaException extends Exception{
    public PersistenciaException(int i) {
        String mensaje;
        switch (i){
            case 1:
                mensaje = "Servidor no encontrado.";
                break;
            case 2:
                mensaje = "Error de DDL.";
                break;
            case 3:
                mensaje = "Error de DML.";
                break;
            case 4:
                mensaje = "Error de SELECT.";
                break;
            default:
                mensaje = "Error de servidor.";
                break;
        }
        super(mensaje);
    }
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

}
