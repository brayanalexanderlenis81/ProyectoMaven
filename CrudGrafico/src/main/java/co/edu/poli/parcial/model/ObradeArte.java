package co.edu.poli.parcial.model;
import java.io.Serializable;

/**
 * Clase abstracta que representa una obra de arte genérica en el museo.
 * Contiene los atributos y comportamientos comunes a todas las obras.
 * Es serializable para permitir la persistencia de sus instancias.
 */
public abstract class ObradeArte implements Serializable {
	// --- Atributos ---
    private String codigo;
    private String titulo;
    private String fechaCreacion;
    private String dimensiones;
    private Artista artista;

    /**
     * Constructor para inicializar una obra de arte.
     *
     * @param codigo El código único de la obra.
     * @param titulo El título de la obra.
     * @param fechaCreacion La fecha de creación de la obra.
     * @param dimensiones Las dimensiones de la obra.
     * @param artista El objeto Artista asociado a la obra.
     */
    public ObradeArte(String codigo, String titulo, String fechaCreacion, String dimensiones, Artista artista) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.dimensiones = dimensiones;
        this.artista = artista;
        validarCodigo();
    }

    // --- Métodos Públicos ---

    /**
     * Método abstracto que debe ser implementado por las subclases.
     * Devuelve una cadena con toda la información de la obra, incluyendo sus detalles específicos.
     *
     * @return Una cadena formateada con los detalles completos de la obra.
     */
    public abstract String mostrarInformacionCompleta();

    // --- Getters y Setters ---

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    // --- Métodos Protegidos ---

    /**
     * Método de ayuda para las clases hijas.
     * Construye y devuelve una cadena con los detalles comunes a todas las obras.
     *
     * @return Una cadena con la información base de la obra.
     */
    protected String obtenerDetallesComunes() {
        return "Código: " + codigo + "\nTítulo: " + titulo + "\nArtista: " + artista.getNombre() +
               "\nFecha: " + fechaCreacion + "\nDimensiones: " + dimensiones;
    }
    protected void validarCodigo() {
        if (this.codigo == null || this.codigo.trim().isEmpty()) {
            // En un proyecto real, aquí se lanzaría una excepción.
            System.out.println("ADVERTENCIA: El código de la obra no puede ser nulo o vacío.");
        }
    }
}
