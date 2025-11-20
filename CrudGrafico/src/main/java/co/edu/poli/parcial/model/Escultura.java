package co.edu.poli.parcial.model;
/**
 * Representa una obra de arte de tipo Escultura.
 * Hereda de ObraDeArte y añade la característica específica del material.
 */
public class Escultura extends ObradeArte {
	private String material;

    /**
     * Constructor para crear un nuevo objeto Escultura.
     *
     * @param codigo El código único de la escultura.
     * @param titulo El título de la escultura.
     * @param fechaCreacion La fecha de creación.
     * @param dimensiones Las dimensiones.
     * @param artista El artista de la escultura.
     * @param material El material del que está hecha (ej. Mármol, Bronce).
     */
    public Escultura(String codigo, String titulo, String fechaCreacion, String dimensiones, Artista artista, String material) {
        super(codigo, titulo, fechaCreacion, dimensiones, artista);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Implementación del método abstracto para mostrar la información completa de la escultura.
     * Utiliza el método protegido del padre para obtener los detalles comunes y añade el material.
     *
     * @return Una cadena formateada con todos los detalles de la escultura.
     */
    @Override
    public String mostrarInformacionCompleta() {
        String infoComun = super.obtenerDetallesComunes();
        return infoComun + "\nTipo: Escultura" + "\nMaterial: " + this.material;
    }
}
