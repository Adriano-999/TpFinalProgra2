package Interface;
import Exception.PersistenciaException;
import Exception.LibreriaException;
import Exception.NoEncontradoException;
import Exception.DatosInvalidosException;

public interface IGestor<T, ID> {
    boolean guardar(T item) throws PersistenciaException, LibreriaException, DatosInvalidosException;
    T buscarPorId(ID id) throws PersistenciaException, LibreriaException, NoEncontradoException;
    boolean eliminar(ID id) throws PersistenciaException, NoEncontradoException, LibreriaException;
}

