package co.edu.poli.parcial.model;
import java.io.Serializable;
/**
 * Representa a un Artista, definido por su identificación, nombre y nacionalidad.
 * Esta clase es serializable para permitir su persistencia en archivos.
 */
public class Artista implements Serializable{
	private String id;
    private String nombre;
    private String nacionalidad;

    /**
     * Constructor para crear un nuevo objeto Artista.
     *
     * @param id El identificador único del artista.
     * @param nombre El nombre completo del artista.
     * @param nacionalidad La nacionalidad del artista.
     */
    public Artista(String id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // --- Getters y Setters ---

    /**
     * Obtiene el ID del artista.
     * @return El ID del artista.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del artista.
     * @return El nombre del artista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el artista.
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la nacionalidad del artista.
     * @return La nacionalidad del artista.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Establece una nueva nacionalidad para el artista.
     * @param nacionalidad La nueva nacionalidad.
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Devuelve una representación en String del objeto Artista.
     * @return Cadena con el nombre y la nacionalidad del artista.
     */
    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}
