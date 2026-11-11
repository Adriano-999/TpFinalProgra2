package Interface;
import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.NoEncontradoException;
import Exception.DatosInvalidosException;
import java.util.ArrayList;

public interface IGestor<T, ID> {
    boolean guardar(T item) throws PersistenciaException, LibreriaException, DatosInvalidosException;
    T buscarPorId(ID id) throws PersistenciaException, LibreriaException, NoEncontradoException;
    ArrayList<T> listarTodos();
    boolean eliminar(ID id) throws PersistenciaException, NoEncontradoException, LibreriaException;
}

