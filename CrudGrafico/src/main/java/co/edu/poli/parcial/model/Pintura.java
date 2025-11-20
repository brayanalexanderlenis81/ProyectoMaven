package co.edu.poli.parcial.model;
/**
 * Representa una obra de arte de tipo Pintura.
 * Hereda de ObraDeArte y añade la característica específica de la técnica.
 */
public class Pintura extends ObradeArte {
	private String tecnica;

    /**
     * Constructor para crear un nuevo objeto Pintura.
     *
     * @param codigo El código único de la pintura.
     * @param titulo El título de la pintura.
     * @param fechaCreacion La fecha de creación.
     * @param dimensiones Las dimensiones.
     * @param artista El artista de la pintura.
     * @param tecnica La técnica utilizada (ej. Óleo, Acuarela).
     */
    public Pintura(String codigo, String titulo, String fechaCreacion, String dimensiones, Artista artista, String tecnica) {
        super(codigo, titulo, fechaCreacion, dimensiones, artista);
        this.tecnica = tecnica;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    /**
     * Implementación del método abstracto para mostrar la información completa de la pintura.
     * Utiliza el método protegido del padre para obtener los detalles comunes y añade la técnica.
     *
     * @return Una cadena formateada con todos los detalles de la pintura.
     */
    @Override
    public String mostrarInformacionCompleta() {
        String infoComun = super.obtenerDetallesComunes();
        return infoComun + "\nTipo: Pintura" + "\nTécnica: " + this.tecnica;
    }
}
