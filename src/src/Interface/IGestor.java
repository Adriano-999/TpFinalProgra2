package Interface;

import java.util.ArrayList;

public interface IGestor<T, ID> {
    boolean guardar(T item);
    T buscarPorId(ID id);
    ArrayList<T> listarTodos();
    boolean eliminar(ID id);
}

