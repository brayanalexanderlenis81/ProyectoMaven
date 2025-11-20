package co.edu.poli.parcial.servicios;
import java.io.*;
import co.edu.poli.parcial.model.ObradeArte;
public class ImplementacionOperacionCRUD implements OperacionCRUD, OperacionArchivo {

    private ObradeArte[] coleccionObras;
    private int contadorObras;

    /**
     * Constructor que inicializa el gestor de la colección.
     * @param tamanoMaximo El tamaño máximo del arreglo para almacenar obras.
     */
    public ImplementacionOperacionCRUD(int tamanoMaximo) {
        this.coleccionObras = new ObradeArte[tamanoMaximo];
        this.contadorObras = 0;
    }

    @Override
    public String create(ObradeArte obra) {
        if (readId(obra.getCodigo()) != null) {
            return "ERROR: Ya existe una obra con el código " + obra.getCodigo() + ".";
        }
        if (contadorObras < coleccionObras.length) {
            coleccionObras[contadorObras] = obra;
            contadorObras++;
            return "Obra de arte creada exitosamente.";
        } else {
            return "ERROR: La colección está llena, no se pueden agregar más obras.";
        }
    }

    @Override
    public ObradeArte[] readAll() {
        ObradeArte[] actuales = new ObradeArte[contadorObras];
        System.arraycopy(coleccionObras, 0, actuales, 0, contadorObras);
        return actuales;
    }

    @Override
    public ObradeArte readId(String codigo) {
        for (int i = 0; i < contadorObras; i++) {
            if (coleccionObras[i].getCodigo().equalsIgnoreCase(codigo)) {
                return coleccionObras[i];
            }
        }
        return null;
    }

    @Override
    public String update(ObradeArte obraActualizada) {
        for (int i = 0; i < contadorObras; i++) {
            if (coleccionObras[i].getCodigo().equalsIgnoreCase(obraActualizada.getCodigo())) {
                coleccionObras[i] = obraActualizada;
                return "Obra actualizada correctamente.";
            }
        }
        return "ERROR: No se encontró ninguna obra con el código " + obraActualizada.getCodigo() + ".";
    }

    @Override
    public String delete(String codigo) {
        for (int i = 0; i < contadorObras; i++) {
            if (coleccionObras[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < contadorObras - 1; j++) {
                    coleccionObras[j] = coleccionObras[j + 1];
                }
                coleccionObras[contadorObras - 1] = null;
                contadorObras--;
                return "Obra eliminada exitosamente.";
            }
        }
        return "ERROR: No se encontró ninguna obra con el código " + codigo + " para eliminar.";
    }

    @Override
    public String serializar(ObradeArte[] obras, String path, String name) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (FileOutputStream fileOut = new FileOutputStream(path + name);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(obras);
            return "Colección guardada exitosamente en " + path + name;
        } catch (IOException i) {
            return "ERROR al guardar el archivo: " + i.getMessage();
        }
    }

    @Override
    public ObradeArte[] deserializar(String path, String name) {
        try (FileInputStream fileIn = new FileInputStream(path + name);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (ObradeArte[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ObradeArte[0]; // Devuelve arreglo vacío si hay error
        }
    }
    
    /**
     * Método para reemplazar la colección actual con una nueva (usado después de deserializar).
     * @param obrasCargadas El arreglo de obras cargado desde el archivo.
     */
    public void cargarObras(ObradeArte[] obrasCargadas) {
        this.coleccionObras = new ObradeArte[this.coleccionObras.length]; // Limpia el arreglo
        System.arraycopy(obrasCargadas, 0, this.coleccionObras, 0, obrasCargadas.length);
        this.contadorObras = obrasCargadas.length;
    }

}
