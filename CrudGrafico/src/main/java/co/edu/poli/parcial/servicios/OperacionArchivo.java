package co.edu.poli.parcial.servicios;
import co.edu.poli.parcial.model.ObradeArte;
/**
 * Interfaz que define el contrato para las operaciones de persistencia de datos
 * (serialización y deserialización).
 */
public interface OperacionArchivo {

    /**
     * Guarda la colección actual de obras de arte en un archivo.
     * @param obras El arreglo de obras a guardar.
     * @param path La ruta (carpeta) donde se guardará el archivo.
     * @param name El nombre del archivo.
     * @return Un mensaje de texto indicando el resultado de la operación.
     */
    String serializar(ObradeArte[] obras, String path, String name);

    /**
     * Carga una colección de obras de arte desde un archivo.
     * @param path La ruta (carpeta) donde se encuentra el archivo.
     * @param name El nombre del archivo a cargar.
     * @return Un arreglo de ObraDeArte con los datos cargados.
     */
    ObradeArte[] deserializar(String path, String name);
}