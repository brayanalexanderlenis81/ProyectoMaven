package co.edu.poli.parcial;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.poli.parcial.model.Artista;
import co.edu.poli.parcial.model.Escultura;
import co.edu.poli.parcial.model.ObradeArte;
import co.edu.poli.parcial.model.Pintura;
import co.edu.poli.parcial.servicios.ImplementacionOperacionCRUD;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<ObradeArte> tablaObras;
    @FXML
    private TableColumn<ObradeArte, String> colCodigo;
    @FXML
    private TableColumn<ObradeArte, String> colTitulo;
    @FXML
    private TableColumn<ObradeArte, String> colTipo;
    @FXML
    private TableColumn<ObradeArte, String> colArtista;

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtDimensiones;
    @FXML
    private ComboBox<Artista> cmbArtista;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private Label lblExtra;
    @FXML
    private TextField txtExtra;

    @FXML
    private TextArea txtDetalle;

    private ImplementacionOperacionCRUD gestor;
    private ObservableList<ObradeArte> listaObservable;
    private ObservableList<Artista> listaArtistas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gestor = new ImplementacionOperacionCRUD(50);

        // Artistas predefinidos (mismos de Principal.java en consola)
        Artista artista1 = new Artista("ART01", "Leonardo da Vinci", "Italiana");
        Artista artista2 = new Artista("ART02", "Auguste Rodin", "Francesa");
        Artista artista3 = new Artista("ART03", "Fernando Botero", "Colombiana");

        listaArtistas = FXCollections.observableArrayList(artista1, artista2, artista3);
        cmbArtista.setItems(listaArtistas);

        // Tipos de obra
        cmbTipo.setItems(FXCollections.observableArrayList("Pintura", "Escultura"));
        lblExtra.setText("Técnica"); // valor por defecto

        cmbTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Pintura".equals(newVal)) {
                lblExtra.setText("Técnica");
            } else if ("Escultura".equals(newVal)) {
                lblExtra.setText("Material");
            } else {
                lblExtra.setText("Técnica / Material");
            }
        });

        colCodigo.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCodigo()));

        colTitulo.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTitulo()));

        colTipo.setCellValueFactory(cell -> {
            ObradeArte obra = cell.getValue();
            String tipo = "Obra";
            if (obra instanceof Pintura) {
                tipo = "Pintura";
            } else if (obra instanceof Escultura) {
                tipo = "Escultura";
            }
            return new SimpleStringProperty(tipo);
        });

        colArtista.setCellValueFactory(cell -> {
            Artista a = cell.getValue().getArtista();
            String nombre = (a != null) ? a.getNombre() : "";
            return new SimpleStringProperty(nombre);
        });

        listaObservable = FXCollections.observableArrayList();
        tablaObras.setItems(listaObservable);


        tablaObras.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarFormularioDesdeObra(newSel);
                txtDetalle.setText(newSel.mostrarInformacionCompleta());
            }
        });


    }


    @FXML
    private void handleCrear() {
        ObradeArte nueva = construirObraDesdeFormulario();
        if (nueva == null) {
            return;
        }

        String mensaje = gestor.create(nueva);
        mostrarAlerta(AlertType.INFORMATION, "Crear obra", mensaje);

        limpiarFormulario();
    }

    @FXML
    private void handleActualizar() {
        ObradeArte seleccionada = tablaObras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(AlertType.WARNING, "Actualizar obra",
                    "Primero selecciona una obra en la tabla.");
            return;
        }

        if (txtTitulo.getText().isEmpty() || txtDimensiones.getText().isEmpty()
                || txtFecha.getText().isEmpty() || txtExtra.getText().isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Actualizar obra",
                    "Título, fecha, dimensiones y técnica/material no pueden estar vacíos.");
            return;
        }

        seleccionada.setTitulo(txtTitulo.getText());
        seleccionada.setDimensiones(txtDimensiones.getText());
        seleccionada.setFechaCreacion(txtFecha.getText());

        if (seleccionada instanceof Pintura) {
            Pintura p = (Pintura) seleccionada;
            p.setTecnica(txtExtra.getText());
        } else if (seleccionada instanceof Escultura) {
            Escultura e = (Escultura) seleccionada;
            e.setMaterial(txtExtra.getText());
        }

        String mensaje = gestor.update(seleccionada);
        mostrarAlerta(AlertType.INFORMATION, "Actualizar obra", mensaje);


        tablaObras.refresh();
        txtDetalle.setText(seleccionada.mostrarInformacionCompleta());
    }

    @FXML
    private void handleEliminar() {
        ObradeArte seleccionada = tablaObras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(AlertType.WARNING, "Eliminar obra",
                    "Primero selecciona una obra en la tabla.");
            return;
        }

        String codigo = seleccionada.getCodigo();
        String mensaje = gestor.delete(codigo);
        mostrarAlerta(AlertType.INFORMATION, "Eliminar obra", mensaje);



        limpiarFormulario();
        txtDetalle.clear();
    }

    @FXML
    private void handleLimpiar() {
        limpiarFormulario();
        tablaObras.getSelectionModel().clearSelection();
        txtDetalle.clear();
    }

    @FXML
    private void handleGuardarArchivo() {
        // Mismo path y nombre que en la versión de consola
        String mensaje = gestor.serializar(gestor.readAll(), "data/", "coleccion.dat");
        mostrarAlerta(AlertType.INFORMATION, "Guardar colección", mensaje);
    }

    @FXML
    private void handleCargarArchivo() {
        ObradeArte[] cargadas = gestor.deserializar("data/", "coleccion.dat");
        if (cargadas.length == 0) {
            mostrarAlerta(AlertType.WARNING, "Cargar colección",
                    "No se pudo cargar el archivo o está vacío.");
            return;
        }
        gestor.cargarObras(cargadas);
        mostrarAlerta(AlertType.INFORMATION, "Cargar colección",
                "Colección cargada correctamente. Usa 'Listar obras' para verlas.");
    }

    @FXML
    private void handleListar() {
        actualizarTabla();
        mostrarAlerta(AlertType.INFORMATION, "Listar obras",
                "Se han listado todas las obras registradas.");
    }

    @FXML
    private void handleVerDetalle() {
        ObradeArte seleccionada = tablaObras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(AlertType.WARNING, "Ver detalle",
                    "Selecciona una obra en la tabla.");
            return;
        }
        txtDetalle.setText(seleccionada.mostrarInformacionCompleta());
    }



    private ObradeArte construirObraDesdeFormulario() {
        String codigo = txtCodigo.getText();
        String titulo = txtTitulo.getText();
        String fecha = txtFecha.getText();
        String dimensiones = txtDimensiones.getText();
        Artista artista = cmbArtista.getValue();
        String tipo = cmbTipo.getValue();
        String extra = txtExtra.getText();

        if (codigo.isEmpty() || titulo.isEmpty() || fecha.isEmpty()
                || dimensiones.isEmpty() || artista == null
                || tipo == null || extra.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Datos incompletos",
                    "Todos los campos son obligatorios.");
            return null;
        }

        if ("Pintura".equals(tipo)) {
            return new Pintura(codigo, titulo, fecha, dimensiones, artista, extra);
        } else if ("Escultura".equals(tipo)) {
            return new Escultura(codigo, titulo, fecha, dimensiones, artista, extra);
        } else {
            mostrarAlerta(AlertType.ERROR, "Tipo no válido",
                    "El tipo debe ser Pintura o Escultura.");
            return null;
        }
    }

    private void actualizarTabla() {
        ObradeArte[] obras = gestor.readAll();
        listaObservable.setAll(obras);
    }

    private void cargarFormularioDesdeObra(ObradeArte obra) {
        txtCodigo.setText(obra.getCodigo());
        txtTitulo.setText(obra.getTitulo());
        txtFecha.setText(obra.getFechaCreacion());
        txtDimensiones.setText(obra.getDimensiones());
        cmbArtista.setValue(obra.getArtista());

        if (obra instanceof Pintura) {
            Pintura p = (Pintura) obra;
            cmbTipo.setValue("Pintura");
            lblExtra.setText("Técnica");
            txtExtra.setText(p.getTecnica());
        } else if (obra instanceof Escultura) {
            Escultura e = (Escultura) obra;
            cmbTipo.setValue("Escultura");
            lblExtra.setText("Material");
            txtExtra.setText(e.getMaterial());
        } else {
            cmbTipo.setValue(null);
            lblExtra.setText("Técnica / Material");
            txtExtra.clear();
        }
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtTitulo.clear();
        txtFecha.clear();
        txtDimensiones.clear();
        cmbArtista.getSelectionModel().clearSelection();
        cmbTipo.getSelectionModel().clearSelection();
        lblExtra.setText("Técnica / Material");
        txtExtra.clear();
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}