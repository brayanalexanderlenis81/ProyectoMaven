package co.edu.poli.parcial.servicios;
import co.edu.poli.parcial.model.ObradeArte;

/**
 * Interfaz que define el contrato para las operaciones CRUD (Create, Read, Update, Delete)
 * sobre las obras de arte de la colección.
 */
public interface OperacionCRUD {

    /**
     * Añade una nueva obra de arte a la colección.
     * @param obra El objeto ObraDeArte a crear.
     * @return Un mensaje de texto indicando el resultado de la operación.
     */
    String create(ObradeArte obra);

    /**
     * Devuelve todas las obras de arte actualmente en la colección.
     * @return Un arreglo de ObraDeArte.
     */
    ObradeArte[] readAll();

    /**
     * Busca y devuelve una obra de arte por su código.
     * @param codigo El código de la obra a buscar.
     * @return El objeto ObraDeArte si se encuentra, de lo contrario null.
     */
    ObradeArte readId(String codigo);

    /**
     * Actualiza la información de una obra de arte existente.
     * @param obraActualizada El objeto ObraDeArte con la información nueva.
     * @return Un mensaje de texto indicando el resultado de la operación.
     */
    String update(ObradeArte obraActualizada);
    
    /**
     * Elimina una obra de arte de la colección usando su código.
     * @param codigo El código de la obra a eliminar.
     * @return Un mensaje de texto indicando el resultado de la operación.
     */
    String delete(String codigo);
}